package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Service;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Service newService = new Service();
        newService.readFromFile("date.in");
        DataTransfer.setService(newService);

        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("My app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
