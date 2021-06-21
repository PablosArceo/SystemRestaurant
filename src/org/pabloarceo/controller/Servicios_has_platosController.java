
package org.pabloarceo.controller;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pabloarceo.bean.Plato;
import org.pabloarceo.bean.Servicios;
import org.pabloarceo.bean.Servicios_has_Platos;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;


public class Servicios_has_platosController implements Initializable{

    private enum operaciones {NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Servicios_has_Platos> listaServicios_has_Platos;
    private ObservableList <Servicios> listaServicio;
    private ObservableList<Plato>listaPlato;
    private MainApp escenarioPrincipal;
    
    
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private TableView tblServiciosHasPlatos;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoServicio;
    
    
    
      public void seleccionarElemento(){
        cmbCodigoServicio.getSelectionModel().select(buscarServicios(((Servicios_has_Platos)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getServicios_CodigoServicio())); 
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((Servicios_has_Platos)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getPlatos_CodigoPlato())); 
    }
    
      
         public void cargarDatos(){
        tblServiciosHasPlatos.setItems(getServicios_has_Platos());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_has_Platos, Integer>("Servicios_CodigoServicio"));       
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Servicios_has_Platos, Integer>("Platos_CodigoPlato"));
    }
      
      
      
        public ObservableList<Servicios_has_Platos> getServicios_has_Platos(){
        ArrayList<Servicios_has_Platos> lista = new ArrayList<Servicios_has_Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServicios_has_Platos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios_has_Platos( resultado.getInt("Servicios_codigoServicio"),
                                        resultado.getInt("Servicios_CodigoServicio"),
                                        resultado.getInt("Platos_CodigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
    }
        return listaServicios_has_Platos = FXCollections.observableArrayList(lista);
    }
    
      
      
    
    
    
    public ObservableList<Plato> getPlato(){
        
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarPlatos()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
              lista.add(new Plato(resultado.getInt("codigoPlato"),
                resultado.getInt("cantidad"),
                         resultado.getString("nombrePlato"),
                        resultado.getString("descripcionPlato"),
                        resultado.getDouble("precioPlato"),
                        resultado.getInt("TipoPlato_codigoTipoPlato")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaPlato = FXCollections.observableArrayList(lista);
        
    }
    
public ObservableList <Servicios> getServicios(){
        ArrayList<Servicios> lista=new ArrayList <Servicios>();
        try{
           PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios}");
           ResultSet resultado=procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Servicios( resultado.getInt("codigoServicio"),
                                        resultado.getDate("fechaServicio"),
                                        resultado.getString("tipoServicio"),
                                        resultado.getString("horaServicio"),
                                        resultado.getString("lugarServicio"),
                                        resultado.getString("telefonoContacto"),                                                                              
                                        resultado.getInt("Empresas_CodigoEmpresa")
               
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio=FXCollections.observableArrayList(lista);                               
    } 

     public Servicios buscarServicios(int codigoServicio){
        Servicios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicios(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                 resultado = new Servicios(registro.getInt("codigoServicio"),
                                          registro.getDate("fechaServicio"),
                                          registro.getString("tipoServicio"),
                                          registro.getString("horaServicio"),
                                          registro.getString("lugarServicio"),
                                          registro.getString("telefonoContacto"),
                                          registro.getInt("Empresas_CodigoEmpresa"));
        }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    } 
    
    
public Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlatos(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Plato(registro.getInt("codigoPlato"),
                                            registro.getInt("cantidad"),
                                            registro.getString("nombrePlato"),
                                            registro.getString("descripcionPlato"),
                                            registro.getDouble ("precioPlato"),
                                            registro.getInt("TipoPlato_codigoTipoPlato")
                                            );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
       

   
    

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
      
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         cargarDatos();
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoServicio.setItems(getServicios());
    }
    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    
     public void ventanaPlatos(){
        escenarioPrincipal.ventanaPlatos();
    }
    
    public void ventanaServicicios(){
       escenarioPrincipal.ventanaServicicios();
       }
    
    
}
    
    
    

