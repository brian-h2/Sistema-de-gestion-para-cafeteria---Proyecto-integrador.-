package com.cafe.domain.producto;

public class Bebida extends Producto {
    private String tamaño;    
    private String tipoLeche;  
    private boolean esCaliente;

   //Utilizamos las propiedades de la clase padre Producto
   public Bebida(int id, String nombre, double precioBase, int stock, String categoria,
                  String tamano, String tipoLeche, boolean esCaliente) {
        super(id, nombre, precioBase, stock, categoria);
        this.tamaño = tamano; this.tipoLeche = tipoLeche; this.esCaliente = esCaliente;
    }

    //Metodo para calcular el precio final de la bebida
    @Override
    public double precioFinal() {
        double precio = precioBase;
        if ("M".equalsIgnoreCase(tamaño)) precio += 100;
        if ("L".equalsIgnoreCase(tamaño)) precio += 200;
        if (tipoLeche != null && !tipoLeche.equalsIgnoreCase("comun")) precio += 150;
        return precio;
    }

    public String getTamaño() {
        return tamaño;
    }
    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }
    public String getTipoLeche() {
        return tipoLeche;
    }
    public void setTipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
    }
    public boolean isEsCaliente() {
        return esCaliente;
    }
    public void setEsCaliente(boolean esCaliente) {
        this.esCaliente = esCaliente;
    }

}
