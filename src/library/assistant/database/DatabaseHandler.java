package library.assistant.database;

import Modelo.Book;
//import com.sun.istack.internal.logging.Logger;
//****************NO SÉ POR QUÉ EL SISTEMA NETBEANS ME DABA ERROR TUVE QUE COMENTAR LA LÍNEA ANTERIOR
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JOptionPane;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;

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

//    public DatabaseHandler(){
//        establecerConexion();
//    }
    private DatabaseHandler() {
        establecerConexion();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public void establecerConexion() {
        try {
            //Class.forName("org.h2.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Can't load database", "Database error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            //e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet excecQuery(String query) {
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

    public boolean excecAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error ocurrido", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en excecAction: " + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean deleteBook(Book book) {

        try {
            String deleteStatement = "DELETE FROM book WHERE id = ?";
            PreparedStatement stmt;
            stmt = connection.prepareStatement(deleteStatement);
            stmt.setString(1, book.getId());
            int res = stmt.executeUpdate();
            if(res == 1){
                return true;
            }            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
