package library.assistant.ui.listmember;

import Modelo.Book;
import Modelo.Member;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistant.alert.AlertMaker;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addbook.BookAddController;
import library.assistant.ui.addbook.LibraryAssistant;
import library.assistant.ui.addmember.MemberAddController;
import library.assistant.util.LibraryAssistantUtil;

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
        List.clear();
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

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void handleMemberEditOp(ActionEvent event) {
        Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for deletion");
            return;
        }

        try {
            //Llamada a un formulario principal más completa:
            FXMLLoader loader = new FXMLLoader(LibraryAssistant.class.getResource("/library/assistant/ui/addmember/member_add.fxml"));
            AnchorPane pane = loader.load();

            //Con esto controlamos el controller:... Ojo super util
            MemberAddController controller = (MemberAddController) loader.getController();
            //BookAddController controller = loader.getController(); funciona igual pero dejé el de arriba por si acaso
            controller.inflateUI(selectedForDeletion);

            Scene scene = new Scene(pane, 340, 221);//En esta línea ponemos dimencionar el formulario

            //Agregamos una hoja de estilo
            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(true);//Esto nos permite redimencionar el formulario

            //Esta linea de código quita los controles de cerrar minimizar y agrandar
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit Member Option");
            LibraryAssistantUtil.setStageIcon(stage);

            stage.setOnCloseRequest((e) -> {
                handleRefresh(new ActionEvent());
            });

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMemberDeleteOp(ActionEvent event) {
        Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for deletion");
            return;
        }

        Boolean memberllreadytodeletion = DatabaseHandler.getInstance().isMemberAlreadyIssue(selectedForDeletion);
        if (memberllreadytodeletion) {
            //El libro si está publicado
            AlertMaker.showErrorMessage("Failed", "Member allready issue");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Member");
        alert.setContentText("Are you sure want to delete the member: " + selectedForDeletion.getName() + "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteMember(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("Member deleted", selectedForDeletion.getName() + " was deleted successfully");
                //List.remove(selectedForDeletion);
                handleRefresh(event);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getName() + " was deleted unsuccessfully");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
    }

}
