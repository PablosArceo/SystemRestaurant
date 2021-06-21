
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
import org.pabloarceo.system.MainApp;
import org.pabloarceo.bean.Servicios;
import org.pabloarceo.bean.Empresa;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.report.GenerarReporte;
public class ServiciosController implements Initializable{
    private MainApp escenarioPrincipal;
    private enum operaciones {NUEVO ,GUARDAR,CANCELAR,EDITAR,REPORTE, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion =  operaciones.NINGUNO;
    private ObservableList <Servicios> listaServicio;
    private ObservableList <Empresa> listaEmpresa;
    private DatePicker fechaServicio;
    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtHoraServicio;
    @FXML private TextField txtLugarServicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtTipoServicio;
    @FXML private GridPane grpFechaServicio;
    
    @FXML private ComboBox cmbCodigoEmpresa;
    
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colLugarServicio;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colCodigoEmpresa;
    
  @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fechaServicio = new DatePicker(Locale.ENGLISH);
        fechaServicio.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaServicio.getCalendarView().todayButtonTextProperty().set("Today");
        fechaServicio.getCalendarView().todayButtonTextProperty().set("Today");
        grpFechaServicio.add(fechaServicio,0,0);    
        fechaServicio.getStylesheets().add("/org/pabloarceo/resource/DatePicker.css");
        cmbCodigoEmpresa.setItems(getEmpresa());
    }
    
     //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblServicios.setItems(getServicios());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Date>("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,String>("tipoServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios,String>("horaServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicios,String>("lugarServicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("telefonoContacto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("Empresas_CodigoEmpresa"));     
        cmbCodigoEmpresa.setItems(getEmpresa());
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
    
     public void seleccionarElementos(){
        txtCodigoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        fechaServicio.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
        txtTipoServicio.setText((((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio()));
        txtHoraServicio.setText((((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio()));
        txtLugarServicio.setText((((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio()));
        txtTelefonoContacto.setText((((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto()));                                                                          
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getEmpresas_CodigoEmpresa()));
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
    
    
    
    
    
      public void desactivarControles(){
        txtCodigoServicio.setEditable(false);
        grpFechaServicio.setDisable(true);
        txtTipoServicio.setEditable(false);
        txtHoraServicio.setEditable(false);
        txtLugarServicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);                                                                          
        cmbCodigoEmpresa.setDisable(false);
                   
    }
    public void activarControles(){
        txtCodigoServicio.setEditable(false);
        grpFechaServicio.setDisable(false);
        txtTipoServicio.setEditable(true);
        txtHoraServicio.setEditable(true);
        txtLugarServicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);                                                                          
        cmbCodigoEmpresa.setDisable(false);
                   
    }
    public void limpiarControles(){
        txtCodigoServicio.setText("");
        txtTipoServicio.setText("");
        txtHoraServicio.setText("");
        txtLugarServicio.setText("");
        txtTelefonoContacto.setText("");                                                                          
        cmbCodigoEmpresa.getSelectionModel().clearSelection();
    }
    
    // Metodo Para agregar Servicios (Tuplas)
    
     public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar(); 
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void guardar(){
        Servicios registro= new Servicios();
        registro.setFechaServicio(fechaServicio.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        registro.setHoraServicio(txtHoraServicio.getText());
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setEmpresas_CodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios(?,?,?,?,?,?)}");
             procedimiento.setDate(1,new java.sql.Date(registro.getFechaServicio().getTime()));
             procedimiento.setString(2, registro.getTipoServicio());
             procedimiento.setString(3, registro.getHoraServicio());
             procedimiento.setString(4, registro.getLugarServicio());
             procedimiento.setString(5, registro.getTelefonoContacto());
             procedimiento.setInt(6, registro.getEmpresas_CodigoEmpresa());
             procedimiento.execute();
             listaServicio.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
  // Metodo para eliminar presupuestos
  public void  eliminarServicios(){
  switch(tipoDeOperacion){
      case GUARDAR:
          desactivarControles();
          limpiarControles();
          btnNuevo.setText("Nuevo");
          btnEliminar.setText("Eliminar");
          btnEditar.setDisable(false);
          btnReporte.setDisable(false);
          tipoDeOperacion = operaciones.NUEVO;
          break;
      default:
          // Verificar que tenga selecionado un registro de la tabla
          if(tblServicios.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios(?)}");
             sp.setInt(1,((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
             sp.execute(); 
             listaEmpresa.remove(tblServicios.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Servicio eliminado en efecto");
             
          }catch(Exception e){
              e.printStackTrace();
              
          }
         
          }
          }else{
          JOptionPane.showMessageDialog(null,"Debe Selecionar el registro a eliminar");
          
          }
  }
  }
    
   public void editarServicios(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServicios.getSelectionModel().getSelectedItem() != null){
                    activarControles();
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento!");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios(?,?,?,?,?,?,?)}");
            Servicios registro = (Servicios)tblServicios.getSelectionModel().getSelectedItem();
            registro.setFechaServicio(fechaServicio.getSelectedDate());
            registro.setTipoServicio(txtTipoServicio.getText());
            registro.setHoraServicio(txtHoraServicio.getText());
            registro.setLugarServicio(txtLugarServicio.getText());
            registro.setTelefonoContacto(txtTelefonoContacto.getText());
            registro.setEmpresas_CodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            procedimiento.setDate(1,new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(2, registro.getTipoServicio());
            procedimiento.setString(3, registro.getHoraServicio());
            procedimiento.setString(4, registro.getLugarServicio());
            procedimiento.setString(5, registro.getTelefonoContacto());
             procedimiento.setInt(6, registro.getEmpresas_CodigoEmpresa());
             procedimiento.setInt(7, registro.getCodigoServicio());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
  
    public void GenerarReporte(){
         switch(tipoDeOperacion){
             case NINGUNO:
                 if (tblServicios.getSelectionModel().getSelectedItem() != null){
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
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                break;     
         }
     } 
   public  void imprimirReporte(){
  Map parametros = new HashMap();
  //parametros.put("codEmpresa",null);
  int codigoServicio = Integer.valueOf(txtCodigoServicio.getText());
  parametros.put("codServicio",codigoServicio);
  GenerarReporte.mostrarReporte("ReporteServicios_.jasper","Reporte de Servicios", parametros);
  }
  
  
  
    
    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
      
    }
      public void ventanaEmpresas () {
      escenarioPrincipal.ventanaEmpresas();
    }

    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    
    
}
