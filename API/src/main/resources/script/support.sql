/*
   * "Author: Agrah"
   * "Purpose: Created support table for storing queries and responses"
   * "User Story: TF-99"
   * "Dated: 2/11/2023"
*/
CREATE TABLE support (
    id SERIAL PRIMARY KEY,
    query TEXT,
    response TEXT
);

alter table support
add column	created_by varchar(255) NULL,
add column	created_date date NULL,
add column	created_date_time timestamp NULL,
add column	last_modified_by varchar(255) NULL,
add column	last_modified_date date NULL,
add column	last_modified_date_time timestamp NULL,
add column	"version" int8 null;

ALTER table support
ADD COLUMN is_active BOOLEAN DEFAULT false;