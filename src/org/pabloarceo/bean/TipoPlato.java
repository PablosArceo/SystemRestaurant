
package org.pabloarceo.bean;

public class TipoPlato {
    private int codigoTipoPlato;
    private String descripcion;

    public int getCodigoTipoPlato() {
        return codigoTipoPlato;
    }

    public void setCodigoTipoPlato(int codigoTipoPlato) {
        this.codigoTipoPlato = codigoTipoPlato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoPlato() {
    }

    public TipoPlato(int codigoTipoPlato, String descripcion) {
        this.codigoTipoPlato = codigoTipoPlato;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
       return getCodigoTipoPlato() + "  I " + getDescripcion();
    }
    
    
    
}
