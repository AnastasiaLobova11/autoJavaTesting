CREATE TABLE test_case
(
    id        SERIAL      NOT NULL PRIMARY KEY,
    title     varchar(40) NOT NULL,
    class_name varchar(40) NOT NULL,
    UNIQUE (class_name)
);