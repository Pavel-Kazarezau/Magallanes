CREATE TABLE IF NOT EXISTS trips
(
    id         UUID UNIQUE NOT NULL,
    name       VARCHAR     NOT NULL,
    creator_id UUID        NOT NULL,
    country    VARCHAR     NOT NULL,
    city       VARCHAR     NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    status     VARCHAR     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (creator_id) REFERENCES users (id)
);