/*
   * "Author: Sharon Sam,"
   * "Purpose: The "menu" table serves to store and manage information about menu items offered by restaurants"
   * "User Story: TF-75"
   * "Dated: 26/10/2023"
*/
CREATE TABLE menu (
    id SERIAL PRIMARY KEY,
    restaurant_id INTEGER REFERENCES restaurant(id),
    item_id INTEGER REFERENCES item(id),
    item_description TEXT,
    item_price DOUBLE PRECISION,
    preparation_time INTEGER,
    item_availability BOOLEAN,
    version BIGINT,
    created_by VARCHAR(255),
    created_date DATE,
    created_date_time TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date DATE,
    last_modified_date_time TIMESTAMP
);


ALTER TABLE menu
ADD COLUMN is_active BOOLEAN DEFAULT false;