
package org.pabloarceo.bean;

public class Servicios_has_Platos {
    private int codigoServicios_has_Platos;
    private int Servicios_CodigoServicio;
    private int Platos_CodigoPlato;

    public int getServicios_CodigoServicio() {
        return Servicios_CodigoServicio;
    }

    public void setServicios_CodigoServicio(int Servicios_CodigoServicio) {
        this.Servicios_CodigoServicio = Servicios_CodigoServicio;
    }

    public int getPlatos_CodigoPlato() {
        return Platos_CodigoPlato;
    }

    public void setPlatos_CodigoPlato(int Platos_CodigoPlato) {
        this.Platos_CodigoPlato = Platos_CodigoPlato;
    }

    public Servicios_has_Platos() {
    }

    public Servicios_has_Platos(int codigoServicios_has_Platos, int Servicios_CodigoServicio, int Platos_CodigoPlato) {
        this.codigoServicios_has_Platos = codigoServicios_has_Platos;
        this.Servicios_CodigoServicio = Servicios_CodigoServicio;
        this.Platos_CodigoPlato = Platos_CodigoPlato;
    }

    @Override
    public String toString() {
        return "Servicios_has_Platos{" + "codigoServicios_has_Platos=" + codigoServicios_has_Platos + ", Servicios_CodigoServicio=" + Servicios_CodigoServicio + ", Platos_CodigoPlato=" + Platos_CodigoPlato + '}';
    }
    
    
    
}
