package org.pabloarceo.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    //Creamos un metodo publico para la conexion
    private Connection conexion;
    
    //Creamos un metodo estatico para aplicar el patron Singleton
    private static Conexion instancia;
    
    public Conexion(){
    
        try{
    
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtonyskinal2016416?useSSL=false","root","admin");

        }catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e){
    
            e.printStackTrace();
    
        }
    
    }
    
    public static Conexion getInstance(){
        
        if(instancia == null){
            
            instancia= new Conexion();
            
        }
        
        return instancia;
        
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    
}
