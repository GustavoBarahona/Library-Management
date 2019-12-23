package library.assistant.ui.listbook;

import Modelo.Book;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
            while(rs.next()){
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
        
        tableView.getItems().setAll(List);
    }

    

}
