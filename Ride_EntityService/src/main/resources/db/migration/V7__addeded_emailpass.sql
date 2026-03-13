ALTER TABLE driver
    ADD email VARCHAR(255) NULL;

ALTER TABLE driver
    ADD password VARCHAR(255) NULL;

ALTER TABLE driver
    MODIFY email VARCHAR (255) NOT NULL;

ALTER TABLE driver
    MODIFY password VARCHAR (255) NOT NULL;

ALTER TABLE booking
DROP
COLUMN booking_status;

ALTER TABLE booking
    ADD booking_status VARCHAR(255) NULL;

ALTER TABLE car
DROP
COLUMN car_type;

ALTER TABLE car
    ADD car_type VARCHAR(255) NULL;

ALTER TABLE driver
DROP
COLUMN driver_approval_status;

ALTER TABLE driver
    ADD driver_approval_status VARCHAR(255) NULL;