-- Create table "provider"
CREATE TABLE provider (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(40) NOT NULL,
    address VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contact_number VARCHAR(20) NOT NULL
    );

-- Create table "customer"
CREATE TABLE customer (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(40) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contact_number VARCHAR(20) NOT NULL
    );

-- Create table "employee"
CREATE TABLE employee (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    day_of_birth DATE,
    contact_number VARCHAR(20) NOT NULL,
    provider_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider (id)
    );

-- Create table "offer"
CREATE TABLE offer (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(150) NOT NULL,
    cost BIGINT NOT NULL,
    status VARCHAR(25) NOT NULL,
    provider_id BIGINT,
    customer_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
    );

-- Create table "offer_delivery"
CREATE TABLE offer_delivery (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    delivery_date DATE NOT NULL,
    accepted BOOLEAN NOT NULL,
    offer_id BIGINT,
    FOREIGN KEY (offer_id) REFERENCES offer (id)
    );

-- Create table "employee_offer"
CREATE TABLE employee_offer (
    employee_id BIGINT,
    offer_id BIGINT,
    PRIMARY KEY (employee_id, offer_id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    FOREIGN KEY (offer_id) REFERENCES offer (id)
);
