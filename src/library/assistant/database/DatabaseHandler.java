package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseHandler {
    
    
    private Statement statement;
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
    
    public ResultSet excecQuery(String query){
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);            
        } catch (SQLException e) {
            System.out.println("Error en excecQuery: " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }
    
    public boolean excecAction(String query){
        try {
            statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error ocurrido", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en excecAction: " + e.getLocalizedMessage());
            return false;
        }
        
    }
}
