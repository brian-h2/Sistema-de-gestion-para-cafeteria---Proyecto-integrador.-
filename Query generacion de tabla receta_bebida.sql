use cafeteria;

CREATE TABLE IF NOT EXISTS receta_bebida (
  id_bebida INT NOT NULL,
  id_ingrediente INT NOT NULL,
  cantidad DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_bebida, id_ingrediente),
  FOREIGN KEY (id_bebida) REFERENCES productos(id),
  FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id)
);

INSERT INTO receta_bebida (id_bebida, id_ingrediente, cantidad)
VALUES
  (1, 1, 200),  -- Latte usa 200 ml de leche
  (1, 2, 50); 
  
  INSERT INTO ingredientes (nombre, stock, unidad) VALUES
('Leche', 5000, 'ml'),
('Café molido', 2000, 'g'),
('Azúcar', 1000, 'g');