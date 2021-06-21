package org.pabloarceo.system;

import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import org.pabloarceo.controller.DatosPersonalesController;
import org.pabloarceo.controller.EmpresasController;
import org.pabloarceo.controller.MenuPrincipalController;
import org.pabloarceo.controller.ProductosController;
import org.pabloarceo.controller.TipoEmpleadoController;
import org.pabloarceo.controller.EmpleadoController;
import org.pabloarceo.controller.PlatosController;
import org.pabloarceo.controller.PresupuestoController;
import org.pabloarceo.controller.Productos_has_platosController;
import org.pabloarceo.controller.ServiciosController;
import org.pabloarceo.controller.Servicios_has_empleadosController;
import org.pabloarceo.controller.Servicios_has_platosController;
import org.pabloarceo.controller.TipoPlatoController;

public class MainApp extends Application {
    
    private final String PAQUETE_VISTA= "/org/pabloarceo/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    public static void main(String[] args) {
       launch(args); 
    }
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        
        this.escenarioPrincipal=escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal");
        escenarioPrincipal.getIcons().add(new Image("/org/pabloarceo/img/app.png"));
        menuPrincipal();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            
            MenuPrincipalController menuPrincipal=(MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",733,473);
            menuPrincipal.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
    }
    
    public void ventanaProgramador(){
        
        try{
            
            DatosPersonalesController datosPersonales=(DatosPersonalesController) cambiarEscena("DatosPersonalesView.fxml",600,400);
            datosPersonales.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    public void ventanaEmpresas(){
        
        try{
            
            EmpresasController empresas=(EmpresasController) cambiarEscena("EmpresasView.fxml",903,484);
            empresas.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
        public void ventanaProductos(){
        
        try{
            
            ProductosController productoController=(ProductosController) cambiarEscena("ProductosView.fxml",766,449);
            productoController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
        
        public void ventanaTipoEmpleado(){
        
        try{
            
            TipoEmpleadoController tipoEmpleadoController=(TipoEmpleadoController) cambiarEscena("TipoEmpleadoView.fxml",802,455);
            tipoEmpleadoController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        }
        
        public void ventanaEmpleado(){
        
        try{
            
            EmpleadoController empleadoController=(EmpleadoController) cambiarEscena("EmpleadoView.fxml",930,530);
            empleadoController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
        public void ventanaPresupuesto(){
        
        try{
            
            PresupuestoController presupuestoController=(PresupuestoController) cambiarEscena("PresupuestoView.fxml",800,510);
            presupuestoController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
        
      public void ventanaServicicios(){
        
        try{
            
            ServiciosController serviciosController=(ServiciosController) cambiarEscena("ServiciosView.fxml",902,546);
            serviciosController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
   
    public void ventanaPlatos(){
        
        try{
            
            PlatosController platosController=(PlatosController) cambiarEscena("PlatosView.fxml",902,546);
            platosController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
      
    
    public void ventanaTipoPlato(){
        
        try{
            
            TipoPlatoController tipoplatoController=(TipoPlatoController) cambiarEscena("TipoPlatoView.fxml",802,455);
            tipoplatoController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    } 
    
     public void ventanaServicios_has_platos(){
        
        try{
            
            Servicios_has_platosController  servicios_has_platosController=(Servicios_has_platosController) cambiarEscena("ServiciosHasPlatosView.fxml",700,478);
            servicios_has_platosController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    } 
     
         public void ventanaProductos_has_platos(){
        
        try{
            
            Productos_has_platosController  productos_has_platosController=(Productos_has_platosController) cambiarEscena("ProductosHasPlatosView.fxml",700,478);
            productos_has_platosController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    } 
              public void ventanaServicios_has_Empleados(){
        
        try{
            
           Servicios_has_empleadosController  servicios_has_empleadosController=(Servicios_has_empleadosController) cambiarEscena("ServiciosHasEmpleadosView.fxml",865,578);
            servicios_has_empleadosController.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    } 
         
         
     
     
      
    
    public Initializable cambiarEscena(String fxml,int ancho,int alto) throws Exception{
        Initializable resultado=null;
        FXMLLoader cargadorFXML=new FXMLLoader();
        InputStream archivo=MainApp.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(MainApp.class.getResource(PAQUETE_VISTA+fxml));
        escena=new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado=(Initializable) cargadorFXML.getController();
        
        return resultado;
    }

}
