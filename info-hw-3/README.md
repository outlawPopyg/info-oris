## SQL-коды для тестов
```sql
create table author
(
    id   bigserial primary key,
    name varchar(20)
);

create table book
(
    id               bigserial primary key,
    name             varchar(100),
    publication_date integer,
    publishing       varchar(20)
);

create table book_author
(
    author_id bigint references author,
    book_id   bigint references book,
    id        bigserial
);

INSERT INTO public.author (id, name) VALUES (1, 'Роберт Мартин');
INSERT INTO public.author (id, name) VALUES (2, 'Роберт Сэджвик');
INSERT INTO public.author (id, name) VALUES (3, 'Джошуа Блох');

INSERT INTO public.book (id, name, publication_date, publishing) VALUES (4, 'Алгоритмы на C++', 1995, 'Pifagor');
INSERT INTO public.book (id, name, publication_date, publishing) VALUES (3, 'Эффективное программирование на Java', 2019, 'Piter');
INSERT INTO public.book (id, name, publication_date, publishing) VALUES (2, 'Алгоритмы на Java', 2014, 'Piter');
INSERT INTO public.book (id, name, publication_date, publishing) VALUES (1, 'Чистый код', 2022, 'Manning');


INSERT INTO public.book_author (id, author_id, book_id) VALUES (1, 1, 1);
INSERT INTO public.book_author (id, author_id, book_id) VALUES (2, 2, 2);
INSERT INTO public.book_author (id, author_id, book_id) VALUES (3, 3, 3);
INSERT INTO public.book_author (id, author_id, book_id) VALUES (4, 2, 4);


```