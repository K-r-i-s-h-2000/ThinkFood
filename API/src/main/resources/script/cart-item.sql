/*
   * "Author: Krishna C,"
   * "Purpose: Created cart_item table for storing the selected items by the user in a their cart,"
   * "User Story: TF-140"
   * "Dated: 26/10/2023"
*/
create table cart_item(
	version BIGINT,
	created_by VARCHAR(255),
	created_date DATE,
	created_date_time TIMESTAMP,
	last_modified_by VARCHAR(255),
	last_modified_date DATE,
	last_modified_date_time TIMESTAMP,
	id SERIAL primary key,
	restaurant_id INTEGER references restaurant(id),
	menu_id INTEGER references menu(id),
	cart_id INTEGER references cart(id),
	quantity int,
	subtotal numeric
);
alter table cart_item
drop column restaurant_id;

alter table cart_item
add column is_active BOOLEAN default false;