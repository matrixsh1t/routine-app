CREATE TABLE IF NOT EXISTS tags (
   tag_id serial PRIMARY KEY,
   tag_name varchar(50)
);

CREATE TABLE IF NOT EXISTS task_tags (
    task_id INT,
    tag_id INT,
    PRIMARY KEY (task_id, tag_id),
    FOREIGN KEY (task_id) REFERENCES tasks (task_id),
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id)
);