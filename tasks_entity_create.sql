CREATE TABLE tasks (
                      task_id serial PRIMARY KEY,
                      task VARCHAR (50) NOT NULL,
                      comment VARCHAR(255),
                      status CHAR (3) NOT NULL CONSTRAINT statusConst
                          CHECK(status IN('clo', 'act', 'can')),
                      week INT NOT NULL CONSTRAINT weekConst
                          CHECK(week >1 AND week <=52),
                      city VARCHAR(10),
                      date_create TIMESTAMP NOT NULL,
                      date_due TIMESTAMP,
                      date_close TIMESTAMP);

ALTER TABLE tasks
    add date_close DATE default '2023-03-20 00:00:00';

ALTER TABLE tasks
    add status CHAR (1) NOT NULL CONSTRAINT statusConst
        CHECK(status IN('a', 'x', 'c')) default 'a';

ALTER TABLE tasks
    add responsible VARCHAR(50) default 'Roman';

ALTER TABLE tasks
    add user_name VARCHAR(50) NOT NULL default 'roman.stupko';

CREATE TABLE tasks (
                       task_id serial PRIMARY KEY,
                       task VARCHAR (50) NOT NULL,
                       comment VARCHAR(255),
                       responsible VARCHAR(50),
                       status CHAR (1) NOT NULL CONSTRAINT statusConst
                           CHECK(status IN('a', 'x', 'c')),
                       week INT NOT NULL CONSTRAINT weekConst
                           CHECK(week >1 AND week <=52),
                       date_create DATE NOT NULL,
                       date_close DATE,
                       date_due DATE NOT NULL,
                       user_name VARCHAR(50) NOT NULL);