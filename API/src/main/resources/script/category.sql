/*
   * "Author: Agrah"
   * "Purpose: Created "category" table for storing the list of category names"
   * "User Story: TF-98"
   * "Dated: 26/10/2023"
*/

CREATE TABLE category (
    id serial PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

alter table category
add column	created_by varchar(255) NULL,
add column	created_date date NULL,
add column	created_date_time timestamp NULL,
add column	last_modified_by varchar(255) NULL,
add column	last_modified_date date NULL,
add column	last_modified_date_time timestamp NULL,
add column	"version" int8 null;

ALTER table category
ADD COLUMN is_active BOOLEAN DEFAULT false;