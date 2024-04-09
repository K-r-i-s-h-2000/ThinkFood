/*
   * "Author: Agrah"
   * "Purpose: Created "item" table for storing the list of item names"
   * "User Story: TF-98"
   * "Dated: 26/10/2023"
*/
CREATE TABLE item (
    id serial PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    category_id integer REFERENCES category(id)
);

alter table item
add column	created_by varchar(255) NULL,
add column	created_date date NULL,
add column	created_date_time timestamp NULL,
add column	last_modified_by varchar(255) NULL,
add column	last_modified_date date NULL,
add column	last_modified_date_time timestamp NULL,
add column	"version" int8 null;

ALTER table item
ADD COLUMN is_active BOOLEAN DEFAULT false;