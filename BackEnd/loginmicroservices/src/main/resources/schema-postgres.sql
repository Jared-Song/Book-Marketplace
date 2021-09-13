DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS business_users CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS book_images CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS book_reviews CASCADE;
DROP TABLE IF EXISTS user_reviews CASCADE;
DROP TABLE IF EXISTS user_incentive CASCADE;
DROP TABLE IF EXISTS incentives CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TYPE IF EXISTS status;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS service_type;
DROP TYPE IF EXISTS transaction_status;
DROP TYPE IF EXISTS request_type;

CREATE TYPE status AS ENUM (
    'Enabled',
    'Disabled',
    'Suspended',
    'PendingRegistration',
    'DisabledReviewsRequests'
);

CREATE TYPE role AS ENUM (
    'Regular',
    'Business',
    'Admin'
);

CREATE TYPE service_type AS ENUM (
    'Supply',
    'PrintOnDemand',
    'EBook',
    'PreOrder'
);

CREATE TYPE transaction_status AS ENUM (
    'Delivered',
    'InTransit',
    'Refunded',
    'Cancelled',
    'PreOrdered'
);

CREATE TYPE request_type AS ENUM (
    'ToBusinessUser',
    'ToRegUser',
    'SellBook',
    'CreateNewBusinessUser'
);

CREATE TABLE users (
    user_id     serial NOT NULL, --
    username    varchar(45) NOT NULL UNIQUE, --
    password    varchar(255) NOT NULL,
    email       varchar(45) NOT NULL, --
    full_name   varchar(45) NOT NULL, --
    rating      int NOT NULL DEFAULT 0,
    rating_no   int NOT NULL DEFAULT 0,
    address     varchar(255), --
	create_at 	timestamp,
	update_at	timestamp,
    status_id   status NOT NULL DEFAULT 'Enabled', --
    role_id     role NOT NULL DEFAULT 'Regular', --
    PRIMARY KEY (user_id),
    CONSTRAINT username_UNIQUE UNIQUE (username),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE business_users (
    user_id     int NOT NULL,
    ABN         int NOT NULL,
    name        varchar(255) NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT ABN_UNIQUE UNIQUE (ABN),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE books (
    book_id serial NOT NULL,
    user_id int NOT NULL,
    book_title varchar(90) NOT NULL,
    genre varchar(45) NOT NULL,
    author_full_name varchar(90) NOT NULL,
    ISBN int NOT NULL,
    release_date timestamp NOT NULL,
    posted_date timestamp NOT NULL,
    price decimal NOT NULL,
    rating      int NOT NULL DEFAULT 0,
    rating_no   int NOT NULL DEFAULT 0,
    service_id  service_type NOT NULL,
    quantity int NOT NULL DEFAULT 0,
    PRIMARY KEY (book_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE transactions (
    transaction_id serial NOT NULL,
    buyer_id int NOT NULL,
    book_id int NOT NULL,
    price decimal NOT NULL,
    date_processed timestamp NOT NULL,
	updated_at	timestamp,
    transactions_status_id transaction_status NOT NULL,
    PRIMARY KEY (transaction_id),
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES users (user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE book_images (
    book_images_id serial NOT NULL,
    book_id int NOT NULL,
    url varchar (255) NOT NULL,
    image_number int NOT NULL,
    PRIMARY KEY (book_images_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE requests (
    request_id serial NOT NULL,
    user_id    int NOT NULL,
    request    varchar(255) NOT NULL,
    request_type request_type NOT NULL,
    PRIMARY KEY (request_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE book_reviews (
    review_id serial NOT NULL,
    reviewer_id int NOT NULL,
    book_id   int NOT NULL,
    rating    int NOT NULL,
    review    varchar(200),
    PRIMARY KEY (review_id),
    CONSTRAINT fk_reviewer FOREIGN KEY (reviewer_id) REFERENCES users (user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE user_reviews (
    review_id serial NOT NULL,
    reviewer_id int NOT NULL,
    user_id   int NOT NULL,
    rating    int NOT NULL,
    review    varchar(200),
    PRIMARY KEY (review_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_reviewer FOREIGN KEY (reviewer_id) REFERENCES users (user_id)
);

CREATE TABLE incentives (
    incentive_id serial NOT NULL,
    seller_id int NOT NULL,
    spending_amount_req int NOT NULL,
    discount_amount int NOT NULL,
	PRIMARY KEY (incentive_id),
    CONSTRAINT fk_user FOREIGN KEY (seller_id) REFERENCES users (user_id)
);

CREATE TABLE user_incentive (
	id serial NOT NULL,
    customer_id  int NOT NULL,
    incentive_id int NOT NULL,
	CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES users (user_id),
	CONSTRAINT fk_incentive FOREIGN KEY (incentive_id) REFERENCES incentives (incentive_id)
);