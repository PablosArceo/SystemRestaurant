package org.pabloarceo.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.pabloarceo.system.MainApp;

public class DatosPersonalesController implements Initializable{
    

    private MainApp escenarioPrincipal;

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
      
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    
   
    
  
 
}
