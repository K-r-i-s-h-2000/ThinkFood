CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT,
    cart_id INT,
    coupon_id INT,
    order_date TIMESTAMP,
    total_cost DECIMAL(10, 2),
    coupon_code VARCHAR(50)
);
ALTER TABLE orders
ADD COLUMN version BIGINT,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date DATE,
ADD COLUMN created_date_time TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date DATE,
ADD COLUMN last_modified_date_time TIMESTAMP;

ALTER TABLE public.orders
ADD CONSTRAINT fk_cart_id
FOREIGN KEY (cart_id) REFERENCES public.cart(id);

ALTER TABLE orders
ADD COLUMN preparation_status VARCHAR(255) DEFAULT 'PREPARATION';
