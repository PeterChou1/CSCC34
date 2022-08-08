DROP TABLE IF EXISTS User, Listing, Availabilities,
    PaymentInfo, Review, Booked,
    ListingAmenities;


CREATE TABLE User (
                      userName CHAR(30) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      postalCode VARCHAR(255) NOT NULL,
                      city VARCHAR(255) NOT NULL,
                      country VARCHAR(255) NOT NULL,
                      sin VARCHAR(255) NOT NULL,
                      dob DATE NOT NULL
);

CREATE TABLE Listing (
                         ID INT AUTO_INCREMENT PRIMARY KEY,
                         owner CHAR(30),
                         name VARCHAR(255),
                         # ENUM('Entire place', 'Private rooms', 'Hotel rooms', 'Shared rooms'),
                         type VARCHAR(255),
                         latitude  DECIMAL(6,4) NOT NULL,
                         longitude  DECIMAL(6,4) NOT NULL,
                         postalCode VARCHAR(255) NOT NULL,
                         city VARCHAR(255) NOT NULL,
                         country VARCHAR(255) NOT NULL,
                         FOREIGN KEY (owner) REFERENCES User (userName) ON DELETE CASCADE
);


CREATE TABLE ListingAmenities (
                                  listingID INT NOT NULL,
                                  amenitiesName VARCHAR(255) NOT NULL,
                                  FOREIGN KEY (listingID) REFERENCES Listing (ID) ON DELETE CASCADE
);

CREATE TABLE Availabilities(
                               ID INT AUTO_INCREMENT PRIMARY KEY,
                               listingID INT NOT NULL,
                               startDate DATE NOT NULL,
                               endDate DATE NOT NULL,
                               price DOUBLE NOT NULL,
                               CONSTRAINT date_cst CHECK(startDate < endDate),
                               FOREIGN KEY (listingID) REFERENCES Listing (ID) ON DELETE CASCADE
);

CREATE TABLE PaymentInfo(
                            ID INT AUTO_INCREMENT PRIMARY KEY,
                            owner CHAR(30) NOT NULL,
                            paymentInfo VARCHAR(255) NOT NULL,
                            FOREIGN KEY(owner) REFERENCES User(userName) ON DELETE CASCADE
);

CREATE TABLE Booked(
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       renterID CHAR(30) NOT NULL,
                       listingID INT NOT NULL,
                       paymentInfo INT NOT NULL,
                       startDate DATE NOT NULL,
                       endDate DATE NOT NULL,
                       price DOUBLE NOT NULL DEFAULT 0,
                       renterCanceled BOOL DEFAULT FALSE,
                       hostCanceled BOOL DEFAULT FALSE,
                       finished BOOL DEFAULT FALSE,
                       FOREIGN KEY(renterID) REFERENCES User(userName) ON DELETE CASCADE,
                       FOREIGN KEY(listingID) REFERENCES Listing(ID) ON DELETE CASCADE,
                       FOREIGN KEY(paymentInfo) REFERENCES PaymentInfo(ID) ON DELETE CASCADE
);

CREATE TABLE Review (
                        bookingID INT NOT NULL,
                        reviewerID CHAR(30) NOT NULL,
                        rating INT NOT NULL,
                        review TEXT NOT NULL,
                        CONSTRAINT rating_cst CHECK(rating <= 5 AND rating >= 1),
                        FOREIGN KEY (bookingID) REFERENCES Booked (ID) ON DELETE CASCADE,
                        FOREIGN KEY (reviewerID) REFERENCES User (userName) ON DELETE CASCADE,
                        PRIMARY KEY(bookingID, reviewerID)
);


DELIMITER |
DROP TRIGGER IF EXISTS check_reviewer |
CREATE TRIGGER check_reviewer BEFORE INSERT ON Review FOR EACH ROW
BEGIN
    DECLARE hostID CHAR(30);
    DECLARE rentID CHAR(30);
    DECLARE fin BOOL DEFAULT FALSE;
    SET hostID = (SELECT owner FROM Listing WHERE
            ID=(SELECT listingID FROM Booked WHERE ID=NEW.bookingID));
    SET rentID = (SELECT renterID FROM booked WHERE ID=NEW.bookingID);
    SET fin = (SELECT finished FROM booked WHERE ID=NEW.bookingID);

    IF NEW.reviewerID <> rentID AND NEW.reviewerID <> hostID THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'reviewer is not renter or host';
    END IF;
    IF NOT fin THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'renter has not finished stay';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_user_age |
