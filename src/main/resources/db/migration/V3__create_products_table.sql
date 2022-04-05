CREATE TABLE test_app.products
(
    id          uuid           NOT NULL PRIMARY KEY,
    customer_id uuid           NOT NULL,
    title       varchar(255)   NOT NULL,
    description varchar(1024),
    price       decimal(10, 2) NOT NULL,
    is_deleted  boolean        NOT NULL DEFAULT false,
    created_at  timestamp      NOT NULL DEFAULT now(),
    modified_at timestamp,
    CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES test_app.customers (id)
);