insert into suppliers(company_name, country) values ('Company1', 'Country1');
insert into suppliers(company_name, country) values ('Company2', 'Country2');
insert into suppliers(company_name, country) values ('Company3', 'Country3');

insert into products(product_name, quantity, price, supplier_id) values ('Name1', 1, 100, 1);
insert into products(product_name, quantity, price, supplier_id) values ('Name2', 2, 200, 1);
insert into products(product_name, quantity, price, supplier_id) values ('Name3', 3, 300, 2);

insert into orders(order_date) values (current_date);
insert into orders(order_date) values (current_date);
insert into orders(order_date) values (current_date);

insert into order_products(product_id, order_id) values (1, 1);
insert into order_products(product_id, order_id) values (2, 1);
insert into order_products(product_id, order_id) values (3, 1);