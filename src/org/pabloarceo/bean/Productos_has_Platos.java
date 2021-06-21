
package org.pabloarceo.bean;


public class Productos_has_Platos {
    private int codigo_has_Platos;
    private int Productos_CodigoProductos;
    private int Platos_CodigoPlato;

    public int getCodigo_has_Platos() {
        return codigo_has_Platos;
    }

    public void setCodigo_has_Platos(int codigo_has_Platos) {
        this.codigo_has_Platos = codigo_has_Platos;
    }

    public int getProductos_CodigoProductos() {
        return Productos_CodigoProductos;
    }

    public void setProductos_CodigoProductos(int Productos_CodigoProductos) {
        this.Productos_CodigoProductos = Productos_CodigoProductos;
    }

    public int getPlatos_CodigoPlato() {
        return Platos_CodigoPlato;
    }

    public void setPlatos_CodigoPlato(int Platos_CodigoPlato) {
        this.Platos_CodigoPlato = Platos_CodigoPlato;
    }

    public Productos_has_Platos() {
    }

    public Productos_has_Platos(int codigo_has_Platos, int Productos_CodigoProductos, int Platos_CodigoPlato) {
        this.codigo_has_Platos = codigo_has_Platos;
        this.Productos_CodigoProductos = Productos_CodigoProductos;
        this.Platos_CodigoPlato = Platos_CodigoPlato;
    }

    @Override
    public String toString() {
        return "Productos_has_Platos{" + "codigo_has_Platos=" + codigo_has_Platos + ", Productos_CodigoProductos=" + Productos_CodigoProductos + ", Platos_CodigoPlato=" + Platos_CodigoPlato + '}';
    }
    
    
    
}
