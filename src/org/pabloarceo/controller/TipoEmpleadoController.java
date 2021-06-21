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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pabloarceo.bean.TipoEmpleado;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;


public class TipoEmpleadoController implements Initializable {
    private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO; 
    private ObservableList<TipoEmpleado>listaTipoEmpleado;
    
    private MainApp escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblTipoEmpleado;
    @FXML private TableColumn colCodigoTipoEmpleado;
     @FXML private TableColumn colDescripcion;
    @FXML private TextField  txtCodigoTipoEmpleado;
    @FXML private TextField txtDescripcion;
    
    // Metodo para activar controles
    public void activarControles(){
    txtCodigoTipoEmpleado.setEditable(false);
       txtDescripcion.setEditable(true);
    
    }
  
    // Metodo para desactivar controles
    public void desactivarControles(){
    txtCodigoTipoEmpleado.setEditable(false);
    txtDescripcion.setEditable(false);
 
    }
    
    // Metodo para limpiar los controles
    public void limpiarControles(){
    txtCodigoTipoEmpleado.setText("");
    txtDescripcion.setText("");

     
    }
    
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
    TipoEmpleado tipoEmpleadoNuevo = new TipoEmpleado();
    tipoEmpleadoNuevo.setDescripcion(txtDescripcion.getText());
    
    try{
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
      sp.setString(1,tipoEmpleadoNuevo.getDescripcion());
      sp.execute();
      listaTipoEmpleado.add(tipoEmpleadoNuevo);
      
    }catch(Exception e){
     e.printStackTrace();
    }
    
    
        
    }
    
    
    
    //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblTipoEmpleado.setItems(getTipoEmpleado());
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, Integer>("codigoTipoEmpleado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, String>("descripcion"));
       
    }
 
    
    
    //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<TipoEmpleado> getTipoEmpleado(){
        
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),resultado.getString("descripcion")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaTipoEmpleado = FXCollections.observableArrayList(lista);
        
    }
    
     // Metodo para seleccionar elementos de la tabla y mostrarlos en los campos
    public void SeleccionarElementos(){
    txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
    txtDescripcion.setText((((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getDescripcion()));

    }
    
     public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        TipoEmpleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento. executeQuery();
            while (registro.next()){
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                                             registro.getString("descripcion"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
     // Editar-Actualizar Tipo Empleado
  public void editarTipoEmpleado(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblTipoEmpleado.getSelectionModel().getSelectedItem() != null){
                
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
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Tipo Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoEmpleado(?,?)}");
      TipoEmpleado tipoEmpleadoActualizado = ((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java
      tipoEmpleadoActualizado.setDescripcion(txtDescripcion.getText());
  
      // Enviando los datos actualizados a ejecutar en el objeto sp
      sp.setString(1,tipoEmpleadoActualizado.getDescripcion());
      sp.setInt(2, tipoEmpleadoActualizado.getCodigoTipoEmpleado());
      sp.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
  

      // Metodo para eliminar Tipo Empleado
  public void  eliminarTipoEmpleado(){
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
          if(tblTipoEmpleado.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Tipo Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
             sp.setInt(1,((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
             sp.execute(); 
             listaTipoEmpleado.remove(tblTipoEmpleado.getSelectionModel().getSelectedIndex());
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
