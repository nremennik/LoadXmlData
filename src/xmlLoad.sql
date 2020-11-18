DROP TABLE transactions;
DROP TABLE client;

CREATE TABLE client
(
    inn        NUMBER        NOT NULL,
    firstname  VARCHAR2(200) NOT NULL,
    lastname   VARCHAR2(200) NOT NULL,
    middlename VARCHAR2(200) NOT NULL
);

CREATE UNIQUE INDEX client_inn_idx ON client (inn);
ALTER TABLE client
    ADD CONSTRAINT client_pk PRIMARY KEY (inn);

-- Need unique constraint condition to prevent duplicate transactions
CREATE TABLE transactions
(
    id       NUMBER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    inn      NUMBER                                  NOT NULL,
    place    VARCHAR2(500)                           NOT NULL,
    amount   NUMBER(*, 2)                            NOT NULL,
    currency VARCHAR2(500)                           NOT NULL,
    card     VARCHAR2(100)                           NOT NULL,

    CONSTRAINT transactions_client_fk FOREIGN KEY (inn) REFERENCES client (inn)
);

ALTER TABLE transactions
    ADD CONSTRAINT transactions_pk PRIMARY KEY (id);

SELECT *
FROM client;

SELECT *
FROM transactions;