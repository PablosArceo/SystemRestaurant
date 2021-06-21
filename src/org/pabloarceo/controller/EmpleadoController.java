
package org.pabloarceo.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pabloarceo.bean.Empleado;
import org.pabloarceo.bean.Presupuesto;
import org.pabloarceo.bean.TipoEmpleado;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;



public class EmpleadoController  implements Initializable{
       private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO;
    private ObservableList <Empleado>listaEmpleado;
    private ObservableList<TipoEmpleado>listaTipoEmpleado;
    
    private MainApp escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblempleado;
    
    
   
    
    @FXML private TextField txtcodigoEmpleado;
    @FXML private TextField txtnumeroEmpleado;
    @FXML private TextField txtapellidosEmpleado;
    @FXML private TextField txtnombresEmpleado;
    @FXML private TextField txtdireccionEmpleado;
    @FXML private TextField txttelefonoContacto;
    @FXML private TextField txtgradoCocinero;
      @FXML private ComboBox  cmbCodigoTipoEmpleado;
      
      
      
     @FXML private TableColumn colcodigoEmpleado;
    @FXML private TableColumn colnumeroEmpleado;
    @FXML private TableColumn colapellidosEmpleado;       
    @FXML private TableColumn colnombresEmpleado; 
    @FXML private TableColumn coldireccionEmpleado;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colTipoEmpleado_codigoTipoEmpleado;
  
    
    
