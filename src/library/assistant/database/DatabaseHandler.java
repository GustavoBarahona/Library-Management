package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    
    private Connection connection;
    //private String url = "jdbc:h2:file:./src/BaseH2/libreria_db";
    private String url = "jdbc:mysql://bujgjsuf73do1mmlrclk-mysql.services.clever-cloud.com:3306/bujgjsuf73do1mmlrclk?autoReconnect=true&useSSL=false";
    private String usuario = "ubetdr4s6bsd53vv";
    private String password = "bAUNw3XDgndy9huxXzxd";
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public DatabaseHandler(){
        establecerConexion();
    }
    
    public void establecerConexion(){
        try {
            //Class.forName("org.h2.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
        
    public void cerrarConexion(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
