
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pabloarceo.bean.Plato;
import org.pabloarceo.bean.Producto;
import org.pabloarceo.bean.Productos_has_Platos;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;


public class Productos_has_platosController implements Initializable{
    
    private enum operaciones {NINGUNO};
     private operaciones tipoDeOperacion = operaciones.NINGUNO;
     private ObservableList<Productos_has_Platos> listaProductos_has_Platos;
     private ObservableList <Producto>listaProducto;
     private ObservableList<Plato>listaPlato;

    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoProducto;
    @FXML private TableView tblProductoshasPlatos;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoProducto;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoProducto.setItems(getProducto());
        
    }
    
    public void cargarDatos(){
        tblProductoshasPlatos.setItems(getProductos_has_Platos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos_has_Platos, Integer>("Productos_CodigoProductos"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Productos_has_Platos, Integer>("Platos_CodigoPlato"));
    }
    public ObservableList<Productos_has_Platos> getProductos_has_Platos(){
        ArrayList<Productos_has_Platos> lista = new ArrayList<Productos_has_Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos_has_Platos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos_has_Platos( resultado.getInt("codigo_has_Platos"),
                                        resultado.getInt("Productos_CodigoProductos"),
                                        resultado.getInt("Platos_CodigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
    }
        return listaProductos_has_Platos = FXCollections.observableArrayList(lista);
    }
    
     //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
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
    
     //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<Producto> getProducto(){
        
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Producto(resultado.getInt("codigoProducto"),
                        resultado.getString("nombreProducto"),
                        resultado.getInt("cantidad")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaProducto = FXCollections.observableArrayList(lista);
        
    }
    
    
     public void seleccionarElemento(){
        cmbCodigoProducto.getSelectionModel().select(buscarProducto(((Productos_has_Platos)tblProductoshasPlatos.getSelectionModel().getSelectedItem()).getProductos_CodigoProductos()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((Productos_has_Platos)tblProductoshasPlatos.getSelectionModel().getSelectedItem()).getPlatos_CodigoPlato())); 

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
       
    
       public Producto buscarProducto(int codigoProducto){
        Producto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Producto(registro.getInt("codigoProducto"),
                                            registro.getString("nombreProducto"),
                                            registro.getInt("cantidad")
                                            );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
       
    
    
    

    private MainApp escenarioPrincipal;

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
      
    }

    
    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    } 
    
      public void ventanaProductos(){
       escenarioPrincipal.ventanaProductos();
    }
      
        public void ventanaPlatos(){
       escenarioPrincipal.ventanaPlatos();
}
    
    
}
