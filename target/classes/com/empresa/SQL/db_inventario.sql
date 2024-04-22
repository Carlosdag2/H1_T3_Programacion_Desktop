-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS inventario_db;

-- Seleccionar la base de datos
USE inventario_db;

-- Crear la tabla de inventario
CREATE TABLE IF NOT EXISTS inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL
);

INSERT INTO inventario (nombre, cantidad, precio) VALUES 
('Camiseta básica', 100, 10.99),
('Pantalones vaqueros', 80, 29.99),
('Zapatillas deportivas', 50, 49.99),
('Camisa de vestir', 60, 39.99),
('Chaqueta de cuero', 30, 99.99),
('Vestido de noche', 40, 79.99),
('Reloj de pulsera', 20, 149.99),
('Auriculares inalámbricos', 25, 89.99),
('Portátil de 15 pulgadas', 15, 799.99),
('Smartphone de gama media', 35, 299.99),
('Bolso de mano', 70, 49.99),
('Gafas de sol', 90, 19.99),
('Botella de vino tinto', 120, 14.99),
('Auriculares con cancelación de ruido', 15, 199.99),
('Tablet Android', 20, 199.99),
('Maletín de viaje', 40, 79.99),
('Botas de senderismo', 45, 69.99),
('Mochila resistente al agua', 55, 39.99),
('Teclado mecánico para juegos', 30, 79.99),
('Cámara digital compacta', 25, 249.99);

select * from inventario;