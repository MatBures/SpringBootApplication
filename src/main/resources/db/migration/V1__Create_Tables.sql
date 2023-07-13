-- Create table "provider"
CREATE TABLE IF NOT EXISTS provider (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL
    );

-- Create table "customer"
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL
    );

-- Create table "employee"
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    day_of_birth DATE,
    contact_number VARCHAR(255) NOT NULL,
    provider_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider (id)
    );

-- Create table "offer"
CREATE TABLE IF NOT EXISTS offer (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    cost BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    provider_id BIGINT,
    customer_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
    );

-- Create table "offer_delivery"
CREATE TABLE IF NOT EXISTS offer_delivery (
    id BIGINT PRIMARY KEY,
    delivery_date DATE NOT NULL,
    accepted BOOLEAN NOT NULL,
    offer_id BIGINT,
    FOREIGN KEY (offer_id) REFERENCES offer (id)
    );
