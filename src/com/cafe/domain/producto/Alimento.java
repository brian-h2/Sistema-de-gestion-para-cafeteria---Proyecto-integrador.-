package com.cafe.domain.producto;

public class Alimento extends Producto{
    private boolean esVegano;
    private boolean contieneGluten;

    //Utilizamos las propiedades de la clase padre Producto
    public Alimento(int id, String nombre, double precioBase, int stock, String categoria,
                    boolean esVegano, boolean contieneGluten) {
        super(id, nombre, precioBase, stock, categoria);
        this.esVegano = esVegano;
        this.contieneGluten = contieneGluten;
    }

    // //Metodo para calcular el precio final del alimento
    // @Override
    // public double precioFinal() {
    //     double precio = precioBase;
    //     if (esVegano) precio += 50;
    //     if (contieneGluten) precio += 30;
    //     return precio;
    // }

    public boolean isEsVegano() {
        return esVegano;
    }
    public void setEsVegano(boolean esVegano) {
        this.esVegano = esVegano;
    }
    public boolean isContieneGluten() {
        return contieneGluten;
    }
    public void setContieneGluten(boolean contieneGluten) {
        this.contieneGluten = contieneGluten;
    }


    
}