    public void cargarDatos(){
        
        tblempleado.setItems(getEmpleado());
        colcodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colnumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,Integer >("numeroEmpleado"));
        colapellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colnombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        coldireccionEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccionEmpleado"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero"));
        colTipoEmpleado_codigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("TipoEmpleado_codigoTipoEmpleado"));
        cmbCodigoTipoEmpleado.setItems(getTipoEmpleado());
        
    }
    
     //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<Empleado> getEmpleado(){
        
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("TipoEmpleado_codigoTipoEmpleado")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
        
    }
   
    
    
    
 
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
   
  // Metodo para Seleccionar elementos en el Table View de Empleado
     public void seleccionarElemento(){
     txtcodigoEmpleado.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
      txtnumeroEmpleado.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
      txtapellidosEmpleado.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
      txtnombresEmpleado.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
      txtdireccionEmpleado.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
      txttelefonoContacto.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
      txtgradoCocinero.setText(String.valueOf(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getGradoCocinero()));      
    cmbCodigoTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getTipoEmpleado_codigoTipoEmpleado()));
     //       cmbCodigoEmpresas.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getEmpresas_codigoEmpresa()));

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
          
          
           public Empleado buscarEmpleado(int codigoEmpleado){
        Empleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Empleado(
                                    registro.getInt("codigoEmpleado"),
                                    registro.getInt("numeroEmpleado"),
                                    registro.getString("apellidosEmpleado"),
                                    registro.getString("nombresEmpleado"),
                                    registro.getString("direccionEmpleado"),
                                    registro.getString("telefonoContacto"),
                                    registro.getString("gradoCocinero"),
                                    registro.getInt("TipoEmpleado_codigoTipoEmpleado")                        
                                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
          
          
          
          
          
          
     
       public void desactivarControles(){
    txtcodigoEmpleado.setEditable(false);
    txtnumeroEmpleado.setEditable(false);
    txtapellidosEmpleado.setEditable(false); 
    txtnombresEmpleado.setEditable(false); 
    txtdireccionEmpleado.setEditable(false); 
    txttelefonoContacto.setEditable(false);
    txtgradoCocinero.setEditable(false);
    cmbCodigoTipoEmpleado.setEditable(false);
    
    }
    
    public void activarControles(){
    txtcodigoEmpleado.setEditable(false);
    txtnumeroEmpleado.setEditable(true);
    txtapellidosEmpleado.setEditable(true);
    txtnombresEmpleado.setEditable(true);
    txtdireccionEmpleado.setEditable(true);
    txttelefonoContacto.setEditable(true);
    txtgradoCocinero.setEditable(true);
    cmbCodigoTipoEmpleado.setEditable(false);

   
    }
    
    public void limpiarControles(){
    txtcodigoEmpleado.setText("");
    txtnumeroEmpleado.setText("");
    txtapellidosEmpleado.setText("");
    txtnombresEmpleado.setText("");
    txtdireccionEmpleado.setText("");
    txttelefonoContacto.setText("");
    txtgradoCocinero.setText("");
    cmbCodigoTipoEmpleado.getSelectionModel().clearSelection();
    
    
    }
     
      // -- Metodo para Nuevo registro (Tupla) De Empleado --
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
    // Metodo para guardar Registro Ingresaso
       public void guardar() {
       Empleado registro = new Empleado();
       registro.setNumeroEmpleado(Integer.parseInt(txtnumeroEmpleado.getText()));
       registro.setApellidosEmpleado((txtapellidosEmpleado.getText()));
       registro.setNombresEmpleado((txtnombresEmpleado.getText()));
       registro.setTelefonoContacto((txttelefonoContacto.getText()));
       registro.setDireccionEmpleado((txtdireccionEmpleado.getText()));
       registro.setGradoCocinero((txtgradoCocinero.getText()));
       registro.setTipoEmpleado_codigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
       
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?,?,?,?,?,?,?)}");
              procedimiento.setInt(1, registro.getNumeroEmpleado());
           procedimiento.setString(2, registro.getApellidosEmpleado());
           procedimiento.setString(3,registro.getNombresEmpleado());
           procedimiento.setString(4,registro.getDireccionEmpleado());
           procedimiento.setString(5,registro.getTelefonoContacto());
           procedimiento.setString(6,registro.getGradoCocinero());
           procedimiento.setInt(7, registro.getTipoEmpleado_codigoTipoEmpleado());
           procedimiento.execute();
           
           listaEmpleado.add(registro);
                   
       }catch(Exception e){
       e.printStackTrace();
       } 
       } 
     
       
       
       
       
       
              // Editar-Actualizar Empleado
  public void editarEmpleado(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblempleado.getSelectionModel().getSelectedItem() != null){
                
            btnEditar.setText("Actualizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            activarControles();
            tipoOperacion = EmpleadoController.Operacion.ACTUALIZAR;
            
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
            tipoOperacion = EmpleadoController.Operacion.NUEVO;
            cargarDatos();
            break;
            
    }
      
      
  }
       
        // Metodo para actualizar los datos del modelo y del tableview ejecutando 
  public void actualizar(){
  try{
      
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Empleaado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?,?,?)}");
      Empleado empleadoActualizado = ((Empleado)tblempleado.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java 
       empleadoActualizado.setNumeroEmpleado(Integer.parseInt(txtnumeroEmpleado.getText()));
       empleadoActualizado.setApellidosEmpleado((txtapellidosEmpleado.getText()));
       empleadoActualizado.setNombresEmpleado((txtnombresEmpleado.getText()));
       empleadoActualizado.setTelefonoContacto((txttelefonoContacto.getText()));
       empleadoActualizado.setDireccionEmpleado((txtdireccionEmpleado.getText()));
       empleadoActualizado.setGradoCocinero((txtgradoCocinero.getText()));
      // Enviando los datos actualizados a ejecutar en el objeto sp
            procedimiento.setInt(1, empleadoActualizado.getNumeroEmpleado());
           procedimiento.setString(2, empleadoActualizado.getApellidosEmpleado());
           procedimiento.setString(3,empleadoActualizado.getNombresEmpleado());
           procedimiento.setString(4,empleadoActualizado.getDireccionEmpleado());
           procedimiento.setString(5,empleadoActualizado.getTelefonoContacto());
           procedimiento.setString(6,empleadoActualizado.getGradoCocinero());
           procedimiento.setInt(7, empleadoActualizado.getTipoEmpleado_codigoTipoEmpleado());   
           procedimiento.setInt(8, empleadoActualizado.getCodigoEmpleado());
  
      procedimiento.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
       
      // Metodo para eliminar presupuestos
  public void  eliminarEmpleado(){
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
          if(tblempleado.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
             sp.setInt(1,((Empleado)tblempleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
             sp.execute(); 
             listaEmpleado.remove(tblempleado.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Empleado eliminada en efecto");
             
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
