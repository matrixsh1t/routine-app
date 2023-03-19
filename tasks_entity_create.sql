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
    add perform_date TIMESTAMP NOT NULL default '2023-03-20 00:00:00';