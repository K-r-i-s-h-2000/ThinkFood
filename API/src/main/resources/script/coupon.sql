CREATE TABLE "coupon"" (
    id SERIAL PRIMARY KEY,
    coupon_code VARCHAR(50) UNIQUE,
    discount_percentage DECIMAL(5, 2)
);

ALTER TABLE "order"
ADD coupon_code VARCHAR(50);
