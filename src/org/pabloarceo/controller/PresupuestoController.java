package org.pabloarceo.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.pabloarceo.bean.Empresa;
import org.pabloarceo.bean.Presupuesto;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.report.GenerarReporte;
import org.pabloarceo.system.MainApp;

public class PresupuestoController implements Initializable{
    private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO;
    private ObservableList <Presupuesto>listaPresupuesto;
    private ObservableList<Empresa>listaEmpresa;
    private DatePicker fechaSolicitud;
            
    private MainApp escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblPresupuesto;
    
    @FXML private TableColumn colCodigoPresupuesto;
    @FXML private TableColumn colFechaSolicitud;
    @FXML private TableColumn colCantidadPresupuesto;
    @FXML private TableColumn colCodigoEmpresa;
    
    @FXML private TextField txtCodigoPresupuesto;
    @FXML private TextField txtCantidadPresupuesto;
    @FXML private TextField txtCodigoEmpresa;
    
    @FXML private ComboBox  cmbCodigoEmpresas;
    @FXML private GridPane gpFechaSolicitud;
    
     //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblPresupuesto.setItems(getPresupuesto());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoPresupuesto"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto,Date >("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("cantidadPresupuesto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("Empresas_codigoEmpresa"));
        cmbCodigoEmpresas.setItems(getEmpresa());
        
    }
    
    //Metodo para ejecutar el procedimmiento almacenado y llenar el observablelist
    public ObservableList<Presupuesto> getPresupuesto(){
        
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarPresupuesto()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Presupuesto(resultado.getInt("codigoPresupuesto"),
                        resultado.getDate("fechaSolicitud"),
                        resultado.getDouble("cantidadPresupuesto"),
                        resultado.getInt("Empresas_codigoEmpresa")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaPresupuesto = FXCollections.observableArrayList(lista);
        
    }
   
     public ObservableList<Empresa> getEmpresa(){
        
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                
                lista.add(new Empresa(
                        resultado.getInt("codigoEmpresa"),
                        resultado.getString("nombreEmpresa"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
                
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        return listaEmpresa = FXCollections.observableArrayList(lista);
        
    }
    
     // Metodo para Seleccionar elementos en el Table View
     public void seleccionarElemento(){
     txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
    fechaSolicitud.selectedDateProperty().set(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getFechaSolicitud());
    txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
      cmbCodigoEmpresas.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getEmpresas_codigoEmpresa()));
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
     
     
     // Metodo para Nuevo registro (Tupla)
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
    // Metodo para guardar Registro Ingresado
       public void guardar() {
       Presupuesto registro = new Presupuesto();
       registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
       registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
       registro.setEmpresas_codigoEmpresa(((Empresa)cmbCodigoEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPresupuesto(?,?,?)}");
           procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
           procedimiento.setDouble(2, registro.getCantidadPresupuesto());
           procedimiento.setInt(3, registro.getEmpresas_codigoEmpresa());
           procedimiento.execute();
           
           listaPresupuesto.add(registro);
                   
       }catch(Exception e){
       e.printStackTrace();
       } 
       } 
       
       
       // Editar-Actualizar Presupuesto
  public void editarPresupuesto(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblPresupuesto.getSelectionModel().getSelectedItem() != null){
                
            btnEditar.setText("Actualizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            activarControles();
            tipoOperacion = PresupuestoController.Operacion.ACTUALIZAR;
            
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
            tipoOperacion = PresupuestoController.Operacion.NUEVO;
            cargarDatos();
            break;
            
    }
      
      
  }
       
          
       // Metodo para actualizar Registro- Presupuesto
     
       
        // Metodo para actualizar los datos del modelo y del tableview ejecutando 
  public void actualizar(){
  try{
      
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPresupuestos(?,?,?,?)}");
      Presupuesto presupuestoActualizado = ((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java
      presupuestoActualizado.setFechaSolicitud(fechaSolicitud.getSelectedDate());
      presupuestoActualizado.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
      // Enviando los datos actualizados a ejecutar en el objeto sp
      procedimiento.setDate(1, new java.sql.Date(presupuestoActualizado.getFechaSolicitud().getTime()));
      procedimiento.setDouble(2, presupuestoActualizado.getCantidadPresupuesto());
      procedimiento.setInt(3,presupuestoActualizado.getEmpresas_codigoEmpresa());
      procedimiento.setInt(4,presupuestoActualizado.getCodigoPresupuesto());    
  
      procedimiento.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
  
   // Metodo para eliminar presupuestos
  public void  eliminarPresupuesto(){
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
          if(tblPresupuesto.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
             sp.setInt(1,((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
             sp.execute(); 
             listaEmpresa.remove(tblPresupuesto.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Presupuesto eliminada en efecto");
             
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
                 if (tblPresupuesto.getSelectionModel().getSelectedItem() != null){
                 imprimirReporte();
                 }else{
                 JOptionPane.showMessageDialog(null,"Debe seleccionar un registro para imprimir reporte");
                  }
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
  
  public  void imprimirReporte(){
  Map parametros = new HashMap();
  //parametros.put("codEmpresa",null);
  int Empresas_codigoEmpresa = Integer.valueOf(((Empresa)cmbCodigoEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
  parametros.put("Empresas_codigoEmpresa",Empresas_codigoEmpresa);
  GenerarReporte.mostrarReporte("ReportePresupuesto.jasper","Reporte de Presupuesto", parametros);
  }
  
  
  
         public void desactivarControles(){
    txtCodigoPresupuesto.setEditable(false);
    txtCantidadPresupuesto.setEditable(false);
    gpFechaSolicitud.setDisable(true);
    cmbCodigoEmpresas.setEditable(false);
    
    }
    
    public void activarControles(){
    txtCodigoPresupuesto.setEditable(false);
    txtCantidadPresupuesto.setEditable(true);
    gpFechaSolicitud.setDisable(false);
    cmbCodigoEmpresas.setEditable(false);
    
   
    }
    
    public void limpiarControles(){
    txtCodigoPresupuesto.setText("");
    txtCantidadPresupuesto.setText("");
    cmbCodigoEmpresas.getSelectionModel().clearSelection();
    
    
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
    fechaSolicitud = new DatePicker(Locale.ENGLISH);
        fechaSolicitud.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
         fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
         fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
         gpFechaSolicitud.add(fechaSolicitud,0,0);    
         fechaSolicitud.getStylesheets().add("/org/pabloarceo/resource/DatePicker.css");
          cmbCodigoEmpresas.setItems(getEmpresa());
    }
    
  
    
   
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    
}
