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
import org.pabloarceo.system.MainApp;
import org.pabloarceo.bean.Producto;
import org.pabloarceo.db.Conexion;


public class ProductosController implements Initializable {
    private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
    private Operacion tipoOperacion = Operacion.NINGUNO; 
    private ObservableList <Producto>listaProducto;
     
    private MainApp escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblproductos;
    @FXML private TableColumn colnombreProductos;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colcodigoProductos;
    @FXML private TextField txtcodigoProducto;
    @FXML private TextField txtnombreProducto;
    @FXML private TextField txtcantidad;
  
    
    // Metodo desactivar controles
       public void desactivarControles(){
    txtcodigoProducto.setEditable(false);
    txtnombreProducto.setEditable(false);
    txtcantidad.setEditable(false);
    }
    // Metodo activar controles
    public void activarControles(){
    txtcodigoProducto.setEditable(false);
    txtnombreProducto.setEditable(true);
    txtcantidad.setEditable(true);
    }
    
    // Metodo limpiar controles
    public void limpiarControles(){
    txtcodigoProducto.setText("");
    txtnombreProducto.setText("");
    txtcantidad.setText("");
    
    
    
    }
    
        // Metodo para seleccionar elementos de la tabla y mostrarlos en los campos
    public void SeleccionarElementos(){
    txtcodigoProducto.setText(String.valueOf(((Producto)tblproductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    txtnombreProducto.setText((((Producto)tblproductos.getSelectionModel().getSelectedItem()).getNombreProducto()));
    txtcantidad.setText(String.valueOf(((Producto)tblproductos.getSelectionModel().getSelectedItem()).getCantidad()));
    }
    
    //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        
        tblproductos.setItems(getProducto());
        colcodigoProductos.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProducto"));
        colnombreProductos.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, String>("cantidad"));

        
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
    Producto productoNuevo = new Producto();
    productoNuevo.setNombreProducto(txtnombreProducto.getText());
    productoNuevo.setCantidad(Integer.parseInt(txtcantidad.getText()));
    try{
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?,?)}");
      sp.setString(1,productoNuevo.getNombreProducto());
      sp.setInt(2,productoNuevo.getCantidad());
      sp.execute();
      listaProducto.add(productoNuevo);
      
    }catch(Exception e){
     e.printStackTrace();
    }
    
    
        
    }
    
    // Metodo para actualizar 
    
    // Editar-Actualizar Empresas 
  public void editarProductos(){
    switch(tipoOperacion){
        case NINGUNO:
            if (tblproductos.getSelectionModel().getSelectedItem() != null){
                
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
       int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de actualizar el registro?","Actualizar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
      PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
      Producto productoActualizado = ((Producto)tblproductos.getSelectionModel().getSelectedItem());
      // obteniendo los datos a la vista al modelo en java
      productoActualizado.setNombreProducto(txtnombreProducto.getText());
      productoActualizado.setCantidad(Integer.parseInt(txtcantidad.getText()));
      // Enviando los datos actualizados a ejecutar en el objeto sp
      sp.setString(1,productoActualizado.getNombreProducto());
      sp.setInt(2, productoActualizado.getCantidad());
      sp.setInt(3, productoActualizado.getCodigoProducto());
      sp.execute();

             
      // 
      JOptionPane.showMessageDialog(null, "Datos Actualizado");
      
          }    
  }catch(Exception e){
      e.printStackTrace();
  }
    
  }
    
     // Metodo para eliminar Presupuestos
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
          if(tblproductos.getSelectionModel().getSelectedItem() != null ){
          int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if(respuesta == JOptionPane.YES_OPTION){
          try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
             sp.setInt(1,((Producto)tblproductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
             sp.execute(); 
             listaProducto.remove(tblproductos.getSelectionModel().getSelectedIndex());
             limpiarControles();
             JOptionPane.showMessageDialog(null,"Producto eliminada en efecto");
             
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
