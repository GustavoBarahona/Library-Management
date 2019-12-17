/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.addbook;

import Modelo.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import library.assistant.database.DatabaseHandler;


/**
 *
 * @author Gustavo
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    
    DatabaseHandler databasehandler;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = new DatabaseHandler();
    }    

    @FXML
    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        
        if(bookID.isEmpty()||bookAuthor.isEmpty()||bookName.isEmpty()||bookPublisher.isEmpty()){
            Alert msj = new Alert(Alert.AlertType.ERROR);
            msj.setHeaderText(null);
            msj.setContentText("Please Enter in all fields");
            msj.showAndWait();
            return;
        }else{
            Book libro = new Book(bookID, bookName, bookAuthor, bookPublisher, true);
            databasehandler.establecerConexion();
            libro.guardarRegistro(databasehandler.getConnection());
            databasehandler.cerrarConexion();            
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
        
}
