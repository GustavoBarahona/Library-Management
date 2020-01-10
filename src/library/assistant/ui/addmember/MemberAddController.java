package library.assistant.ui.addmember;

import Modelo.Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
 * FXML Controller class
 *
 * @author Sistemas
 */
public class MemberAddController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    DatabaseHandler databasehandler;
    @FXML
    private AnchorPane rootPane;

    private boolean isInEditMode = Boolean.FALSE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //databasehandler = new DatabaseHandler();
        databasehandler = DatabaseHandler.getInstance();
        chekData();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addMember(ActionEvent event) {
        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        if(isInEditMode){
            handleEditOperation();
            return;
        }

        Member member = new Member(mID, mName, mMobile, mEmail);
        databasehandler.establecerConexion();
        int resultado = member.guardarRegistro(databasehandler.getConnection());
        databasehandler.cerrarConexion();
        if (resultado == 1) {
            Alert msj = new Alert(Alert.AlertType.INFORMATION);
            msj.setTitle("Registro agregado");
            msj.setContentText("El registro ha sido agregado exitósamente");
            msj.setHeaderText("Resultado");
            msj.show();
        }

    }

    private void chekData() {
        String sql = "SELECT name FROM member";
        ResultSet rs = databasehandler.excecQuery(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inflateUI(Member member) {
        name.setText(member.getName());
        id.setText(member.getId());
        mobile.setText(member.getMobile());
        email.setText(member.getEmail());
        id.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }
    
    private void handleEditOperation() {
        Member member = new Member(id.getText(), name.getText(), mobile.getText(), email.getText());
        if(databasehandler.updateMember(member)){
            AlertMaker.showSimpleAlert("Success", "Member Updated");
        }else{
            AlertMaker.showSimpleAlert("Failed", "Can updated member");
        }
                
    }

}
