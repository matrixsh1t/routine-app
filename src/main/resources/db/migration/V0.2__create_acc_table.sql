CREATE TABLE IF NOT EXISTS account (
                                       account_id serial PRIMARY KEY,
                                       username varchar(50) NOT NULL,
                                       password varchar(128) NOT NULL,
                                       email varchar(50) NOT NULL UNIQUE,
                                       role varchar(20) NOT NULL
);