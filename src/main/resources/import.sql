INSERT INTO categories (name) VALUES ('Fiction');
INSERT INTO categories (name) VALUES ('Science Fiction');
INSERT INTO categories (name) VALUES ('Fantasy');
INSERT INTO categories (name) VALUES ('Mystery');

INSERT INTO authors (name) VALUES ('George Orwell');
INSERT INTO authors (name) VALUES ('Isaac Asimov');
INSERT INTO authors (name) VALUES ('J.K. Rowling');
INSERT INTO authors (name) VALUES ('J.R.R. Tolkien');
INSERT INTO authors (name) VALUES ('Agatha Christie');
INSERT INTO authors (name) VALUES ('Arthur Conan Doyle');

-- George Orwell Books
INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780451524935', '1984', 'A dystopian novel set in a totalitarian society under constant surveillance.', 328);
INSERT INTO book_category (book_id, category_id) VALUES (1, 1); -- Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780451526342', 'Animal Farm', 'A political allegory depicting the events of the Russian Revolution.', 112);
INSERT INTO book_category (book_id, category_id) VALUES (2, 1); -- Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780151072552', 'Homage to Catalonia', 'A personal account of George Orwell''s experiences in the Spanish Civil War.', 232);
INSERT INTO book_category (book_id, category_id) VALUES (3, 1); -- Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780452284241', 'Down and Out in Paris and London', 'A memoir of Orwell''s experience with poverty.', 213);
INSERT INTO book_category (book_id, category_id) VALUES (4, 1); -- Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780156612069', 'The Road to Wigan Pier', 'An investigation into the living conditions of the working class in the north of England.', 215);
INSERT INTO book_category (book_id, category_id) VALUES (5, 1); -- Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (1, '9780451526342', 'Burmese Days', 'A story set in British Burma during the colonial period.', 287);
INSERT INTO book_category (book_id, category_id) VALUES (6, 1); -- Fiction

-- Isaac Asimov Books
INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780553293357', 'Foundation', 'A science fiction novel about the collapse and rebirth of a galactic empire.', 296);
INSERT INTO book_category (book_id, category_id) VALUES (7, 2); -- Science Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780553288100', 'The Gods Themselves', 'A novel exploring parallel universes and the implications of different forms of energy.', 288);
INSERT INTO book_category (book_id, category_id) VALUES (8, 2); -- Science Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780553294385', 'The Caves of Steel', 'A detective novel set in a future where humans coexist with robots.', 270);
INSERT INTO book_category (book_id, category_id) VALUES (9, 2); -- Science Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780553293623', 'The Naked Sun', 'A sequel to The Caves of Steel, exploring a murder mystery on a distant planet.', 240);
INSERT INTO book_category (book_id, category_id) VALUES (10, 2); -- Science Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780385177252', 'I, Robot', 'A collection of short stories that form the basis of Asimov''s Robot series.', 256);
INSERT INTO book_category (book_id, category_id) VALUES (11, 2); -- Science Fiction

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (2, '9780553278392', 'Pebble in the Sky', 'A science fiction novel about a man who is transported millions of years into the future.', 256);
INSERT INTO book_category (book_id, category_id) VALUES (12, 2); -- Science Fiction

