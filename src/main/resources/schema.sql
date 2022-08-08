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
                         type  ENUM('Entire place', 'Private rooms', 'Hotel rooms', 'Shared rooms'),
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