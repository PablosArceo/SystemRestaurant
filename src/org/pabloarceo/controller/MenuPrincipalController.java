
package org.pabloarceo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.pabloarceo.system.MainApp;

public class MenuPrincipalController implements Initializable{
    
    private MainApp escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        
      escenarioPrincipal.ventanaProgramador();
      
    }
    
    public void ventanaEmpresas(){
    
        escenarioPrincipal.ventanaEmpresas();
        
    }
    public void ventanaProductos(){
       escenarioPrincipal.ventanaProductos();
    }
    
    public void ventanaTipoEmpleado(){
       escenarioPrincipal.ventanaTipoEmpleado();
    }
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
      public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
       public void ventanaServicicios(){
       escenarioPrincipal.ventanaServicicios();
       }
       public void ventanaPlatos(){
       escenarioPrincipal.ventanaPlatos();
}
       public void ventanaTipoPlato(){
       escenarioPrincipal.ventanaTipoPlato();
           }
       public void ventanaServicios_has_platos(){
           escenarioPrincipal.ventanaServicios_has_platos();
       }
       public void ventanaProductos_has_platos(){
           escenarioPrincipal.ventanaProductos_has_platos();
       }
         public void ventanaServicios_has_Empleados(){
             escenarioPrincipal.ventanaServicios_has_Empleados();
         }
}