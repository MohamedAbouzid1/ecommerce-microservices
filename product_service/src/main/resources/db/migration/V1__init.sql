CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL ,
    description TEXT,
    price DECIMAL(10,9),
    stock INT,
    created_at TIMESTAMP DEFAULT NOW()
)