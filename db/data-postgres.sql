INSERT INTO users VALUES (1,'admin','$2a$10$lEB1xrf8rfti00nDYDy1iuklqEr3tgILvnDZJX4DtMyo9MFxG4I7u','admin@test.com','admin',0,0,'Admin Address',TIMESTAMP '2021-09-20T12:22:31.028+00:00', NULL, 'ENABLED', 'ADMIN');
INSERT INTO users VALUES (2,'user','$2a$10$653wiS/s1aBMeEJUGJcS8eKXpM0V.XQtUJaud4hndTbCPv3lIlzJW','user@test.com','normal user',0,0,'User Address',TIMESTAMP '2021-09-22T02:39:20.593+00:00', NULL, 'ENABLED', 'USER_NORMAL');
INSERT INTO users VALUES (3,'business','$2a$10$JxfmJlvv9g1O7L3yVsKXie7aETfBVdO1rY/lOoZuavn2NjdHaDpi.','business@test.com','business user',0,0,'Business User Address',TIMESTAMP '2021-09-22T02:44:23.945+00:00', NULL, 'ENABLED', 'USER_BUSINESS');

INSERT INTO business_users VALUES (1, 3, 12345, 'business corp');

INSERT INTO requests VALUES (1, 1, 'request new bus', 'NEW_BUSINESS_USER', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (2, 2, 'request to reg', 'TO_REG_USER', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (3, 3, 'new book', 'NEW_BOOK_LISTING', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (4, 1, 'to bus', 'TO_BUSINESS_USER', '2004-10-19 10:23:54', NULL);

INSERT INTO books VALUES (1, 1, 3, 'The Storyteller: Tales of Life and Music', 'Actor & Entertainer Biographies', 'USED', 'Dave Grohl', 006307609, 23.99, 5, 1, 'PRINT_ON_DEMAND', 100, 'AVAILABLE', TIMESTAMP '2004-10-19 10:43:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (2, 3, 3, 'Peril', 'Political & Social Sciences', 'USED', 'Bob Woodward', 198218291, 19.61, 5, 1, 'E_BOOK', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (3, 3, 3, 'Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones', 'Business & Money', 'USED', 'James Clear', 807296726, 10.88, 5, 1, 'PRE_ORDER', 9, 'UNAVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (4, 1, 3, 'Chasing Fireflies: A Novel of Discovery', 'Christian Books', 'USED', 'Charles Martin', 800612389, 9.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (5, 2, 3, 'Start with Why: How Great Leaders Inspire Everyone to Take Action', 'Computers & Technology', 'USED', 'Simon Sinek', 807424616, 15.87, 5, 1, 'SUPPLY', 99, 'UNAVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (6, 2, 3, 'Trisha`s Kitchen: Easy Comfort Food for Friends and Family', 'Cookbooks, Food & Wine', 'USED', 'Trisha Yearwood', 035856737, 18.74, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (7, 2, 3, 'The Book of Unusual Knowledge', 'Engineering & Transportation','NEW', 'Publications International Ltd.', 145084580, 7.00, 5, 1, 'SUPPLY', 0, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (8, 2, 3, 'Vanderbilt: The Rise and Fall of an American Dynasty', 'History', 'NEW', 'Anderson Cooper', 006296461, 14.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (9, 2, 3, 'Notorious RBG: The Life and Times of Ruth Bader Ginsburg', 'Humor & Entertainment', 'NEW', 'Irin Carmon', 800190554, 10.20, 5, 1, 'SUPPLY', 99, 'PENDING_APPROVAL', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (10,2, 3, 'Rich Dad Poor Dad: What the Rich Teach Their Kids About Money That the Poor and Middle Class Do Not!', 'Parenting & Relationships', 'USED', 'Robert T. Kiyosaki', 1612680178, 6.17, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');

INSERT INTO transactions VALUES (1, 1, 1, 5.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED', 2);
INSERT INTO transactions VALUES (2, 3, 2, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'IN_TRANSIT');
INSERT INTO transactions VALUES (3, 3, 3, 9.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'REFUNDED');
INSERT INTO transactions VALUES (4, 1, 5, 59.09, TIMESTAMP '2004-10-19 10:23:54', NULL, 'CANCELLED');
INSERT INTO transactions VALUES (5, 2, 8, 5.90, TIMESTAMP '2004-10-19 10:23:54', NULL, 'PRE_ORDER');
INSERT INTO transactions VALUES (6, 1, 8, 50.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'PROCESSING');
INSERT INTO transactions VALUES (7, 3, 8, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (8, 1, 5, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (9, 3, 10, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (10, 1, 1, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');

INSERT INTO book_images VALUES (1, 1, 'https://images-na.ssl-images-amazon.com/images/I/81kosCB1luL.jpg', 0);
INSERT INTO book_images VALUES (2, 2, 'https://images-na.ssl-images-amazon.com/images/I/71Ptolb7qnL.jpg', 0);
INSERT INTO book_images VALUES (3, 3, 'https://m.media-amazon.com/images/I/513Y5o-DYtL.jpg', 0);
INSERT INTO book_images VALUES (4, 4, 'https://m.media-amazon.com/images/I/51Ls5sJlVcL.jpg', 0);
INSERT INTO book_images VALUES (5, 5, 'https://m.media-amazon.com/images/I/51Zuy0g7l+L.jpg', 0);
INSERT INTO book_images VALUES (6, 6, 'https://images-na.ssl-images-amazon.com/images/I/61Q+ggWFU3S.jpg', 0);
INSERT INTO book_images VALUES (7, 7, 'https://images-na.ssl-images-amazon.com/images/I/71WC3z4Wt0L.jpg', 0);
INSERT INTO book_images VALUES (8, 8, 'https://images-na.ssl-images-amazon.com/images/I/81Ok1VQoXWS.jpg', 0);
INSERT INTO book_images VALUES (9, 9, 'https://m.media-amazon.com/images/I/51ko3byryDL.jpg', 0);
INSERT INTO book_images VALUES (10, 10, 'https://images-na.ssl-images-amazon.com/images/I/81bsw6fnUiL.jpg', 0);

INSERT INTO book_images VALUES (11, 6, 'https://images-na.ssl-images-amazon.com/images/I/51eqbFcsRPL.jpg', 1);
INSERT INTO book_images VALUES (12, 7, 'https://images-na.ssl-images-amazon.com/images/I/71SpBr17hML.jpg', 1);
INSERT INTO book_images VALUES (13, 7, 'https://images-na.ssl-images-amazon.com/images/I/71KPPNNPDvL.jpg', 2);
INSERT INTO book_images VALUES (14, 7, 'https://images-na.ssl-images-amazon.com/images/I/91i2w+GMTrL.jpg', 3);
INSERT INTO book_images VALUES (15, 7, 'https://images-na.ssl-images-amazon.com/images/I/911GzqUkrWL.jpg', 4);
INSERT INTO book_images VALUES (16, 7, 'https://images-na.ssl-images-amazon.com/images/I/91eMAvBY-aL.jpg', 5);
INSERT INTO book_images VALUES (17, 10, 'https://images-na.ssl-images-amazon.com/images/I/91EuSD+B5kL.jpg', 1);
INSERT INTO book_images VALUES (18, 10, 'https://images-na.ssl-images-amazon.com/images/I/81XB9GoppmL.jpg', 2);
INSERT INTO book_images VALUES (19, 10, 'https://images-na.ssl-images-amazon.com/images/I/81Uo1Xn3VXL.jpg', 3);