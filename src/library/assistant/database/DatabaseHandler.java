package library.assistant.database;

import Modelo.Book;
import Modelo.Member;
//import com.sun.istack.internal.logging.Logger;
//****************NO SÉ POR QUÉ EL SISTEMA NETBEANS ME DABA ERROR TUVE QUE COMENTAR LA LÍNEA ANTERIOR
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
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
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isBookAlreadyIssue(Book book) {

        try {
            String checkstmt = "SELECT COUNT(*) FROM issue WHERE bookID = ?";
            PreparedStatement stmt = connection.prepareStatement(checkstmt);
            stmt.setString(1, book.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateBook(Book book) {

        try {
            String update = "UPDATE book set title=?, author = ?, publisher = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getId());
            int res = stmt.executeUpdate();
            return (res > 0);            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateMember(Member member) {

        try {
            String update = "UPDATE member set name=?, mobile = ?, email = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getMobile());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getId());
            int res = stmt.executeUpdate();
            return (res > 0);            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteMember(Member member) {

        try {
            String deleteStatement = "DELETE FROM member WHERE id = ?";
            PreparedStatement stmt;
            stmt = connection.prepareStatement(deleteStatement);
            stmt.setString(1, member.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean isMemberAlreadyIssue(Member member) {

        try {
            String checkstmt = "SELECT COUNT(*) FROM issue WHERE memberID = ?";
            PreparedStatement stmt = connection.prepareStatement(checkstmt);
            stmt.setString(1, member.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void main(String[] args) throws Exception{
        
    }
    
    public ObservableList<PieChart.Data> getBookGraphicStatistics(){
        try {
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            String qry1 = "SELECT COUNT(*) FROM book";
            String qry2 = "SELECT COUNT(*) FROM issue";
            //ResultSet rs 
        } catch (Exception e) {
        }
        return null;
    }
}
