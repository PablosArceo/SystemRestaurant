
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pabloarceo.bean.TipoPlato;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;

public class TipoPlatoController implements Initializable{
     private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO; 
    private ObservableList<TipoPlato>listaTipoPlato;
    private MainApp escenarioPrincipal;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblTipoPlato;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private TableColumn colDescripcion;
    @FXML private TextField  txtCodigoTipoPlato;
    @FXML private TextField txtDescripcion;
    
    
    // Metodo para activar controles
    public void activarControles(){
    txtCodigoTipoPlato.setEditable(false);
       txtDescripcion.setEditable(true);
    
    }
  
    // Metodo para desactivar controles
    public void desactivarControles(){
    txtCodigoTipoPlato.setEditable(false);
    txtDescripcion.setEditable(false);
 
    }
    
    // Metodo para limpiar los controles
    public void limpiarControles(){
    txtCodigoTipoPlato.setText("");
    txtDescripcion.setText("");

     
    }
    
    
    
    
    
     //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblTipoPlato.setItems(getTipoPlato());
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, Integer>("codigoTipoPlato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("descripcion"));
       
    }
 
    
    
    //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<TipoPlato> getTipoPlato(){
        
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoPlato()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),resultado.getString("descripcion")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaTipoPlato = FXCollections.observableArrayList(lista);
        
    }
    
     // Metodo para seleccionar elementos de la tabla y mostrarlos en los campos
    public void SeleccionarElementos(){
    txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
    txtDescripcion.setText((((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getDescripcion()));

    }
    
    
    // Metodo para buscar Tipo Plato
        public TipoPlato buscarTipoPlato(int codigoTipoPlato){
        TipoPlato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoPlato(?)}");
            procedimiento.setInt(1, codigoTipoPlato);
            ResultSet registro = procedimiento. executeQuery();
            while (registro.next()){
                resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),
                                             registro.getString("descripcion"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    // Metodo Para agregar 
          // Metodo para Nuevo
    public void nuevo(){
       switch(tipoOperacion){
           case NINGUNO:
          activarControles();
          limpiarControles();
          btnNuevo.setText("Guardar");
          btnEliminar.setText("Cancelar");
          btnEditar.setDisable(true);
          btnReporte.setDisable(true);
          tipoOperacion = Operacion.GUARDAR;
          break;
           case  GUARDAR:  
               guardar();
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnEliminar.setText("Eliminar");
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               tipoOperacion = Operacion.NINGUNO;
               // Cargar los nuevos datos de la tabla 
               cargarDatos();
               break;
       }     
       
    }
    // Metodo para boton Guardar 
    public void guardar(){
    TipoPlato tipoPlatoNuevo = new TipoPlato();
    tipoPlatoNuevo.setDescripcion(txtDescripcion.getText());
    
    try{
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
      sp.setString(1,tipoPlatoNuevo.getDescripcion());
      sp.execute();
      listaTipoPlato.add(tipoPlatoNuevo);
      
    }catch(Exception e){
     e.printStackTrace();
    }    
    }
    
    // Metodo Para editar 

  public void editarTipoPlato(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblTipoPlato.getSelectionModel().getSelectedItem() != null){
                
            btnEditar.setText("Actualizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            activarControles();
            tipoOperacion = Operacion.ACTUALIZAR;
            
            }else{
            JOptionPane.showMessageDialog(null,"Debe selecionar un registro para actualizar");
            
            }
            break;
        case ACTUALIZAR:
            actualizar();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            tipoOperacion = Operacion.NUEVO;
            cargarDatos();
            break;
            
    }
      
      
  }
  // Metodo para actualizar los datos del modelo y del tableview ejecutando Tipo Empleado
  public void actualizar(){
  try{
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Tipo Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoPlato(?,?)}");
      TipoPlato tipoPlatoActualizado = ((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java
      tipoPlatoActualizado.setDescripcion(txtDescripcion.getText());
  
      // Enviando los datos actualizados a ejecutar en el objeto sp
      sp.setString(1,tipoPlatoActualizado.getDescripcion());
      sp.setInt(2, tipoPlatoActualizado.getCodigoTipoPlato());
      sp.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
     // Metodo para eliminar Tipo Empleado
  public void  eliminarTipoPlato(){
  switch(tipoOperacion){
      case GUARDAR:
          desactivarControles();
          limpiarControles();
          btnNuevo.setText("Nuevo");
          btnEliminar.setText("Eliminar");
          btnEditar.setDisable(false);
          btnReporte.setDisable(false);
          tipoOperacion = Operacion.NUEVO;
          break;
      default:
          // Verificar que tenga selecionado un registro de la tabla
          if(tblTipoPlato.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Tipo Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoPlato(?)}");
             sp.setInt(1,((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
             sp.execute(); 
             listaTipoPlato.remove(tblTipoPlato.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Tipo Empleado eliminada en efecto");
             
          }catch(Exception e){
              e.printStackTrace();
              
          }
         
          }
          }else{
          JOptionPane.showMessageDialog(null,"Debe Selecionar el registro a eliminar");
          
          }
  }
  }
    
    public void GenerarReporte(){
         switch(tipoOperacion){
             case NINGUNO:
                 break;
            case ACTUALIZAR:
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoOperacion = Operacion.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;     
         }
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
        
    }
    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }

   
    
}