CREATE TRIGGER check_user_age BEFORE INSERT ON User FOR EACH ROW
BEGIN
    DECLARE age INT;
    SET age = DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), NEW.dob)), '%Y')+0;
    IF age < 18 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'user under 18';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_availabilities_overlap |
CREATE TRIGGER check_availabilities_overlap BEFORE INSERT ON Availabilities FOR EACH ROW
BEGIN
    DECLARE overlapavaliable INT;
    DECLARE overlapbooked INT;
    SET overlapbooked = (SELECT COUNT(ID) FROM Booked WHERE
        (NEW.startDate < endDate) AND
        (NEW.endDate > startDate) AND
        (NEW.listingID = listingID) AND
        NOT renterCanceled AND
        NOT hostCanceled LIMIT 1);

    SET overlapavaliable = (SELECT COUNT(ID) FROM Availabilities WHERE
        (NEW.startDate < endDate ) AND
        (NEW.endDate > startDate)  AND
        (NEW.listingID = listingID) AND
        NOT (ID=NEW.ID) LIMIT 1);
    IF overlapbooked > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'insert date conflicts with current booking';
    END IF;
    IF overlapavaliable > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'insert date conflicts with current availabilities';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_availabilities_update |
CREATE TRIGGER check_availabilities_update BEFORE UPDATE ON Availabilities FOR EACH ROW
BEGIN
    DECLARE overlapavaliable INT;
    DECLARE overlapbooked INT;
    SET overlapbooked = (SELECT COUNT(ID) FROM Booked WHERE
        (NEW.startDate < endDate)  AND
        (NEW.endDate > startDate) AND
        (NEW.listingID = listingID) AND
        NOT renterCanceled AND
        NOT hostCanceled LIMIT 1);

    SET overlapavaliable = (SELECT COUNT(ID) FROM Availabilities WHERE (NEW.startDate < endDate) AND
        (NEW.endDate > startDate)  AND
        (NEW.listingID = listingID) AND
        NOT (ID=OLD.ID) LIMIT 1);
    IF overlapbooked > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'update date conflicts with current booking';
    END IF;

    IF overlapavaliable > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'update date conflicts with current availabilities';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_update |
CREATE TRIGGER check_booking_update BEFORE UPDATE ON Booked FOR EACH ROW
BEGIN
    IF NEW.price <> OLD.price THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot change price of booking';
    END IF;
    IF OLD.finished THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot update finished booking';
    END IF;
    IF OLD.renterCanceled OR OLD.hostCanceled THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot update canceled booking';
    END IF;
    IF OLD.endDate <> NEW.endDate OR OLD.startDate <> NEW.startDate THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot change date of booking';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_after_update |
