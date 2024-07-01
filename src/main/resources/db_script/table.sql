CREATE TABLE book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn_number VARCHAR(20) NOT NULL,
    return_date DATE,
    borrow_date DATE,
    borrower_id INT,
    PRIMARY KEY (id)
);

CREATE TABLE borrower (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);