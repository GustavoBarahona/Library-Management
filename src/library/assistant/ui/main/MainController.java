package library.assistant.ui.main;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addbook.LibraryAssistant;

/**
 * FXML Controller class
 *
 * @author Gustavo
 */
public class MainController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private TextField bookIDImput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;

    DatabaseHandler handler;
    @FXML
    private TextField memberIDImput;
    @FXML
    private Text memberName;
    @FXML
    private Text contact;
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> issueDataList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info, 1);
        JFXDepthManager.setDepth(member_info, 1);
        handler = DatabaseHandler.getInstance();
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/library/assistant/ui/addmember/member_add.fxml", "library.assistant.ui.addmember");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/library/assistant/ui/addbook/FXMLDocument.fxml", "library.assistant.ui.addbook");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listmember/member_list.fxml", "library.assistant.ui.listmember");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listbook/Book_List.fxml", "library.assistant.ui.listbook");
    }

    void loadWindow(String loc, String title) {
        try {
            //Llamada a un formulario principal más completa:
            FXMLLoader loader = new FXMLLoader(LibraryAssistant.class.getResource(loc));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane, 340, 221);//En esta línea ponemos dimencionar el formulario

            //Agregamos una hoja de estilo
            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(true);//Esto nos permite redimencionar el formulario

            //Esta linea de código quita los controles de cerrar minimizar y agrandar
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        String id = bookIDImput.getText();
        String query = "SELECT * FROM book WHERE id = '" + id + "'";
        ResultSet rs = handler.excecQuery(query);
        Boolean flag = false;
        try {
            while (rs.next()) {
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");

                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus) ? "Available" : "Not Available";
                bookStatus.setText(status);

                flag = true;
            }
            if (!flag) {
                bookName.setText("No such book Available");
            }
        } catch (SQLException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        String id = memberIDImput.getText();
        String query = "SELECT * FROM member WHERE id = '" + id + "'";
        ResultSet rs = handler.excecQuery(query);
        Boolean flag = false;
        try {
            while (rs.next()) {
                String mName = rs.getString("name");
                String mContact = rs.getString("mobile");

                memberName.setText(mName);
                contact.setText(mContact);

                flag = true;
            }

            if (!flag) {
                memberName.setText("No Such Member Available");
            }

        } catch (SQLException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String memberID = memberIDImput.getText();
        String bookID = bookIDImput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to issue the book" + bookName.getText() + "\n to " + memberName.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String str1 = "INSERT INTO issue(memberID, bookID) VALUES('" + memberID + "', '" + bookID + "')";
            String str2 = "UPDATE book SET isavail = false WHERE id = '" + bookID + "'";
            System.out.println(str1 + " and " + str2);
            if (handler.excecAction(str1) && handler.excecAction(str2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Book Issue Complete");
                alert.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setHeaderText(null);
                alert.setContentText("Issue Operation Failed");
                alert.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cancelled");
            alert.setHeaderText(null);
            alert.setContentText("Issue Operation Cancelled");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadBookInfo2(ActionEvent event) {

        ObservableList<String> issuData = FXCollections.observableArrayList();

        String id = bookID.getText();
        String query = "SELECT * FROM issue WHERE bookID = '" + id + "'";
        ResultSet rs = handler.excecQuery(query);
        try {
            while (rs.next()) {
                String mBookID = id;
                String mMemberID = rs.getString("memberID");
                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");

                issuData.add("Issue Data and Time: " + mIssueTime.toGMTString());
                issuData.add("Renew Count: " + mRenewCount);
                issuData.add("Book Informacion:- ");

                query = "SELECT * FROM book WHERE id = '" + mBookID + "'";
                ResultSet r1 = handler.excecQuery(query);
                while (r1.next()) {
                    issuData.add("Book Name: " + r1.getString("title"));
                    issuData.add("Book ID: " + r1.getString("id"));
                    issuData.add("Book Author: " + r1.getString("author"));
                    issuData.add("Book Publisher: " + r1.getString("publisher"));
                }

                query = "SELECT * FROM member WHERE id = '" + mMemberID + "'";
                r1 = handler.excecQuery(query);
                issuData.add("Member Information:- ");
                while (r1.next()) {
                    issuData.add("Name: " + r1.getString("name"));
                    issuData.add("Mobile: " + r1.getString("mobile"));
                    issuData.add("Email: " + r1.getString("email"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        issueDataList.getItems().setAll(issuData);
    }//Fin método loadBookInfo2

}