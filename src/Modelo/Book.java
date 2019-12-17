
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Book {
    private StringProperty id;
    private StringProperty title;
    private StringProperty author;
    private StringProperty publisher;
    private BooleanProperty isavail;
    
    public Book(String id, String title, String author, String publisher, Boolean isavail){
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.isavail= new SimpleBooleanProperty(isavail);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author = new SimpleStringProperty(author);
    }

    public StringProperty getPublisher() {
        return publisher;
    }

    public void setPublisher(StringProperty publisher) {
        this.publisher = publisher;
    }

    public Boolean getIsavail() {
        return isavail.get();
    }

    public void setIsavail(Boolean isavail) {
        this.isavail = new SimpleBooleanProperty(isavail);
    }
    
    /************************************************************************************/
        
    public StringProperty idProperty(){
        return id;
    }
    
    public StringProperty titleProperty(){
        return title;
    }
    
    public StringProperty authorProperty(){
        return author;
    }
    
    public StringProperty publisherProperty(){
        return publisher;
    }
    
    public BooleanProperty isavailableProperty(){
        return isavail;
    }
    
    /******************************************************************************/
    /********************  METODOS  **********************************************/
    
    public int guardarRegistro(Connection conexion){
        try {
            PreparedStatement instruccion
                    = conexion.prepareStatement("INSERT INTO book(id, title, author, publisher, isavail)"
                            + "VALUES(?,?,?,?,?)");
            instruccion.setString(1, id.get());
            instruccion.setString(2, title.get());
            instruccion.setString(3, author.get());
            instruccion.setString(4, publisher.get());
            instruccion.setBoolean(5, isavail.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }    
    
}
