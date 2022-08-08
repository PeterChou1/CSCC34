

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