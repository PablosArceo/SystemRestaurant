
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pabloarceo.bean.Plato;
import org.pabloarceo.bean.TipoPlato;
import org.pabloarceo.db.Conexion;
import org.pabloarceo.system.MainApp;



public class PlatosController implements Initializable{
     private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO; 
    private ObservableList<Plato>listaPlato;
        private ObservableList<TipoPlato>listaTipoPlato;

    private MainApp escenarioPrincipal;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @FXML private TableView tblPlatos;
            
    @FXML private TableColumn colCodigoPlato;      
    @FXML private TableColumn ColCantidad;
    @FXML private TableColumn colNombrePlato;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colPrecioPlato;
    @FXML private TableColumn colCodigoTipoPlato;       
            
    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField  txtDescripcion;
    @FXML private TextField  txtPrecioPlato;
            
    @FXML private ComboBox    cmbCodigoTipoPlato;     
    
    
    public void activarControles(){
    txtCodigoPlato.setEditable(false);
    txtCantidad.setEditable(true);
    txtNombrePlato.setEditable(true);
    txtDescripcion.setEditable(true);
    txtPrecioPlato.setEditable(true);
    cmbCodigoTipoPlato.setDisable(false);
    }
    
    public void desactivarControles(){
    txtCodigoPlato.setDisable(false);
    txtCantidad.setEditable(false);
    txtNombrePlato.setEditable(false);
    txtDescripcion.setEditable(false);
    txtPrecioPlato.setEditable(false);
    cmbCodigoTipoPlato.setDisable(false);
   
    }
    
    public void limpiarControles(){
    txtCodigoPlato.setText("");
    txtCantidad.setText("");
    txtNombrePlato.setText("");
    txtDescripcion.setText("");
    txtPrecioPlato.setText("");
    cmbCodigoTipoPlato.getSelectionModel().clearSelection();
         
    }
    
    
    public void cargarDatos(){
        
        tblPlatos.setItems(getPlato());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoPlato"));
        ColCantidad.setCellValueFactory(new PropertyValueFactory<Plato,Integer >("cantidad"));
        colNombrePlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("nombrePlato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Plato, String>("descripcionPlato"));
        colPrecioPlato.setCellValueFactory(new PropertyValueFactory<Plato, Double>("precioPlato"));
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("TipoPlato_codigoTipoPlato"));
        cmbCodigoTipoPlato.setItems(getTipoPlato());
        
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
    
       public void seleccionarElementos(){
        txtCodigoPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtCantidad.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtNombrePlato.setText((((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato()));
        txtDescripcion.setText((((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato()));
        txtPrecioPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));                                                                          
        cmbCodigoTipoPlato.getSelectionModel().select(buscarTipoPlato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getTipoPlato_codigoTipoPlato()));
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
     
     //Metodo Para Agregar Plato
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
       Plato registro = new Plato();
       registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
       registro.setNombrePlato((txtNombrePlato.getText()));
       registro.setDescripcionPlato((txtDescripcion.getText()));
       registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
       registro.setTipoPlato_codigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
        
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlatos(?,?,?,?,?)}");
           procedimiento.setInt(1, registro.getCantidad());
           procedimiento.setString(2, registro.getNombrePlato());
           procedimiento.setString(3,registro.getDescripcionPlato());
           procedimiento.setDouble(4,registro.getPrecioPlato());
           procedimiento.setInt(5,registro.getTipoPlato_codigoTipoPlato());
           procedimiento.execute();
           
           listaPlato.add(registro);
                   
       }catch(Exception e){
       e.printStackTrace();
       } 
       } 
     
    // Metodo para editar
     public void editarPlatos(){
  switch(tipoOperacion){
        case NINGUNO:
            if (tblPlatos.getSelectionModel().getSelectedItem() != null){
                
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
      
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlatos(?,?,?,?,?,?)}");
       Plato registro = ((Plato)tblPlatos.getSelectionModel().getSelectedItem());
       registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
       registro.setNombrePlato((txtNombrePlato.getText()));
       registro.setDescripcionPlato((txtDescripcion.getText()));
       registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
      // Enviando los datos actualizados a ejecutar en el objeto sp
      procedimiento.setInt(1,registro.getCantidad());
      procedimiento.setString(2,registro.getNombrePlato());
      procedimiento.setString(3,registro.getDescripcionPlato());
      procedimiento.setDouble(4,registro.getPrecioPlato());
      procedimiento.setInt(5,registro.getTipoPlato_codigoTipoPlato());
      procedimiento.setInt(6,registro.getCodigoPlato());
      procedimiento.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }  
      // Metodo para eliminar plato
  public void  eliminarPlato(){
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
          if(tblPlatos.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPlatos(?)}");
             sp.setInt(1,((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
             sp.execute(); 
             listaPlato.remove(tblPlatos.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Plato eliminada en efecto");
             
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
        cmbCodigoTipoPlato.setItems(getTipoPlato());

        
    }
    
    public void menuPrincipal(){
        
        escenarioPrincipal.menuPrincipal();
        
    }
    
   
    
  
 
}
    

