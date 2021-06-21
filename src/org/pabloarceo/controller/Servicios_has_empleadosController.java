
package org.pabloarceo.controller;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
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
import org.pabloarceo.bean.Empleado;
import org.pabloarceo.bean.Servicios;
import org.pabloarceo.bean.Servicios_has_Empleados;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;

public class Servicios_has_empleadosController implements Initializable{
        private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
        private MainApp escenarioPrincipal;
        private operaciones tipoDeOperacion = operaciones.NINGUNO;
        private ObservableList<Servicios_has_Empleados> listaServicios_has_Empleados;
        private ObservableList <Servicios> listaServicio;
        private ObservableList <Empleado>listaEmpleado;
        private DatePicker fecha;
        
        @FXML private TextField txtServicios_codigoServicio;
        @FXML private ComboBox cmbCodigoServicio;
        @FXML private ComboBox cmbCodigoEmpleado;
        @FXML private GridPane grpFechaEvento;
        @FXML private TextField txtHoraEvento;
        @FXML private TextField txtLugarEvento;
        
        
        @FXML private TableView tblServicioshasEmpleados;
        @FXML private TableColumn colServicios_codigoServicio;
        @FXML private TableColumn colCodigoServicio;
        @FXML private TableColumn colCodigoEmpleado;
        @FXML private TableColumn colFechaEvento;
        @FXML private TableColumn colHoraEvento;
        @FXML private TableColumn colLugarEvento;

        @FXML private Button btnNuevo;
        @FXML private Button btnEliminar;
        @FXML private Button btnEditar;
        @FXML private Button btnReporte; 
    
    
    
    
    
        @Override
    public void initialize(URL location, ResourceBundle resources) {
         cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFechaEvento.add(fecha,0,0);
        fecha.getStylesheets().add("/org/pabloarceo/resource/DatePicker.css" );
        cmbCodigoServicio.setItems(getServicios());
        cmbCodigoEmpleado.setItems(getEmpleado());       
    }
    
      public void cargarDatos(){
        tblServicioshasEmpleados.setItems(getServicios_has_Empleados());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, Integer>("codigoServicios_has_Empleados"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, Integer>("Servicios_CodigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, Integer>("Empleados_CodigoEmpleado"));  
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<Servicios_has_Empleados, String>("lugarEvento"));

    }
      public ObservableList<Servicios_has_Empleados> getServicios_has_Empleados(){
        ArrayList<Servicios_has_Empleados> lista = new ArrayList<Servicios_has_Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServicios_has_Empleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios_has_Empleados( resultado.getInt("codigoServicios_has_Empleados"),
                                        resultado.getInt("Servicios_CodigoServicio"),
                                        resultado.getInt("Empleados_CodigoEmpleado"),
                                        resultado.getDate("fechaEvento"),
                                        resultado.getString("horaEvento"),
                                        resultado.getString("lugarEvento")));
            }
        }catch(Exception e){
            e.printStackTrace();
    }
        return listaServicios_has_Empleados = FXCollections.observableArrayList(lista);
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
   
    public void seleccionarElemento(){
        txtServicios_codigoServicio.setText(String.valueOf(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicios_has_Empleados()));
        fecha.selectedDateProperty().set(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getFechaEvento());
        txtHoraEvento.setText(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getHoraEvento());
        txtLugarEvento.setText(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getLugarEvento());
        cmbCodigoServicio.getSelectionModel().select(buscarServicios(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getServicios_CodigoServicio())); 
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getEmpleados_CodigoEmpleado())); 

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
    txtServicios_codigoServicio.setEditable(false);
    txtHoraEvento.setEditable(false);
    txtLugarEvento.setEditable(false);
    grpFechaEvento.setDisable(false);
    cmbCodigoServicio.setEditable(false);
    cmbCodigoEmpleado.setDisable(false);
    }
    
    public void activarControles(){
    txtServicios_codigoServicio.setEditable(false);
    txtHoraEvento.setEditable(true);
    txtLugarEvento.setEditable(true);
    grpFechaEvento.setDisable(false);
    cmbCodigoServicio.setEditable(false);
    cmbCodigoEmpleado.setDisable(false);
    }
    
    public void limpiarControles(){
    txtServicios_codigoServicio.setText("");
    txtHoraEvento.setText("");
    txtLugarEvento.setText("");
    cmbCodigoServicio.getSelectionModel().clearSelection();
    cmbCodigoEmpleado.getSelectionModel().clearSelection();
    }      
             
             
             
    // Metodo para agregar
         public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void guardar(){
     
        Servicios_has_Empleados registro = new Servicios_has_Empleados();
        registro.setFechaEvento(fecha.getSelectedDate());
        registro.setHoraEvento(txtHoraEvento.getText());
        registro.setLugarEvento(txtLugarEvento.getText());
        registro.setServicios_CodigoServicio(((Servicios)this.cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setEmpleados_CodigoEmpleado(((Empleado)this.cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarServicios_has_Empleados(?,?,?,?,?)");
                procedimiento.setInt(1, registro.getServicios_CodigoServicio());
                procedimiento.setInt(2, registro.getEmpleados_CodigoEmpleado());
                procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(4, registro.getHoraEvento());
                procedimiento.setString(5, registro.getLugarEvento());
                procedimiento.execute();
                listaServicios_has_Empleados.add(registro);         
            }catch(Exception e){
                e.printStackTrace();
            }
        
    }     
    
    // Metodo para eliminar
     public void eliminarServiciosHasEmpleados(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            default:
                if(tblServicioshasEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Si elimina este registro se elimaran todas las cadenas del mismo", "Eliminar Servicios Has Empleados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios_has_Empleados(?)}");
                            procedimiento.setInt(1, (((Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicios_has_Empleados()));
                            procedimiento.execute();
                            listaServicios_has_Empleados.remove(tblServicioshasEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            }catch(Exception e){
                            e.printStackTrace();
                        }  
                    }    
                }else{
                JOptionPane.showMessageDialog(null, "Debe Seleccionar Un Elemento");
            }
        }
    }
    
    public void editarServiciosHasEmpleados(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServicioshasEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                      JOptionPane.showMessageDialog(null, "Debe Seleecionar Un Elemento");  
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void actualizar(){
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios_has_Empleados(?,?,?,?,?,?)}");
          Servicios_has_Empleados registro = (Servicios_has_Empleados)tblServicioshasEmpleados.getSelectionModel().getSelectedItem();
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(txtHoraEvento.getText());
            registro.setLugarEvento(txtLugarEvento.getText());
            registro.setServicios_CodigoServicio(((Servicios)this.cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
            registro.setEmpleados_CodigoEmpleado(((Empleado)this.cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.setInt(1, registro.getServicios_CodigoServicio());
            procedimiento.setInt(2, registro.getEmpleados_CodigoEmpleado());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(4, registro.getHoraEvento());
            procedimiento.setString(5, registro.getLugarEvento());
            procedimiento.setInt(6, registro.getCodigoServicios_has_Empleados());

            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnEditar.setDisable(false);
                btnReporte.setText("Reporte");
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    
    
    
    
    
    


    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
      
    }


    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
     public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
      public void ventanaServicicios(){
       escenarioPrincipal.ventanaServicicios();
       }
}