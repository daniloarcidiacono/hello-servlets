INSERT INTO books(isbn, title, year, link) VALUES('1234', 't1', 1000, null);
INSERT INTO authors(id, name) VALUES(1, 'a1');
INSERT INTO authors(id, name) VALUES(2, 'a2');
INSERT INTO authors(id, name) VALUES(3, 'a3');
INSERT INTO books_authors(book_id, author_id) VALUES('1234', 1);
INSERT INTO books_authors(book_id, author_id) VALUES('1234', 2);
