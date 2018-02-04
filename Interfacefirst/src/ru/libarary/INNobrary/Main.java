package ru.libarary.INNobrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
   // public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("God.fxml"));
            primaryStage.setTitle("INNObrary");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setMinHeight(0);
            primaryStage.setMinWidth(0);
            primaryStage.setResizable(false);
            primaryStage.setX(300);
            primaryStage.setY(100);
            primaryStage.show();

           // this.primaryStage = primaryStage;

    }



    public static void main(String[] args) {
        launch(args);
    }
}
