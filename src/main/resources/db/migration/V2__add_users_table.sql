CREATE TABLE IF NOT EXISTS users
(
    id                      UUID      NOT NULL,
    username                VARCHAR   NOT NULL,
    email                   VARCHAR   NOT NULL,
    password                VARCHAR   NOT NULL,
    role                    VARCHAR   NOT NULL,
    created_at              TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP NOT NULL,
    account_non_expired     BOOLEAN   NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN   NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN   NOT NULL DEFAULT TRUE,
    enabled                 BOOLEAN   NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT mg_user_username_key UNIQUE (username);
ALTER TABLE users
    ADD CONSTRAINT mg_user_email_key UNIQUE (email);