package org.pabloarceo.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.pabloarceo.system.MainApp;
import org.pabloarceo.bean.Empresa;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.report.GenerarReporte;

public class EmpresasController implements Initializable {
    private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO; 
    private ObservableList<Empresa>listaEmpresa;
    
    private MainApp escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    
    
    // Metodo para activar controles
    public void activarControles(){
    txtCodigoEmpresa.setEditable(false);
    txtNombreEmpresa.setEditable(true);
    txtDireccion.setEditable(true);
    txtTelefono.setEditable(true);
        
    }
    
    
    // Metodo para desactivar controles
    public void desactivarControles(){
    txtCodigoEmpresa.setEditable(false);
    txtNombreEmpresa.setEditable(false);
    txtDireccion.setEditable(false);
    txtTelefono.setEditable(false);
    }
    
    // Metodo para limpiar los controles
    public void limpiarControles(){
    txtCodigoEmpresa.setText("");
    txtNombreEmpresa.setText("");
    txtDireccion.setText("");
    txtTelefono.setText("");
     
    }
    
    // Metodo para Nuevo
    public void nuevo(){
       switch(tipoOperacion){
           case NINGUNO:
          activarControles();
          btnNuevo.setText("Guardar");
          btnEliminar.setDisable(false);
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
               btnEliminar.setDisable(false);
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
    Empresa empresaNueva = new Empresa();
    empresaNueva.setNombreEmpresa(txtNombreEmpresa.getText());
    empresaNueva.setDireccion(txtDireccion.getText());
    empresaNueva.setTelefono(txtTelefono.getText());
    try{
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpresas(?,?,?)}");
      sp.setString(1,empresaNueva.getNombreEmpresa());
      sp.setString(2,empresaNueva.getDireccion());
      sp.setString(3,empresaNueva.getTelefono());
      sp.execute();
      listaEmpresa.add(empresaNueva);
      
    }catch(Exception e){
     e.printStackTrace();
    }
    
    
        
    }
  

    //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblEmpresas.setItems(getEmpresa());
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
        desactivarControles();
    }
    
    
    //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<Empresa> getEmpresa(){
        
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),resultado.getString("nombreEmpresa"),resultado.getString("direccion"),resultado.getString("telefono")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaEmpresa = FXCollections.observableArrayList(lista);
        
    }
    
    // Metodo para seleccionar elementos de la tabla y mostrarlos en los campos
    public void SeleccionarElementos(){
    txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    txtNombreEmpresa.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa()));
    txtDireccion.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion()));
    txtTelefono.setText((((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono()));
    
    }
    // Metodo para eliminar empresas
 public void  eliminarEmpresa(){
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
          if(tblEmpresas.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Empresa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpresa(?)}");
             sp.setInt(1,((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
             sp.execute(); 
             listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Empresa eliminada en efecto");
             
          }catch(Exception e){
              e.printStackTrace();
              
          }
         
          }
          }else{
          JOptionPane.showMessageDialog(null,"Debe Selecionar el registro a eliminar");
          
          }
  }
  }
    
  
  
  
  
  
  // Editar-Actualizar Empresas 
  public void editarEmpresa(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblEmpresas.getSelectionModel().getSelectedItem() != null){
                
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
  
  // Metodo para actualizar los datos del modelo y del tableview ejecutando 
  public void actualizar(){
  try{
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Empresa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpresas(?,?,?,?)}");
      Empresa empresaActualizada = ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java
      empresaActualizada.setNombreEmpresa(txtNombreEmpresa.getText());
      empresaActualizada.setDireccion(txtDireccion.getText());
      empresaActualizada.setTelefono(txtTelefono.getText());
      // Enviando los datos actualizados a ejecutar en el objeto sp
      sp.setString(1,empresaActualizada.getNombreEmpresa());
      sp.setString(2, empresaActualizada.getDireccion());
      sp.setString(3, empresaActualizada.getTelefono());
      sp.setInt(4, empresaActualizada.getCodigoEmpresa());
      sp.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
  
public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Empresa(
                                    registro.getInt("codigoEmpresa"),
                                    registro.getString("nombreEmpresa"),
                                    registro.getString("direccion"),
                                    registro.getString("telefono"));
        }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
  
public void GenerarReporte(){
         switch(tipoOperacion){
             case NINGUNO:
                 imprimirReporte();
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




  public void imprimirReporte(){
  Map parametros = new HashMap();
  parametros.put("codigoEmpresa",null);
  GenerarReporte.mostrarReporte("ReporteEmpresas.jasper","ReporteDeEmpresas", parametros);
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
    
    @FXML
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
     public void ventanaServicicios(){
       escenarioPrincipal.ventanaServicicios();
       }
}
