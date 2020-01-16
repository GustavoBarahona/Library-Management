package library.assistant.ui.main.toolbar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.assistant.util.LibraryAssistantUtil;


public class ToolbarController implements Initializable {

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    @FXML
    private void loadAddMember(ActionEvent event) {
        //loadWindow("/library/assistant/ui/addmember/member_add.fxml", "library.assistant.ui.addmember");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/addmember/member_add.fxml"), "Add New Member", null);
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        //loadWindow("/library/assistant/ui/addbook/FXMLDocument.fxml", "library.assistant.ui.addbook");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/addbook/FXMLDocument.fxml"), "Add New Book", null);
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        //loadWindow("/library/assistant/ui/listmember/member_list.fxml", "library.assistant.ui.listmember");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/listmember/member_list.fxml"), "Member List", null);
        
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        //loadWindow("/library/assistant/ui/listbook/Book_List.fxml", "library.assistant.ui.listbook");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/ui/listbook/Book_List.fxml"), "Book List", null);
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        //loadWindow("/library/assistant/settings/Settings.fxml", "library.assistant.settings");
        LibraryAssistantUtil.loadWindow(getClass().getResource("/library/assistant/settings/Settings.fxml"), "Settings", null);
    }   
    
}
