CREATE TABLE images
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    type       VARCHAR(100) NOT NULL,
    country    VARCHAR      NOT NULL,
    city       VARCHAR      NOT NULL,
    data       bytea,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL
);
