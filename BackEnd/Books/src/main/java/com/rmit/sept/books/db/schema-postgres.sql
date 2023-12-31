DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS business_users CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS book_images CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS book_reviews CASCADE;
DROP TABLE IF EXISTS user_reviews CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS user_incentive CASCADE;
DROP TABLE IF EXISTS incentives CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TYPE IF EXISTS user_status;
DROP TYPE IF EXISTS transaction_status;
DROP TYPE IF EXISTS book_status;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS service_type;
DROP TYPE IF EXISTS request_type;
DROP TYPE IF EXISTS quality;

CREATE TYPE user_status AS ENUM (
    'ENABLED',
    'DISABLED',
    'SUSPENDED',
    'PENDING_REGISTRATION',
    'DISABLED_REVIEWS_AND_REQUESTS',
    'TERMINATED'
);

CREATE TYPE transaction_status AS ENUM (
    'DELIVERED',
    'IN_TRANSIT',
    'REFUNDED',
    'CANCELLED',
    'PRE_ORDER',
    'PROCESSING',
    'REFUND_PENDING'
);

CREATE TYPE book_status AS ENUM (
    'AVAILABLE',
    'UNAVAILABLE',
    'PENDING_APPROVAL'
);

CREATE TYPE role AS ENUM (
    'USER_NORMAL',
    'USER_BUSINESS',
    'ADMIN'
);

CREATE TYPE service_type AS ENUM (
    'SUPPLY',
    'PRINT_ON_DEMAND',
    'E_BOOK',
    'PRE_ORDER'
);

CREATE TYPE request_type AS ENUM (
    'NEW_BUSINESS_USER',
    'TO_REG_USER',
    'NEW_BOOK_LISTING',
    'TO_BUSINESS_USER',
    'REFUND_TRANSACTION'
);

CREATE TYPE quality AS ENUM (
    'NEW',
    'USED'
);

CREATE TABLE users (
    user_id      serial NOT NULL, --
    username     varchar(45) NOT NULL UNIQUE, --
    password     varchar(255) NOT NULL,
    email        varchar(45) NOT NULL, --
    full_name    varchar(45) NOT NULL, --
    rating_total int NOT NULL DEFAULT 0,
    rating_no    int NOT NULL DEFAULT 0,
    address      varchar(255), --
	create_at 	 timestamp,
	update_at	 timestamp,
    user_status    user_status NOT NULL DEFAULT 'ENABLED', --
    role         role NOT NULL DEFAULT 'USER_NORMAL', --
    PRIMARY KEY (user_id),
    CONSTRAINT username_UNIQUE UNIQUE (username),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE business_users (
    id          serial NOT NULL,
    user_id     int NOT NULL,
    ABN         int NOT NULL,
    name        varchar(255) NOT NULL,
    created_at 	timestamp,
	updated_at	timestamp,
    PRIMARY KEY (user_id),
    CONSTRAINT ABN_UNIQUE UNIQUE (ABN),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE requests (
    request_id      serial NOT NULL,
    user_id         int NOT NULL,
    object_id       int,
    request         varchar(255) NOT NULL,
    request_type    request_type NOT NULL,
    create_at 	    timestamp,
	update_at	    timestamp,
    PRIMARY KEY (request_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE books (
    book_id      serial NOT NULL,
    seller_id    int NOT NULL,
    request_id   int NOT NULL,
    book_title   varchar(120) NOT NULL,
    category     varchar(45) NOT NULL,
    quality      quality NOT NULL DEFAULT 'USED', 
    author_name  varchar(90) NOT NULL,
    ISBN         int NOT NULL,
    price        decimal NOT NULL,
    rating_total int NOT NULL DEFAULT 0,
    rating_no    int NOT NULL DEFAULT 0,
    service_id   service_type NOT NULL,
    quantity     int NOT NULL DEFAULT 0,
    status_id    book_status NOT NULL DEFAULT 'UNAVAILABLE',
    create_at 	 timestamp,
	update_at	 timestamp,
    PRIMARY KEY (book_id),
    CONSTRAINT fk_user FOREIGN KEY (seller_id) REFERENCES users (user_id),
    CONSTRAINT fk_request FOREIGN KEY (request_id) REFERENCES requests (request_id)
);

CREATE TABLE transactions (
    transaction_id          serial NOT NULL,
    buyer_id                int NOT NULL,
    book_id                 int NOT NULL,
    price                   decimal NOT NULL,
    date_processed          timestamp NOT NULL,
	updated_at	            timestamp,
    transactions_status_id  transaction_status NOT NULL DEFAULT 'PROCESSING',
    quantity                int NOT NULL DEFAULT 1,
    is_reviewed             boolean NOT NULL DEFAULT FALSE,
    PRIMARY KEY (transaction_id),
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES users (user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE book_images (
    book_images_id  serial NOT NULL,
    book_id         int NOT NULL,
    url             varchar (255) NOT NULL,
    image_number    int NOT NULL,
    PRIMARY KEY (book_images_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE incentives (
    incentive_id        serial NOT NULL,
    seller_id           int NOT NULL,
    spending_amount_req int NOT NULL,
    discount_amount     int NOT NULL,
	PRIMARY KEY (incentive_id),
    CONSTRAINT fk_user FOREIGN KEY (seller_id) REFERENCES users (user_id)
);

CREATE TABLE user_incentive (
	id              serial NOT NULL,
    customer_id     int NOT NULL,
    incentive_id    int NOT NULL,
	CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES users (user_id),
	CONSTRAINT fk_incentive FOREIGN KEY (incentive_id) REFERENCES incentives (incentive_id)
);