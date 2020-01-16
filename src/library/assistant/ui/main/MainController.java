package library.assistant.ui.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addbook.LibraryAssistant;
import library.assistant.util.LibraryAssistantUtil;

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
//    @FXML
//    private ListView<String> issueDataList;

    Boolean isRedyForSub = false;
    @FXML
    private StackPane rootPane;
    @FXML
    private Tab tabRenewSubmission;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburger;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info, 1);
        JFXDepthManager.setDepth(member_info, 1);
        handler = DatabaseHandler.getInstance();
        //tabPane.getTabs().remove(1);

        initDrawer();
    }

    

//    void loadWindow(String loc, String title) {
//        try {
//            //Llamada a un formulario principal más completa:
//            FXMLLoader loader = new FXMLLoader(LibraryAssistant.class.getResource(loc));
//            AnchorPane pane = loader.load();
//            Scene scene = new Scene(pane, 340, 221);//En esta línea ponemos dimencionar el formulario
//
//            //Agregamos una hoja de estilo
//            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
//            Stage stage = new Stage(StageStyle.DECORATED);
//            stage.setResizable(true);//Esto nos permite redimencionar el formulario
//
//            //Esta linea de código quita los controles de cerrar minimizar y agrandar
//            //stage.initStyle(StageStyle.UNDECORATED);
//            stage.setTitle(title);
//            LibraryAssistantUtil.setStageIcon(stage);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

    
    //Este método sirve para llenar un LIST VIEW  OJO!!!!!
//    @FXML
//    private void loadBookInfo2(ActionEvent event) {
//
//        ObservableList<String> issuData = FXCollections.observableArrayList();
//        isRedyForSub = false;
//
//        String id = bookID.getText();
//        String query = "SELECT * FROM issue WHERE bookID = '" + id + "'";
//        ResultSet rs = handler.excecQuery(query);
//        try {
//            while (rs.next()) {
//                String mBookID = id;
//                String mMemberID = rs.getString("memberID");
//                Timestamp mIssueTime = rs.getTimestamp("issueTime");
//                int mRenewCount = rs.getInt("renew_count");
//
//                issuData.add("  Issue Data and Time: " + mIssueTime.toGMTString());
//                issuData.add("  Renew Count: " + mRenewCount);
//                issuData.add("\nBook Informacion:- ");
//
//                query = "SELECT * FROM book WHERE id = '" + mBookID + "'";
//                ResultSet r1 = handler.excecQuery(query);
//                while (r1.next()) {
//                    issuData.add("  Book Name: " + r1.getString("title"));
//                    issuData.add("  Book ID: " + r1.getString("id"));
//                    issuData.add("  Book Author: " + r1.getString("author"));
//                    issuData.add("  Book Publisher: " + r1.getString("publisher"));
//                }
//
//                query = "SELECT * FROM member WHERE id = '" + mMemberID + "'";
//                r1 = handler.excecQuery(query);
//                issuData.add("\nMember Information:- ");
//                while (r1.next()) {
//                    issuData.add("  Name: " + r1.getString("name"));
//                    issuData.add("  Mobile: " + r1.getString("mobile"));
//                    issuData.add("  Email: " + r1.getString("email"));
//                }
//
//                isRedyForSub = true;
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
//        }
//
//        issueDataList.getItems().setAll(issuData);
//    }//Fin método loadBookInfo2

    @FXML
    private void loadSubmissionOp(ActionEvent event) {
        if (!isRedyForSub) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to submit");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submission Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to return the book?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {

            String id = bookID.getText();
            String ac1 = "DELETE FROM issue WHERE bookid = '" + id + "'";
            String ac2 = "UPDATE book SET isavail = true WHERE id = '" + id + "'";

            if (handler.excecAction(ac1) && handler.excecAction(ac2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Succes");
                alert1.setHeaderText(null);
                alert1.setContentText("Book has been submitted");

                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Submitted has been failed");

                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void loadRenewOp(ActionEvent event) {

        if (!isRedyForSub) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to renew");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to retew the book?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String ac = "UPDATE issue SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count + 1 WHERE bookID = '" + bookID.getText() + "'";
            System.out.println(ac);
            if (handler.excecAction(ac)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Succes");
                alert1.setHeaderText(null);
                alert1.setContentText("Book has been renewed");

                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Renew has been Failed");

                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void handleMenuClose(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void handleMenuAddBook(ActionEvent event) {
        //loadWindow("/library/assistant/ui/addbook/FXMLDocument.fxml", "library.assistant.ui.addbook");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/addbook/FXMLDocument.fxml"), "Add New Book", null);
    }

    @FXML
    private void handleMenuAddMember(ActionEvent event) {
        //loadWindow("/library/assistant/ui/addmember/member_add.fxml", "library.assistant.ui.addmember");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/addmember/member_add.fxml"), "Add New Member", null);
    }

    @FXML
    private void handleMenuViewBook(ActionEvent event) {
        //loadWindow("/library/assistant/ui/listbook/Book_List.fxml", "library.assistant.ui.listbook");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/listbook/Book_List.fxml"), "Book List", null);
    }

    @FXML
    private void handleMenuViewMember(ActionEvent event) {
        //loadWindow("/library/assistant/ui/listmember/member_list.fxml", "library.assistant.ui.listmember");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/listmember/member_list.fxml"), "Member List", null);
    }

    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = ((Stage) rootPane.getScene().getWindow());
        stage.setFullScreen(true);
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("/library/assistant/ui/main/toolbar/toolbar.fxml"));
            drawer.setSidePane(toolbar);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        //HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(hamburger);
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });

    }
}
