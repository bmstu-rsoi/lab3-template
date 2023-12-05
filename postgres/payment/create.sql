\c payments;

CREATE TABLE payment
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED')),
    price       INT         NOT NULL
);

GRANT ALL PRIVILEGES ON payment TO program;

CREATE SEQUENCE payment_id_seq;

GRANT ALL PRIVILEGES ON payment_id_seq TO program;

