CREATE TABLE account (
    id SERIAL NOT NULL CONSTRAINT account_pk PRIMARY KEY,
    customer_id SERIAL NOT NULL,
    account_number VARCHAR(16) NOT NULL,
    account_nickname VARCHAR(30),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);