-- J.K. Rowling Books
INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545582889', 'Harry Potter and the Sorcerer''s Stone', 'The first book in the Harry Potter series, introducing a young wizard and his adventures.', 309);
INSERT INTO book_category (book_id, category_id) VALUES (13, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545582933', 'Harry Potter and the Chamber of Secrets', 'The second book in the Harry Potter series, where Harry faces new dangers at Hogwarts.', 341);
INSERT INTO book_category (book_id, category_id) VALUES (14, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545582957', 'Harry Potter and the Prisoner of Azkaban', 'The third book in the Harry Potter series, where Harry discovers more about his past.', 435);
INSERT INTO book_category (book_id, category_id) VALUES (15, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545582988', 'Harry Potter and the Goblet of Fire', 'The fourth book in the Harry Potter series, featuring the Triwizard Tournament.', 734);
INSERT INTO book_category (book_id, category_id) VALUES (16, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545583008', 'Harry Potter and the Order of the Phoenix', 'The fifth book in the Harry Potter series, where Harry leads a secret student group.', 870);
INSERT INTO book_category (book_id, category_id) VALUES (17, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (3, '9780545583022', 'Harry Potter and the Half-Blood Prince', 'The sixth book in the Harry Potter series, focusing on the rise of Lord Voldemort.', 652);
INSERT INTO book_category (book_id, category_id) VALUES (18, 3); -- Fantasy

-- J.R.R. Tolkien Books
INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928227', 'The Hobbit', 'A fantasy novel about the adventures of Bilbo Baggins in Middle-earth.', 310);
INSERT INTO book_category (book_id, category_id) VALUES (19, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928203', 'The Fellowship of the Ring', 'The first book in The Lord of the Rings trilogy, where the journey to destroy the One Ring begins.', 423);
INSERT INTO book_category (book_id, category_id) VALUES (20, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928210', 'The Two Towers', 'The second book in The Lord of the Rings trilogy, following the continued quest to destroy the One Ring.', 352);
INSERT INTO book_category (book_id, category_id) VALUES (21, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928227', 'The Return of the King', 'The final book in The Lord of the Rings trilogy, concluding the epic journey of the Fellowship.', 416);
INSERT INTO book_category (book_id, category_id) VALUES (22, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928234', 'The Silmarillion', 'A collection of mythopoeic stories that provide the background to the events in The Lord of the Rings.', 386);
INSERT INTO book_category (book_id, category_id) VALUES (23, 3); -- Fantasy

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (4, '9780547928241', 'Unfinished Tales', 'A collection of stories and essays that provide additional background to The Lord of the Rings.', 512);
INSERT INTO book_category (book_id, category_id) VALUES (24, 3); -- Fantasy

-- Agatha Christie Books
INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073488', 'Murder on the Orient Express', 'A detective novel where Hercule Poirot solves a murder on a train.', 256);
INSERT INTO book_category (book_id, category_id) VALUES (25, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073511', 'The ABC Murders', 'A mystery novel where Hercule Poirot faces a serial killer targeting victims alphabetically.', 272);
INSERT INTO book_category (book_id, category_id) VALUES (26, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073535', 'And Then There Were None', 'A mystery novel where ten strangers are invited to an isolated island, only to be murdered one by one.', 272);
INSERT INTO book_category (book_id, category_id) VALUES (27, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073542', 'The Murder of Roger Ackroyd', 'A detective novel featuring Hercule Poirot and a shocking twist ending.', 312);
INSERT INTO book_category (book_id, category_id) VALUES (28, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073566', 'The Mysterious Affair at Styles', 'The first novel featuring Hercule Poirot, solving a murder in a country manor.', 296);
INSERT INTO book_category (book_id, category_id) VALUES (29, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description, pages) VALUES (5, '9780062073580', 'Death on the Nile', 'A detective novel where Hercule Poirot solves a murder during a cruise on the Nile River.', 336);
INSERT INTO book_category (book_id, category_id) VALUES (30, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528018', 'A Study in Scarlet', 'The first novel featuring Sherlock Holmes, where he solves a murder mystery in London.',332);
INSERT INTO book_category (book_id, category_id) VALUES (31, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528278', 'The Hound of the Baskervilles', 'A mystery novel where Sherlock Holmes investigates a legend of a supernatural hound.',401);
INSERT INTO book_category (book_id, category_id) VALUES (32, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528223', 'The Sign of the Four', 'A mystery novel where Sherlock Holmes unravels a complex plot involving stolen treasure.',289);
INSERT INTO book_category (book_id, category_id) VALUES (33, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528230', 'The Adventures of Sherlock Holmes', 'A collection of short stories featuring Sherlock Holmes solving various cases.',512);
INSERT INTO book_category (book_id, category_id) VALUES (34, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528254', 'The Memoirs of Sherlock Holmes', 'A collection of stories detailing more of Sherlock Holmes'' adventures.',276);
INSERT INTO book_category (book_id, category_id) VALUES (35, 4); -- Mystery

INSERT INTO books (author_id, isbn, title, description,pages) VALUES (6, '9780451528322', 'The Return of Sherlock Holmes', 'A collection of stories where Sherlock Holmes returns after being presumed dead.',451);
INSERT INTO book_category (book_id, category_id) VALUES (36, 4); -- Mystery

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 1);
INSERT INTO copies (available, book_id) VALUES (true, 1);
INSERT INTO copies (available, book_id) VALUES (true, 2);
INSERT INTO copies (available, book_id) VALUES (true, 2);
INSERT INTO copies (available, book_id) VALUES (true, 3);
INSERT INTO copies (available, book_id) VALUES (true, 3);
INSERT INTO copies (available, book_id) VALUES (true, 4);
INSERT INTO copies (available, book_id) VALUES (true, 4);
INSERT INTO copies (available, book_id) VALUES (true, 5);
INSERT INTO copies (available, book_id) VALUES (true, 5);
INSERT INTO copies (available, book_id) VALUES (true, 6);
INSERT INTO copies (available, book_id) VALUES (true, 6);

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 7);
INSERT INTO copies (available, book_id) VALUES (true, 7);
INSERT INTO copies (available, book_id) VALUES (true, 8);
INSERT INTO copies (available, book_id) VALUES (true, 8);
INSERT INTO copies (available, book_id) VALUES (true, 9);
INSERT INTO copies (available, book_id) VALUES (true, 9);
INSERT INTO copies (available, book_id) VALUES (true, 10);
INSERT INTO copies (available, book_id) VALUES (true, 10);
INSERT INTO copies (available, book_id) VALUES (true, 11);
INSERT INTO copies (available, book_id) VALUES (true, 11);
INSERT INTO copies (available, book_id) VALUES (true, 12);
INSERT INTO copies (available, book_id) VALUES (true, 12);

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 13);
INSERT INTO copies (available, book_id) VALUES (true, 13);
INSERT INTO copies (available, book_id) VALUES (true, 14);
INSERT INTO copies (available, book_id) VALUES (true, 14);
INSERT INTO copies (available, book_id) VALUES (true, 15);
INSERT INTO copies (available, book_id) VALUES (true, 15);
INSERT INTO copies (available, book_id) VALUES (true, 16);
INSERT INTO copies (available, book_id) VALUES (true, 16);
INSERT INTO copies (available, book_id) VALUES (true, 17);
INSERT INTO copies (available, book_id) VALUES (true, 17);
INSERT INTO copies (available, book_id) VALUES (true, 18);
INSERT INTO copies (available, book_id) VALUES (true, 18);

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 19);
INSERT INTO copies (available, book_id) VALUES (true, 19);
INSERT INTO copies (available, book_id) VALUES (true, 20);
INSERT INTO copies (available, book_id) VALUES (true, 20);
INSERT INTO copies (available, book_id) VALUES (true, 21);
INSERT INTO copies (available, book_id) VALUES (true, 21);
INSERT INTO copies (available, book_id) VALUES (true, 22);
INSERT INTO copies (available, book_id) VALUES (true, 22);
INSERT INTO copies (available, book_id) VALUES (true, 23);
INSERT INTO copies (available, book_id) VALUES (true, 23);
INSERT INTO copies (available, book_id) VALUES (true, 24);
INSERT INTO copies (available, book_id) VALUES (true, 24);

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 25);
INSERT INTO copies (available, book_id) VALUES (true, 25);
INSERT INTO copies (available, book_id) VALUES (true, 26);
INSERT INTO copies (available, book_id) VALUES (true, 26);
INSERT INTO copies (available, book_id) VALUES (true, 27);
INSERT INTO copies (available, book_id) VALUES (true, 27);
INSERT INTO copies (available, book_id) VALUES (true, 28);
INSERT INTO copies (available, book_id) VALUES (true, 28);
INSERT INTO copies (available, book_id) VALUES (true, 29);
INSERT INTO copies (available, book_id) VALUES (true, 29);
INSERT INTO copies (available, book_id) VALUES (true, 30);
INSERT INTO copies (available, book_id) VALUES (true, 30);

-- Cópias
INSERT INTO copies (available, book_id) VALUES (true, 31);
INSERT INTO copies (available, book_id) VALUES (true, 31);
INSERT INTO copies (available, book_id) VALUES (true, 32);
INSERT INTO copies (available, book_id) VALUES (true, 32);
INSERT INTO copies (available, book_id) VALUES (true, 33);
INSERT INTO copies (available, book_id) VALUES (true, 33);
INSERT INTO copies (available, book_id) VALUES (true, 34);
INSERT INTO copies (available, book_id) VALUES (true, 34);
INSERT INTO copies (available, book_id) VALUES (true, 35);
INSERT INTO copies (available, book_id) VALUES (true, 35);
INSERT INTO copies (available, book_id) VALUES (true, 36);
INSERT INTO copies (available, book_id) VALUES (true, 36);

INSERT INTO addresses (bairro, cep, complemento, logradouro, numero, cidade, estado) VALUES ('Centro', '01001-000', 'Apt 101', 'Rua A', '123','Porto Alegre','RS');
INSERT INTO addresses (bairro, cep, complemento, logradouro, numero, cidade, estado) VALUES ('Vila Nova', '02002-000', 'Casa', 'Avenida B', '456','Porto Alegre','RS');

INSERT INTO users (birth_date, late_return, address_id, cpf, email, full_name, password) VALUES ('1990-05-15', 0, 1, '12345678901', 'alex@gmail.com', 'Alex Green', '$2a$10$iZJwrJbGrLiByGwD/eiE0u/SPiICKFbVmTLTfVfM34duTf7zU/C/m');
INSERT INTO users (birth_date, late_return, address_id, cpf, email, full_name, password) VALUES ('1985-08-20', 1, 2, '98765432100', 'ana@gmail.com', 'Ana Brown', '$2a$10$iZJwrJbGrLiByGwD/eiE0u/SPiICKFbVmTLTfVfM34duTf7zU/C/m');

INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');
INSERT INTO roles (authority) VALUES ('ROLE_USER');

INSERT INTO user_role (role_id, user_id) VALUES (1, 1);  
INSERT INTO user_role (role_id, user_id) VALUES (2, 1);  
INSERT INTO user_role (role_id, user_id) VALUES (2, 2);

INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-01', '2024-08-01', TRUE, '2024-08-29', 1, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-02', '2024-08-02', TRUE, '2024-08-30', 2, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-03', '2024-08-03', TRUE, '2024-08-31', 3, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-04', '2024-08-04', TRUE, '2024-09-02', 4, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-05', '2024-08-05', TRUE, '2024-09-03', 5, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-06', '2024-08-06', FALSE, NULL, 6, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-07', '2024-08-07', FALSE, NULL, 7, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-08', '2024-08-08', TRUE, '2024-09-06', 8, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-09', '2024-08-09', TRUE, '2024-09-07', 9, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-10', '2024-08-10', FALSE, NULL, 10, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-11', '2024-08-11', TRUE, '2024-09-09', 11, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-12', '2024-08-12', TRUE, '2024-09-10', 12, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-13', '2024-08-13', FALSE, NULL, 13, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-14', '2024-08-14', TRUE, '2024-09-12', 14, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-15', '2024-08-15', TRUE, '2024-09-13', 15, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-16', '2024-08-16', FALSE, NULL, 16, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-17', '2024-08-17', TRUE, '2024-09-15', 17, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-18', '2024-08-18', TRUE, '2024-09-16', 18, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-19', '2024-08-19', FALSE, NULL, 19, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-20', '2024-08-20', TRUE, '2024-09-18', 20, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-21', '2024-08-21', TRUE, '2024-09-19', 21, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-22', '2024-08-22', FALSE, NULL, 22, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-23', '2024-08-23', TRUE, '2024-09-21', 23, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-24', '2024-08-24', TRUE, '2024-09-22', 24, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-25', '2024-08-25', FALSE, NULL, 25, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-26', '2024-08-26', TRUE, '2024-09-24', 26, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-27', '2024-08-27', TRUE, '2024-09-25', 27, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-28', '2024-08-28', FALSE, NULL, 28, 2);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-29', '2024-08-29', TRUE, '2024-09-27', 29, 1);
INSERT INTO loans (date_to_return, loan_date, returned, returned_at, copy_id, user_id) VALUES ('2024-09-30', '2024-08-30', TRUE, '2024-09-28', 30, 2);

