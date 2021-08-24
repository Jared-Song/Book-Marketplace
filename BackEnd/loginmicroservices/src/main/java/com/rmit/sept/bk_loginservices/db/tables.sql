DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS business_users;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS service_types;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_status;
DROP TABLE IF EXISTS book_images;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS request_types;
DROP TABLE IF EXISTS book_reviews;
DROP TABLE IF EXISTS user_reviews;
DROP TABLE IF EXISTS incentives;
DROP TABLE IF EXISTS incentive_ids;

CREATE TABLE users (
    user_id     int NOT NULL,
    username    varchar(45) NOT NULL,
    password    varchar(255) NOT NULL,
    email       varchar(45) NOT NULL,
    first_name  varchar(45) NOT NULL,
    middle_name varchar(45),
    last_name   varchar(45) NOT NULL,
    rating      int NOT NULL DEFAULT 0,
    rating_no   int NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id),
    CONSTRAINT username_UNIQUE UNIQUE (user_id)
);
INSERT INTO users VALUES (0,'a','password','caramelwilson@gmail.com','a','b','c');

CREATE TABLE profiles (
    user_id int NOT NULL,
    status_id int NOT NULL,
    role_id int NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE business_users (
    user_id     int NOT NULL,
    ABN         int NOT NULL,
    name        varchar(255) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE statuses (
    status_id int NOT NULL,
    description varchar(45) NOT NULL,
    PRIMARY KEY (status_id),
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES profiles (status_id)
);
INSERT INTO statuses VALUES (0, "Enabled");
INSERT INTO statuses VALUES (1, "Disabled");
INSERT INTO statuses VALUES (2, "Suspended");
INSERT INTO statuses VALUES (3, "Pending Registration");
INSERT INTO statuses VALUES (4, "Disabled reviews + requests");


CREATE TABLE roles (
    role_id int NOT NULL,
    description varchar (45) NOT NULL,
    PRIMARY KEY (role_id)
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES profiles (role_id)
)
INSERT INTO roles VALUES (0, "Admin");
INSERT INTO roles VALUES (1, "Business");
INSERT INTO roles VALUES (2, "Customer");

CREATE TABLE books (
    book_id int NOT NULL,
    user_id int NOT NULL,
    book_title varchar(90) NOT NULL,
    genre varchar(45) NOT NULL,
    author_first_name varchar(45) NOT NULL,
    author_last_name varchar(45) NOT NULL,
    ISBN int NOT NULL,
    release_date timestamp NOT NULL,
    posted_date timestamp NOT NULL,
    price double NOT NULL,
    rating      int NOT NULL DEFAULT 0,
    rating_no   int NOT NULL DEFAULT 0,
    service_id  int NOT NULL DEFAULT 0,
    quantity int NOT NULL DEFAULT 0,
    PRIMARY KEY (book_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE service_types {
    service_id int NOT NULL,
    description varchar(45) NOT NULL,
    PRIMARY KEY (service_id),
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES books (service_id)
}
INSERT INTO service_types VALUES (0, "Limited supply");
INSERT INTO service_types VALUES (1, "Print on demand");
INSERT INTO service_types VALUES (2, "E-Book");
INSERT INTO service_types VALUES (3, "Pre-order");

CREATE TABLE transactions (
    transaction_id int NOT NULL,
    buyer_id int NOT NULL,
    seller_id int NOT NULL,
    book_id int NOT NULL,
    cost int NOT NULL,
    date_processed timestamp NOT NULL,
    transactions_status_id int NOT NULL,
    PRIMARY KEY (transaction_id),
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES users (user_id),
    CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES users (user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE transaction_status {
    transaction_status_id int NOT NULL,
    description varchar(45) NOT NULL,
    PRIMARY KEY (transaction_status_id),
    CONSTRAINT fk_transaction_status FOREIGN KEY (transaction_status_id) REFERENCES transactions (transaction_status_id)
}
INSERT INTO transactions_status VALUES (0, "Delivered");
INSERT INTO transactions_status VALUES (1, "In-transit");
INSERT INTO transactions_status VALUES (2, "Refunded");
INSERT INTO transactions_status VALUES (3, "Cancelled");
INSERT INTO transactions_status VALUES (4, "Pre-ordered");

CREATE TABLE book_images (
    book_images_id int NOT NULL,
    book_id int NOT NULL,
    url varchar (90) NOT NULL,
    image_number int NOT NULL,
    PRIMARY KEY (book_images_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE requests (
    request_id int NOT NULL,
    user_id    int NOT NULL,
    request    varchar(255) NOT NULL,
    request_type int NOT NULL,
    PRIMARY KEY (request_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE request_types (
    request_type int NOT NULL,
    description varchar (90),
    PRIMARY KEY (request_type),
    CONSTRAINT fk_request_type FOREIGN KEY (request_type) REFERENCES requests (request_type)
)
INSERT INTO request_types VALUES (0, "Change from normal user to business user");
INSERT INTO request_types VALUES (1, "Change from business user to normal user");

CREATE TABLE book_reviews (
    review_id int NOT NULL,
    reviewer_id int NOT NULL,
    book_id   int NOT NULL,
    rating    int NOT NULL,
    review    varchar(200),
    PRIMARY KEY (review_id),
    CONSTRAINT fk_reviewer FOREIGN KEY (reviewer_id) REFERENCES users (user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE user_reviews (
    review_id int NOT NULL,
    reviewer_id int NOT NULL,
    user_id   int NOT NULL,
    rating    int NOT NULL,
    review    varchar(200),
    PRIMARY KEY (review_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_reviewer FOREIGN KEY (reviewer_id) REFERENCES users (user_id)
);

CREATE TABLE incentives ( --not sure what this is for
    customer_id  int NOT NULL,
    incentive_id int NOT NULL
);

CREATE TABLE incentive_ids (
    incentive_id int NOT NULL,
    seller_id int NOT NULL,
    spending_amount_req int NOT NULL,
    discount_amount int NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (seller_id) REFERENCES users (user_id)
);