CREATE TRIGGER check_booking_after_update AFTER UPDATE ON Booked FOR EACH ROW
BEGIN
    DECLARE Id1 INT;
    DECLARE Id2 INT;
    DECLARE newStartDate DATE;
    DECLARE newEndDate DATE;
    -- update availabilities if host or renter cancel
    IF NEW.renterCanceled OR NEW.hostCanceled THEN
        SET newStartDate = NEW.startDate;
        SET newEndDate = NEW.endDate;
        SET Id1 = (SELECT ID FROM Availabilities WHERE listingID=NEW.listingID AND endDate=NEW.startDate AND price=NEW.price);
        SET Id2 = (SELECT ID FROM Availabilities WHERE listingID=NEW.listingID AND startDate=NEW.endDate AND price=NEW.price);
        IF Id1 IS NOT NULL THEN
            SET newStartDate = (SELECT startDate FROM Availabilities WHERE ID=Id1);
            DELETE FROM Availabilities WHERE ID=Id1;
        END IF;
        IF Id2 IS NOT NULL THEN
            SET newEndDate = (SELECT endDate FROM Availabilities WHERE ID=Id2);
            DELETE FROM Availabilities WHERE ID=Id2;
        END IF;
        INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
            (NEW.listingID, newStartDate, newEndDate, NEW.price);
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_valid |
CREATE TRIGGER check_booking_valid BEFORE INSERT ON Booked FOR EACH ROW
BEGIN
    DECLARE ownsHome BOOL;
    DECLARE ownsPayment BOOL;
    DECLARE priorBooking BOOL;
    DECLARE aID, alist INT;
    DECLARE aStart, aEnd DATE;
    DECLARE aPrice DOUBLE;
    SELECT ID, listingID, startDate, endDate, price
    INTO
        aID, aList, aStart, aEnd, aPrice
    FROM Availabilities WHERE listingID=NEW.listingID AND
            startDate <= NEW.startDate AND
            NEW.endDate <= endDate
    LIMIT 1;
    IF NEW.price <> 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot set price bookings';
    END IF;
    IF aID IS NULL THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'no available bookings';
    END IF;
    SET priorBooking = (SELECT COUNT(ID) FROM Booked
                        WHERE renterID=NEW.renterID AND
                            (NEW.startDate < endDate) AND
                            (NEW.endDate > startDate)  AND
                            NOT renterCanceled AND
                            NOT hostCanceled
                        LIMIT 1);
    IF priorBooking > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'prior booking conflicting';
    END IF;
    SET ownsHome = (SELECT owner FROM Listing WHERE ID=NEW.listingID) = NEW.renterID;
    IF ownsHome THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot book home you own';
    END IF;
    SET ownsPayment = (SELECT owner FROM PaymentInfo WHERE ID=NEW.paymentInfo) = NEW.renterID;
    IF NOT ownsPayment THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot pay using payment you do not own';
    END IF;
    IF (aStart <= NEW.startDate)  AND  (NEW.endDate <= aEnd) THEN
        SET NEW.price = aPrice;
        DELETE FROM Availabilities WHERE ID=aID;
        -- calculate new availabilities given booking
        IF aStart <= NEW.startDate THEN
            INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
                (alist, aStart, NEW.startDate, aPrice);
        END IF;
        IF NEW.endDate <= aEnd THEN
            INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
                (alist, NEW.endDate, aEnd, aPrice);
        END IF;
    END IF;
END |
DELIMITER ;



