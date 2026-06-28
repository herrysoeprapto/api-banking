CREATE TABLE customer (
    id SERIAL NOT NULL CONSTRAINT customer_pk PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);
