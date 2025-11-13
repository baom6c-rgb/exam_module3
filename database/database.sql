CREATE DATABASE room_rental_management;
USE room_rental_management;

CREATE TABLE payment_method (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_name VARCHAR(50) NOT NULL UNIQUE
);


INSERT INTO payment_method (payment_name) VALUES
('theo tháng'),
('theo quý'),
('theo năm');

CREATE TABLE rental (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    start_date DATE NOT NULL,
    payment_id INT NOT NULL,
    note VARCHAR(200),
    FOREIGN KEY (payment_id) REFERENCES payment_method(payment_id)
);

ALTER DATABASE room_rental_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

ALTER TABLE payment_method CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE rental CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO rental (customer_name, phone, start_date, payment_id, note) VALUES
('Nguyễn Văn A', '0123456789', '2020-10-10', 1, 'Phòng có điều hòa'),
('Nguyễn Văn B', '0123456789', '2020-10-10', 2, '...'),
('Nguyễn Văn C', '0123456789', '2020-10-10', 3, 'Phòng có điều hòa'),
('Nguyễn Văn D', '0123456789', '2020-10-10', 1, 'Phòng có điều hòa');





