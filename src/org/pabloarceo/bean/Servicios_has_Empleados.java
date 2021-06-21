
package org.pabloarceo.bean;

import java.util.Date;

public class Servicios_has_Empleados {
    private int codigoServicios_has_Empleados ;
    private int Servicios_CodigoServicio;
    private int Empleados_CodigoEmpleado;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;

    public int getCodigoServicios_has_Empleados() {
        return codigoServicios_has_Empleados;
    }

    public void setCodigoServicios_has_Empleados(int codigoServicios_has_Empleados) {
        this.codigoServicios_has_Empleados = codigoServicios_has_Empleados;
    }

    public int getServicios_CodigoServicio() {
        return Servicios_CodigoServicio;
    }

    public void setServicios_CodigoServicio(int Servicios_CodigoServicio) {
        this.Servicios_CodigoServicio = Servicios_CodigoServicio;
    }

    public int getEmpleados_CodigoEmpleado() {
        return Empleados_CodigoEmpleado;
    }

    public void setEmpleados_CodigoEmpleado(int Empleados_CodigoEmpleado) {
        this.Empleados_CodigoEmpleado = Empleados_CodigoEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public Servicios_has_Empleados(int codigoServicios_has_Empleados, int Servicios_CodigoServicio, int Empleados_CodigoEmpleado, Date fechaEvento, String horaEvento, String lugarEvento) {
        this.codigoServicios_has_Empleados = codigoServicios_has_Empleados;
        this.Servicios_CodigoServicio = Servicios_CodigoServicio;
        this.Empleados_CodigoEmpleado = Empleados_CodigoEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public Servicios_has_Empleados() {
    }

    @Override
    public String toString() {
        return "Servicios_has_Empleados{" + "codigoServicios_has_Empleados=" + codigoServicios_has_Empleados + ", Servicios_CodigoServicio=" + Servicios_CodigoServicio + ", Empleados_CodigoEmpleado=" + Empleados_CodigoEmpleado + ", fechaEvento=" + fechaEvento + ", horaEvento=" + horaEvento + ", lugarEvento=" + lugarEvento + '}';
    }
    
    
    
    
}
