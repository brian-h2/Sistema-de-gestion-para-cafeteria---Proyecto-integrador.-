-- 1) Base y opciones
CREATE DATABASE IF NOT EXISTS cafeteria
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;
USE cafeteria;

-- 2) Categorías (para clasificar productos)
CREATE TABLE IF NOT EXISTS categorias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE
);

-- 3) Productos (BEBIDA / ALIMENTO)
CREATE TABLE IF NOT EXISTS productos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  precio_base DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  categoria_id INT NOT NULL,
  tipo ENUM('BEBIDA','ALIMENTO') NOT NULL,
  -- Campos específicos para bebida (pueden ser NULL si es ALIMENTO)
  es_caliente TINYINT(1),
  tamano VARCHAR(10),          -- S/M/L
  tipo_leche VARCHAR(20),      -- comun/almendra/avena etc.
  -- Campos específicos para alimento
  es_sin_tacc TINYINT(1),
  es_vegano TINYINT(1),
  CONSTRAINT fk_prod_cat FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- 4) Semillas mínimas (para probar DAO/lectura)
INSERT INTO categorias(nombre) VALUES 
  ('bebidas calientes'), ('bebidas frías'), ('snacks'), ('postres')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO productos(nombre,precio_base,stock,categoria_id,tipo,es_caliente,tamano,tipo_leche,es_sin_tacc,es_vegano) VALUES
  ('Latte',1500,50,1,'BEBIDA',1,'M','comun',NULL,NULL),
  ('Capuccino',1600,40,1,'BEBIDA',1,'M','comun',NULL,NULL),
  ('Brownie',1200,30,4,'ALIMENTO',NULL,NULL,NULL,0,0);
