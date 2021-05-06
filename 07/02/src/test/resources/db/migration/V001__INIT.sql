CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       title varchar(255) not null
);
insert into books (title) values ('Test Book');