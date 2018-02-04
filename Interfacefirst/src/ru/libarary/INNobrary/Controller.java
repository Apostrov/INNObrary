package ru.libarary.INNobrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public TextField user;
    public PasswordField password;
    public Button push_regedit;
    public Label incorrect;
    public MenuButton choose;
    public PasswordField passReg;
    public PasswordField retypepassReg;
    public TextField userReg;
    public Label incorrectReg;
    public MenuItem Reader;
    public MenuItem Librarian;
    public boolean check = false;
    public Button Createacc;
    public Button signin;
    public AnchorPane booking;

    public void push(ActionEvent actionEvent)throws IOException {
        if(user.getText().isEmpty() || password.getText().isEmpty()){incorrect.setVisible(true);

        }
        else {
            System.out.println(user.getText());
            System.out.println(password.getText());
            Stage stage;

            stage = (Stage) signin.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("booking.fxml")),600,600));
            stage.setX(700);
            stage.setY(150);

        }
    }


    public void regestr(ActionEvent actionEvent)throws Exception {
             try{

                 Stage stage;
                 Parent root = FXMLLoader.load(getClass().getResource("sign up.fxml"));
                 stage = (Stage) push_regedit.getScene().getWindow();
                 stage.setTitle("sign up");
                 stage.setResizable(false);
                 stage.setX(650);
                 stage.setY(150);
                 stage.setScene(new Scene(root, 600,600));
                 stage.show();

                // Main.primaryStage.close();



             } catch (IOException e) {
                 e.printStackTrace();
             }

    }

    public void Reader(ActionEvent actionEvent) {
        choose.setText("Reader");
        check = true;

    }


    public void Librarian(ActionEvent actionEvent) {
        choose.setText("Librarian");
        check = true;
    }

    public void Create_an_account(ActionEvent actionEvent)throws IOException {
        if(passReg.getText().equals(retypepassReg.getText()) && (!userReg.getText().isEmpty() && !passReg.getText().isEmpty()) && check){
        System.out.println(userReg.getText());
        System.out.println(passReg.getText());
        Stage stage;

        stage = (Stage) Createacc.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
        stage.setX(300);
        stage.setY(100);

        }
        else {
            incorrectReg.setVisible(true);
        }
    }

    public void back(ActionEvent actionEvent)throws IOException {
        Stage stage;

        stage = (Stage) Createacc.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
        stage.setX(300);
        stage.setY(100);
    }

    public void back_book(ActionEvent actionEvent)throws IOException {

        Stage stage;

        stage = (Stage) booking.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
        stage.setX(600);
        stage.setY(150);
    }
}
