package library.assistant.ui.addbook;

import Modelo.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.alert.AlertMaker;

import library.assistant.database.DatabaseHandler;

/**
 *
 * @author Gustavo
 */
public class BookAddController implements Initializable {

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

    @FXML
    private AnchorPane rootPane;

    private boolean isInEditMode = Boolean.FALSE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //databasehandler = new DatabaseHandler();
        databasehandler = DatabaseHandler.getInstance();
        chekData();
    }

    @FXML
    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {
            Alert msj = new Alert(Alert.AlertType.ERROR);
            msj.setHeaderText(null);
            msj.setContentText("Please Enter in all fields");
            msj.showAndWait();
            return;
        }
        
        if(isInEditMode){
            handleEditOperation();
            return;
        }
        
        Book libro = new Book(bookID, bookName, bookAuthor, bookPublisher, true);
        databasehandler.establecerConexion();
        int resultado = libro.guardarRegistro(databasehandler.getConnection());
        databasehandler.cerrarConexion();
        if (resultado == 1) {
            Alert msj = new Alert(Alert.AlertType.INFORMATION);
            msj.setTitle("Registro agregado");
            msj.setContentText("El registro ha sido agregado exitósamente");
            msj.setHeaderText("Resultado");
            msj.show();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void chekData() {
        String sql = "SELECT title FROM book";
        ResultSet rs = databasehandler.excecQuery(sql);
        try {
            while (rs.next()) {
                String title = rs.getString("title");
                System.out.println(title);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inflateUI(Book book) {
        title.setText(book.getTitle());
        id.setText(book.getId());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        id.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }

    private void handleEditOperation() {
        Book book = new Book(id.getText(), title.getText(), author.getText(), publisher.getText(), true);
        if(databasehandler.updateBook(book)){
            AlertMaker.showSimpleAlert("Success", "Book Updated");
        }else{
            AlertMaker.showSimpleAlert("Failed", "Can updated book");
        }
                
    }
    
    
}
