/*
   * "Author: Agrah"
   * "Purpose: Created reviews_and_item table for storing reviews and ratings for a restaurant"
   * "User Story: TF-99"
   * "Dated: 31/10/2023"
*/

CREATE TABLE reviews_and_ratings (
    id serial PRIMARY KEY,
    customer_id INT,
    restaurant_id INT,
    review TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5)
);


ALTER TABLE reviews_and_ratings
    ADD CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id);

ALTER TABLE reviews_and_ratings
    ADD CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id);

alter table reviews_and_ratings
add column	created_by varchar(255) NULL,
add column	created_date date NULL,
add column	created_date_time timestamp NULL,
add column	last_modified_by varchar(255) NULL,
add column	last_modified_date date NULL,
add column	last_modified_date_time timestamp NULL,
add column	"version" int8 null;

ALTER table reviews_and_ratings
ADD COLUMN is_active BOOLEAN DEFAULT false;