
package library.assistant.ui.listbook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Sistemas
 */
public class BookListController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> titleCol;
    @FXML
    private TableColumn<?, ?> idCol;
    @FXML
    private TableColumn<?, ?> authorCol;
    @FXML
    private TableColumn<?, ?> publisherCol;
    @FXML
    private TableColumn<?, ?> availabilityCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
