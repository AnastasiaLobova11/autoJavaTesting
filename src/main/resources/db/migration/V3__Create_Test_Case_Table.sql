CREATE TABLE test_case
(
    id        SERIAL      NOT NULL PRIMARY KEY,
    title     varchar(40) NOT NULL,
    className varchar(40) NOT NULL,
    UNIQUE (className)
);