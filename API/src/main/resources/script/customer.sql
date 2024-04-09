/*
   * "Author: ajay.S,"
   * "Purpose: The "customer" table records essential information about customers"
   * "User Story: TF-80"
   * "Dated: 31/10/2023"
*/
CREATE TABLE customer (
    customer_id serial PRIMARY KEY,
    address VARCHAR(255),
    longitude DOUBLE PRECISION,
    latitude DOUBLE PRECISION,
    date_of_birth DATE,
    gender VARCHAR(10)
);
ALTER TABLE customer
ADD COLUMN version BIGINT,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date DATE,
ADD COLUMN created_date_time TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date DATE,
ADD COLUMN last_modified_date_time TIMESTAMP;

-- Add the user_id column to the customer table
ALTER TABLE customer
ADD COLUMN user_id INT;

-- Add a foreign key constraint to reference the "users" table
ALTER TABLE customer
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id) REFERENCES users(id);
alter table customer add column is_active BOOLEAN default false;

alter table users  add column authentication_token varchar(50);
ALTER TABLE users
ALTER COLUMN authentication_token TYPE VARCHAR(256);
