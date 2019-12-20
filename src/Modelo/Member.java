
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Member {
    private StringProperty id;
    private StringProperty name;
    private StringProperty mobile;
    private StringProperty email;

    public Member(String id, String name, String mobile, String email) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.mobile = new SimpleStringProperty(mobile);
        this.email = new SimpleStringProperty(email);
    }

    
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getMobile() {
        return mobile.get();
    }

    public void setMobile(String mobile) {
        this.mobile = new SimpleStringProperty(mobile);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    
    
    /**
     * @return **********************************************************************************/
        
    
    public StringProperty idProperty(){
        return id;
    }
    
    public StringProperty nameProperty(){
        return name;
    }
    
    public StringProperty mobileProperty(){
        return mobile;
    }
    
    public StringProperty emailProperty(){
        return email;
    }
    
    /******************************************************************************/
    /********************  METODOS  **********************************************/
    
    public int guardarRegistro(Connection conexion){
        try {
            PreparedStatement instruccion
                    = conexion.prepareStatement("INSERT INTO member(id, name, mobile, email)"
                            + "VALUES(?,?,?,?)");
            instruccion.setString(1, id.get());
            instruccion.setString(2, name.get());
            instruccion.setString(3, mobile.get());
            instruccion.setString(4, email.get());
            
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }    
    
}
