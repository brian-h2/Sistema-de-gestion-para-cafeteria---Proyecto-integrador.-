use cafeteria;


CREATE TABLE ingredientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  stock DECIMAL(10,2) NOT NULL,
  unidad VARCHAR(10) NOT NULL -- g, ml, unidad
);

CREATE TABLE receta_bebida (
  id_bebida INT NOT NULL,
  id_ingrediente INT NOT NULL,
  cantidad DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_bebida, id_ingrediente),
  FOREIGN KEY (id_bebida) REFERENCES productos(id),
  FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id)
);