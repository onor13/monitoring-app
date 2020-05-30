package sample1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //URL fxmlSample = getClass().getResource( "fxml/sample.fxml" );
        URL fxmlSample = Thread.currentThread().getContextClassLoader().getResource("fxml/sample.fxml");
        Parent root = FXMLLoader.load( fxmlSample );
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
