/*
   * "Author: ajay.S,"
   * "Purpose: The "user" table records essential information about users"
   * "User Story: TF-80"
   * "Dated: 31/10/2023"
*/
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
   );
   ALTER TABLE users
   ADD COLUMN version BIGINT,
   ADD COLUMN created_by VARCHAR(255),
   ADD COLUMN created_date DATE,
   ADD COLUMN created_date_time TIMESTAMP,
   ADD COLUMN last_modified_by VARCHAR(255),
   ADD COLUMN last_modified_date DATE,
   ADD COLUMN last_modified_date_time TIMESTAMP;
   alter table USERS add column is_active BOOLEAN default false;