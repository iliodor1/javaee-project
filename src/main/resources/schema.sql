DROP table if exists suppliers, products, orders, order_products;

CREATE TABLE IF NOT EXISTS suppliers
(
    id           bigint GENERATED ALWAYS AS IDENTITY primary key,
    company_name varchar(50) NOT NULL,
    country      varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id          bigint GENERATED ALWAYS AS IDENTITY primary key,
    name        varchar(50)                      NOT NULL,
    quantity    int                              NOT NULL,
    price       decimal check ( price > 0 )      NOT NULL,
    supplier_id bigint check ( supplier_id > 0 ) NOT NULL REFERENCES suppliers (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders
(
    id         bigint GENERATED ALWAYS AS IDENTITY primary key,
    order_date date NOT NULL
);

CREATE TABLE IF NOT EXISTS order_products
(
    product_id bigint NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    order_id   bigint NOT NULL REFERENCES orders (id) ON DELETE CASCADE
);