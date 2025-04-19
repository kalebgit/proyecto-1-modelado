-- Script para crear las tablas en SQLite

-- Tabla de componentes
CREATE TABLE IF NOT EXISTS componentes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    precio REAL NOT NULL,
    marca TEXT NOT NULL,
    tipo_componente TEXT NOT NULL,
    atributos TEXT -- Almacenará atributos específicos en formato JSON
);

-- Tabla de software
CREATE TABLE IF NOT EXISTS software (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    version TEXT NOT NULL,
    precio REAL NOT NULL
);

-- Tabla de PCs
CREATE TABLE IF NOT EXISTS pcs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    tipo TEXT NOT NULL,
    precio_total REAL NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de relación PC-Componente
CREATE TABLE IF NOT EXISTS pc_componentes (
    pc_id INTEGER,
    componente_id INTEGER,
    PRIMARY KEY (pc_id, componente_id),
    FOREIGN KEY (pc_id) REFERENCES pcs(id),
    FOREIGN KEY (componente_id) REFERENCES componentes(id)
);

-- Tabla de relación PC-Software
CREATE TABLE IF NOT EXISTS pc_software (
    pc_id INTEGER,
    software_id INTEGER,
    PRIMARY KEY (pc_id, software_id),
    FOREIGN KEY (pc_id) REFERENCES pcs(id),
    FOREIGN KEY (software_id) REFERENCES software(id)
);

-- Tabla de pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente TEXT NOT NULL,
    pc_id INTEGER,
    estado TEXT NOT NULL,
    sucursal TEXT NOT NULL,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pc_id) REFERENCES pcs(id)
);

-- Insertar datos de ejemplo para componentes

-- CPUs Intel
INSERT INTO componentes (nombre, precio, marca, tipo_componente, atributos)
VALUES 
('Core i3-13100', 1999.99, 'Intel', 'CPU', '{"cantidadNucleos":4,"socket":"LGA1700"}'),
('Core i5-13600K', 4999.99, 'Intel', 'CPU', '{"cantidadNucleos":6,"socket":"LGA1700"}'),
('Core i7-13700K', 7999.99, 'Intel', 'CPU', '{"cantidadNucleos":8,"socket":"LGA1700"}'),
('Core i9-13900K', 11999.99, 'Intel', 'CPU', '{"cantidadNucleos":12,"socket":"LGA1700"}');

-- CPUs AMD
INSERT INTO componentes (nombre, precio, marca, tipo_componente, atributos)
VALUES 
('Ryzen 5 5600G', 2499.99, 'AMD', 'CPU', '{"cantidadNucleos":6,"socket":"AM4"}'),
('Ryzen 5 7600X', 5499.99, 'AMD', 'CPU', '{"cantidadNucleos":6,"socket":"AM5"}'),
('Ryzen 7 7700X', 7999.99, 'AMD', 'CPU', '{"cantidadNucleos":8,"socket":"AM5"}'),
('Ryzen 9 7950X3D', 12999.99, 'AMD', 'CPU', '{"cantidadNucleos":16,"socket":"AM5"}');

-- Software
INSERT INTO software (nombre, version, precio)
VALUES 
('Windows', '11', 1500.0),
('Microsoft Office', '365', 1200.0),
('Adobe Photoshop', '2024', 1800.0),
('AutoCAD', '2024', 2500.0),
('Terminal con WSL', '2.0', 0.0);
