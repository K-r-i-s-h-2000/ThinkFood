/*
   * "Author: Sharon Sam,"
   * "Purpose: The "restaurant" table records essential information about restaurants"
   * "User Story: TF-75"
   * "Dated: 26/10/2023"
*/


CREATE TABLE restaurant (
    id SERIAL PRIMARY KEY,
    restaurant_name VARCHAR(255),
    restaurant_description TEXT,
    restaurant_latitude DOUBLE PRECISION,
    restaurant_longitude DOUBLE PRECISION,
    restaurant_contact VARCHAR(255),
    restaurant_email VARCHAR(255),
    restaurant_availability BOOLEAN
);

ALTER TABLE restaurant
ADD COLUMN version BIGINT,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date DATE,
ADD COLUMN created_date_time TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date DATE,
ADD COLUMN last_modified_date_time TIMESTAMP;

ALTER TABLE restaurant
ADD COLUMN user_id INT;

ALTER TABLE restaurant
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE restaurant
ADD COLUMN is_active BOOLEAN DEFAULT false;



