
package library.assistant.settings;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Sistemas
 */
public class SettingsLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        mainWindow(stage);
    }

    private void mainWindow(Stage stage) {
        try {
            //Llamada a un formulario principal más completa:
            FXMLLoader loader = new FXMLLoader(SettingsLoader.class.getResource("/library/assistant/settings/Settings.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane, 340, 239);//En esta línea ponemos dimencionar el formulario
            
            //Agregamos una hoja de estilo
            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
            
            stage.setResizable(true);//Esto nos permite redimencionar el formulario
            
            //Esta linea de código quita los controles de cerrar minimizar y agrandar
            //stage.initStyle(StageStyle.UNDECORATED);
                        
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
            
            //Preferences.initConfig();
            
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
