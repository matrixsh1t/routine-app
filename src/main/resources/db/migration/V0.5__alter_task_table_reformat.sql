
DROP TABLE tasks;
CREATE TABLE tasks (
                       task_id serial PRIMARY KEY,
                       task VARCHAR (150) NOT NULL,
                       comment VARCHAR(255),
                       city VARCHAR(50),
                       status CHAR (1) NOT NULL CONSTRAINT statusConst
                           CHECK(status IN('a', 'x', 'c')),
                       date_create DATE NOT NULL,
                       date_due DATE NOT NULL,
                       date_close DATE,
                       account_id INT NOT NUll,
                           constraint account_id_fk
                           foreign key (account_id) references account (account_id)
                           );