package library.assistant.ui.listmember;

import Modelo.Member;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
 * @author Gustavo
 */
public class MemberlistController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> memberCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;
    
    ObservableList<Member> List = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }
    
    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        memberCol.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<Member, String>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Member, String>("email"));        
    }

    private void loadData() {
        //DatabaseHandler handler = new DatabaseHandler();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM member";
        ResultSet rs = handler.excecQuery(query);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("id");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                
                List.add(new Member(id, name, mobile, email)); //ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.getItems().setAll(List);
    }

}
