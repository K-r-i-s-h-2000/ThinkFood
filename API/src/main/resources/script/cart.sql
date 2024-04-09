/**
   * "Author: Krishna C,"
   * "Purpose: Created cart table for storing the selected items by the user,"
   * "User Story: TF-140"
   * "Dated: 26/10/2023"
*/
create table cart(
	version BIGINT,
	created_by VARCHAR(255),
	created_date DATE,
	created_date_time TIMESTAMP,
	last_modified_by VARCHAR(255),
	last_modified_date DATE,
	last_modified_date_time TIMESTAMP,
	id SERIAL primary key,
	user_id INTEGER references "user"(id),
	total_amount numeric,
);

alter table cart
add column cart_item_id INTEGER references cart_item(id);

alter table cart
drop column cart_item_id;

alter table cart
add column is_active BOOLEAN default false;