-- seed data
-- USER
INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
VALUES ('testUser1', 'peter', 'M2N0H6', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-1999', '%d-%m-%Y'));
INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
VALUES ('testUser2', 'james', 'M2N0H7', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-1980', '%d-%m-%Y'));
INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
VALUES ('testUser3', 'roberta', 'H2N0H5', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-1934', '%d-%m-%Y'));
INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
VALUES ('testUser4', 'rock', 'T1A8D1', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-1991', '%d-%m-%Y'));
INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
VALUES ('testUser5', 'revy', 'M2NA31', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-1969', '%d-%m-%Y'));

-- LISTING
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser1', 'Super Mansion','Entire place', 34.0364, 84.3454, 'E6J3K2', 'McAdam', 'Canada');
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser1', 'Dingy Hut','Shared rooms', 35.0364, 84.3254, 'G7A8K8', 'Saint-Nicolas', 'Canada');
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser1', 'Sketchy Back Alley','Entire place', 35.0355, 84.3454, 'K2S9J0', 'Stittsville', 'Canada');
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser2', 'Polluted Riverside','Entire place', 35.5354, 84.3454, 'A1K6C9', 'Torbay', 'Canada');
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser2', 'Meth Lab','Shared rooms', 35.0364, 84.3244, 'G7A8K8', 'Saint-Nicolas', 'Canada');
INSERT INTO LISTING(owner, name, type, latitude, longitude, postalcode, city, country)
VALUES ('testUser2', 'Slave Alley','Entire place', 35.0355, 84.3433, 'K2S9J0', 'Stittsville', 'Canada');

-- avaliabilities
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (1, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (1, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 3000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (2, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (2, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (3, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (3, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (4, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (4, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (5, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (5, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (6, STR_TO_DATE('27-01-2020', '%d-%m-%Y'), STR_TO_DATE('27-04-2020', '%d-%m-%Y'), 2000);
INSERT INTO Availabilities(listingID, startDate, endDate, price)
VALUES (6, STR_TO_DATE('27-05-2020', '%d-%m-%Y'), STR_TO_DATE('27-07-2020', '%d-%m-%Y'), 2000);

-- Payment
INSERT INTO PaymentInfo(owner, paymentinfo)
VALUES ('testUser1', '1111-2222-2222-2222');
INSERT INTO PaymentInfo(owner, paymentinfo)
VALUES ('testUser2', '2222-2222-2222-2222');
INSERT INTO PaymentInfo(owner, paymentinfo)
VALUES ('testUser3', '3333-2222-2222-2222');
INSERT INTO PaymentInfo(owner, paymentinfo)
VALUES ('testUser4', '4444-2222-2222-2222');


-- Booking
INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, finished)
    VALUE('testUser3', 1, 3, STR_TO_DATE('10-02-2020', '%d-%m-%Y'), STR_TO_DATE('25-02-2020', '%d-%m-%Y'), TRUE);
INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, renterCanceled)
    VALUE('testUser3', 3, 3, STR_TO_DATE('25-02-2020', '%d-%m-%Y'), STR_TO_DATE('27-02-2020', '%d-%m-%Y'), TRUE);
INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate)
    VALUE('testUser4', 2, 4, STR_TO_DATE('28-05-2020', '%d-%m-%Y'), STR_TO_DATE('01-06-2020', '%d-%m-%Y'));

-- Review
INSERT INTO Review(bookingID, reviewerID, rating, review)
    VALUE(1, 'testUser3', 4, 'Good Stuff love the huge mansion');

DELIMITER |
DROP TRIGGER IF EXISTS check_reviewer |
CREATE TRIGGER check_reviewer BEFORE INSERT ON Review FOR EACH ROW
BEGIN
    DECLARE hostID CHAR(30);
    DECLARE rentID CHAR(30);
    DECLARE fin BOOL DEFAULT FALSE;
    SET hostID = (SELECT owner FROM Listing WHERE
            ID=(SELECT listingID FROM Booked WHERE ID=NEW.bookingID));
    SET rentID = (SELECT renterID FROM booked WHERE ID=NEW.bookingID);
    SET fin = (SELECT finished FROM booked WHERE ID=NEW.bookingID);

    IF NEW.reviewerID <> rentID AND NEW.reviewerID <> hostID THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'reviewer is not renter or host';
    END IF;
    IF NOT fin THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'renter has not finished stay';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_user_age |
CREATE TRIGGER check_user_age BEFORE INSERT ON User FOR EACH ROW
BEGIN
    DECLARE age INT;
    SET age = DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), NEW.dob)), '%Y')+0;
    IF age < 18 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'user under 18';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_availabilities_overlap |
CREATE TRIGGER check_availabilities_overlap BEFORE INSERT ON Availabilities FOR EACH ROW
BEGIN
    DECLARE overlapavaliable INT;
    DECLARE overlapbooked INT;
    SET overlapbooked = (SELECT COUNT(ID) FROM Booked WHERE
        (NEW.startDate < endDate) AND
        (NEW.endDate > startDate) AND
        (NEW.listingID = listingID) AND
        NOT renterCanceled AND
        NOT hostCanceled LIMIT 1);

    SET overlapavaliable = (SELECT COUNT(ID) FROM Availabilities WHERE
        (NEW.startDate < endDate ) AND
        (NEW.endDate > startDate)  AND
        (NEW.listingID = listingID) AND
        NOT (ID=NEW.ID) LIMIT 1);
    IF overlapbooked > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'insert date conflicts with current booking';
    END IF;
    IF overlapavaliable > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'insert date conflicts with current availabilities';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_availabilities_update |
CREATE TRIGGER check_availabilities_update BEFORE UPDATE ON Availabilities FOR EACH ROW
BEGIN
    DECLARE overlapavaliable INT;
    DECLARE overlapbooked INT;
    SET overlapbooked = (SELECT COUNT(ID) FROM Booked WHERE
        (NEW.startDate < endDate)  AND
        (NEW.endDate > startDate) AND
        (NEW.listingID = listingID) AND
        NOT renterCanceled AND
        NOT hostCanceled LIMIT 1);

    SET overlapavaliable = (SELECT COUNT(ID) FROM Availabilities WHERE (NEW.startDate < endDate) AND
        (NEW.endDate > startDate)  AND
        (NEW.listingID = listingID) AND
        NOT (ID=OLD.ID) LIMIT 1);
    IF overlapbooked > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'update date conflicts with current booking';
    END IF;

    IF overlapavaliable > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'update date conflicts with current availabilities';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_update |
CREATE TRIGGER check_booking_update BEFORE UPDATE ON Booked FOR EACH ROW
BEGIN
    IF NEW.price <> OLD.price THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot change price of booking';
    END IF;
    IF OLD.finished THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot update finished booking';
    END IF;
    IF OLD.renterCanceled OR OLD.hostCanceled THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot update canceled booking';
    END IF;
    IF OLD.endDate <> NEW.endDate OR OLD.startDate <> NEW.startDate THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot change date of booking';
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_after_update |
CREATE TRIGGER check_booking_after_update AFTER UPDATE ON Booked FOR EACH ROW
BEGIN
    DECLARE Id1 INT;
    DECLARE Id2 INT;
    DECLARE newStartDate DATE;
    DECLARE newEndDate DATE;
    -- update availabilities if host or renter cancel
    IF NEW.renterCanceled OR NEW.hostCanceled THEN
        SET newStartDate = NEW.startDate;
        SET newEndDate = NEW.endDate;
        SET Id1 = (SELECT ID FROM Availabilities WHERE listingID=NEW.listingID AND endDate=NEW.startDate AND price=NEW.price);
        SET Id2 = (SELECT ID FROM Availabilities WHERE listingID=NEW.listingID AND startDate=NEW.endDate AND price=NEW.price);
        IF Id1 IS NOT NULL THEN
            SET newStartDate = (SELECT startDate FROM Availabilities WHERE ID=Id1);
            DELETE FROM Availabilities WHERE ID=Id1;
        END IF;
        IF Id2 IS NOT NULL THEN
            SET newEndDate = (SELECT endDate FROM Availabilities WHERE ID=Id2);
            DELETE FROM Availabilities WHERE ID=Id2;
        END IF;
        INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
            (NEW.listingID, newStartDate, newEndDate, NEW.price);
    END IF;
END |
DELIMITER ;

DELIMITER |
DROP TRIGGER IF EXISTS check_booking_valid |
CREATE TRIGGER check_booking_valid BEFORE INSERT ON Booked FOR EACH ROW
BEGIN
    DECLARE ownsHome BOOL;
    DECLARE ownsPayment BOOL;
    DECLARE priorBooking BOOL;
    DECLARE aID, alist INT;
    DECLARE aStart, aEnd DATE;
    DECLARE aPrice DOUBLE;
    SELECT ID, listingID, startDate, endDate, price
    INTO
        aID, aList, aStart, aEnd, aPrice
    FROM Availabilities WHERE listingID=NEW.listingID AND
            startDate <= NEW.startDate AND
            NEW.endDate <= endDate
    LIMIT 1;
    IF NEW.price <> 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot set price bookings';
    END IF;
    IF aID IS NULL THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'no available bookings';
    END IF;
    SET priorBooking = (SELECT COUNT(ID) FROM Booked
                        WHERE renterID=NEW.renterID AND
                            (NEW.startDate < endDate) AND
                            (NEW.endDate > startDate)  AND
                            NOT renterCanceled AND
                            NOT hostCanceled
                        LIMIT 1);
    IF priorBooking > 0 THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'prior booking conflicting';
    END IF;
    SET ownsHome = (SELECT owner FROM Listing WHERE ID=NEW.listingID) = NEW.renterID;
    IF ownsHome THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot book home you own';
    END IF;
    SET ownsPayment = (SELECT owner FROM PaymentInfo WHERE ID=NEW.paymentInfo) = NEW.renterID;
    IF NOT ownsPayment THEN
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'cannot pay using payment you do not own';
    END IF;
    IF (aStart <= NEW.startDate)  AND  (NEW.endDate <= aEnd) THEN
        SET NEW.price = aPrice;
        DELETE FROM Availabilities WHERE ID=aID;
        -- calculate new availabilities given booking
        IF aStart <= NEW.startDate THEN
            INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
                (alist, aStart, NEW.startDate, aPrice);
        END IF;
        IF NEW.endDate <= aEnd THEN
            INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES
                (alist, NEW.endDate, aEnd, aPrice);
        END IF;
    END IF;
END |
DELIMITER ;


-- reviewer is not renter or host testcase

# INSERT INTO Review(bookingID, reviewerID, rating, review)
# VALUE(1, 'testUser2', 4, 'Good Stuff love the huge mansion');

-- renter has not finished stay
# INSERT INTO Review(bookingID, reviewerID, rating, review)
# VALUE(2, 'testUser4', 4, 'Good Stuff love the huge mansion');

-- user under 18
# INSERT INTO User(userName, name, postalCode, city, country, sin, dob)
# VALUES ('testUser10', 'peter', 'M2N0H6', 'toronto', 'CANADA', '343569414',  STR_TO_DATE('27-12-2020', '%d-%m-%Y'));

-- can update availabilities
# UPDATE Availabilities
# SET endDate=STR_TO_DATE('28-07-2020', '%d-%m-%Y')
# WHERE ID=2;

-- cannot update availabilities (date conflicts with current booking)
# UPDATE Availabilities
# SET endDate=STR_TO_DATE('29-05-2020', '%d-%m-%Y')
# WHERE ID=15;

-- cannot update availabilities (date conflicts with current availabilities)
# UPDATE Availabilities
# SET startDate=STR_TO_DATE('26-04-2020', '%d-%m-%Y')
# WHERE ID=15;

-- cannot insert availabilities (date conflicts with current booking)
# INSERT INTO Availabilities(listingID, startDate, endDate, price)
# VALUES (2, STR_TO_DATE('29-05-2020', '%d-%m-%Y'),
#            STR_TO_DATE('30-05-2020', '%d-%m-%Y'), 3214);

-- cannot insert availabilities (date conflicts with current availabilities)
# INSERT INTO Availabilities(listingID, startDate, endDate, price)
# VALUES (2, STR_TO_DATE('25-05-2020', '%d-%m-%Y'),
#            STR_TO_DATE('28-05-2020', '%d-%m-%Y'), 3214);

-- cannot change price of booking
# UPDATE Booked
# SET price=3000
# WHERE ID=1;

-- cannot update finished booking
# UPDATE Booked
# SET endDate=STR_TO_DATE('26-02-2020', '%d-%m-%Y')
# WHERE ID=1;

-- cannot update canceled booking
# UPDATE Booked
# SET endDate=STR_TO_DATE('26-02-2020', '%d-%m-%Y')
# WHERE ID=2;

-- cancel a booking
-- (SELECT COUNT(*) FROM availabilities WHERE listingID=2) == 2;
# UPDATE Booked
# SET renterCanceled=TRUE
# WHERE ID=3;

-- no avaliable booking
# INSERT INTO Booked(renterID, listingID, paymentInfo, startDate, endDate, price)
#     VALUE('testUser3', 1, 3, STR_TO_DATE('10-02-2020', '%d-%m-%Y'), STR_TO_DATE('25-02-2020', '%d-%m-%Y'), 2000);

-- prior booking conflicting
# INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, price, renterCanceled)
#     VALUE('testUser3', 3, 3, STR_TO_DATE('10-02-2020', '%d-%m-%Y'), STR_TO_DATE('25-02-2020', '%d-%m-%Y'), 2000, TRUE);

-- cannot book home you own
# INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, price)
# VALUE('testUser1', 1, 1, STR_TO_DATE('28-05-2020', '%d-%m-%Y'), STR_TO_DATE('01-06-2020', '%d-%m-%Y'), 3000);

-- cannot pay using payment you do not own
# INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, price)
#     VALUE('testUser1', 5, 4, STR_TO_DATE('28-05-2020', '%d-%m-%Y'), STR_TO_DATE('01-06-2020', '%d-%m-%Y'), 3000);

-- cannot set price of booking
# INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, price)
# VALUE('testUser4', 2, 4, STR_TO_DATE('02-06-2020', '%d-%m-%Y'), STR_TO_DATE('03-06-2020', '%d-%m-%Y'), 3000);