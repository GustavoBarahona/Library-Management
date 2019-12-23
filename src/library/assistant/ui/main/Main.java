
package library.assistant.ui.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Sistemas
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        mainWindow(stage);
    }

    private void mainWindow(Stage stage) {
        try {
            //Llamada a un formulario principal más completa:
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));
            StackPane sp = loader.load();
            Scene scene = new Scene(sp, 600, 356);//En esta línea ponemos dimencionar el formulario
            
            //Agregamos una hoja de estilo
            //scene.getStylesheets().addAll(getClass().getResource("/library.assistant.ui.addbook/addbook.css").toExternalForm());
            
            stage.setResizable(true);//Esto nos permite redimencionar el formulario
            
            //Esta linea de código quita los controles de cerrar minimizar y agrandar
            //stage.initStyle(StageStyle.UNDECORATED);
                        
            stage.setTitle("library.assistant.ui.main");
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
