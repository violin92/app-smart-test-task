CREATE SCHEMA IF NOT EXISTS test_app;

CREATE TABLE test_app.customers
(
    id          uuid         NOT NULL PRIMARY KEY,
    title       varchar(255) NOT NULL,
    is_deleted  boolean      NOT NULL DEFAULT false,
    created_at  timestamp    NOT NULL DEFAULT now(),
    modified_at timestamp
);