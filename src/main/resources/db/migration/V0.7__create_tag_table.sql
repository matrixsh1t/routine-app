CREATE TABLE IF NOT EXISTS tags (
   tag_id serial PRIMARY KEY NOT NULL,
   tag_name varchar(50)
);

CREATE TABLE IF NOT EXISTS task_tags (
    task_id int8 not null references tasks,
    tag_id int8 not null references tags,
    PRIMARY KEY (task_id, tag_id)
);
