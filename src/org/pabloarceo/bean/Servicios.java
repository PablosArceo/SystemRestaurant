
package org.pabloarceo.bean;

import java.util.Date;

public class Servicios {
    private int codigoServicio;
    private Date fechaServicio;
    private String tipoServicio;
    private String horaServicio;
    private String lugarServicio;
    private String telefonoContacto;
    private int Empresas_CodigoEmpresa;

    public Servicios() {
    }

    public Servicios(int codigoServicio, Date fechaServicio, String tipoServicio, String horaServicio, String lugarServicio, String telefonoContacto, int Empresas_CodigoEmpresa) {
        this.codigoServicio = codigoServicio;
        this.fechaServicio = fechaServicio;
        this.tipoServicio = tipoServicio;
        this.horaServicio = horaServicio;
        this.lugarServicio = lugarServicio;
        this.telefonoContacto = telefonoContacto;
        this.Empresas_CodigoEmpresa = Empresas_CodigoEmpresa;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getEmpresas_CodigoEmpresa() {
        return Empresas_CodigoEmpresa;
    }

    public void setEmpresas_CodigoEmpresa(int Empresas_CodigoEmpresa) {
        this.Empresas_CodigoEmpresa = Empresas_CodigoEmpresa;
    }

 
     @Override
    public String toString() {
        return getCodigoServicio() + "  I " + getTipoServicio();
    }
    
  
}
