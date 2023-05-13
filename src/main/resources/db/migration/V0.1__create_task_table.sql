CREATE TABLE IF NOT EXISTS tasks (
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