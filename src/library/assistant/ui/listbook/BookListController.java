package library.assistant.ui.listbook;

import Modelo.Book;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.assistant.alert.AlertMaker;
import library.assistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Sistemas
 */
public class BookListController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

    ObservableList<Book> List = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("isavail"));
    }

    private void loadData() {
        //DatabaseHandler handler = new DatabaseHandler();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM book";
        ResultSet rs = handler.excecQuery(query);
        try {
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String id = rs.getString("id");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isavail");

                List.add(new Book(id, title, author, publisher, avail)); //ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //tableView.getItems().setAll(List);
        tableView.setItems(List); //De esta manera funciona (remove) que está más abajo de estas lineas de codigo
    }

    @FXML
    private void handleBookDeleteOp(ActionEvent event) {
        Book selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion");
            return;
        }

        Boolean bookallreadytodeletion = DatabaseHandler.getInstance().isBookAlreadyIssue(selectedForDeletion);

        if (bookallreadytodeletion) {
            //El libro si está publicado
            AlertMaker.showErrorMessage("Failed", "Book allready issue");
            return;
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting book");
            alert.setContentText("Are you sure want to delete the book: " + selectedForDeletion.getTitle() + "?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                Boolean result = DatabaseHandler.getInstance().deleteBook(selectedForDeletion);
                if (result) {
                    AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getTitle() + " was deleted successfully");
                    List.remove(selectedForDeletion);
                } else {
                    AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getTitle() + " was deleted successfully");
                }
            } else {
                AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
            }
        }

    }

}
