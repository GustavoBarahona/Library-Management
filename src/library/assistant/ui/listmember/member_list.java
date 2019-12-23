package library.assistant.ui.listmember;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.ui.listbook.Book_list;

/**
 *
 * @author Gustavo
 */
public class member_list extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();

        mainWindow(stage);
    }

    private void mainWindow(Stage stage) {
        try {
            //Llamada a un formulario principal más completa:
            FXMLLoader loader = new FXMLLoader(member_list.class.getResource("member_list.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane, 340, 221);//En esta línea ponemos dimencionar el formulario

            //Agregamos una hoja de estilo
            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
            stage.setResizable(true);//Esto nos permite redimencionar el formulario

            //Esta linea de código quita los controles de cerrar minimizar y agrandar
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("library.assistant.ui.listmember");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}