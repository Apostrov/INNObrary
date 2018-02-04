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
    public Button pushRegedit;
    public Label incorrect;
    public MenuButton choose;
    public PasswordField passReg;
    public PasswordField retypePassReg;
    public TextField userReg;
    public Label incorrectReg;
    public boolean check = false;
    public Button signUp;
    public Button signIn;
    public AnchorPane booking;

    public void push (ActionEvent actionEvent) throws IOException {
        if (user.getText().isEmpty() || password.getText().isEmpty()) {
            incorrect.setVisible(true);
        } else {
            System.out.println(user.getText());     // When 'login' button pressed
            System.out.println(password.getText()); // When 'login' button pressed
            Stage stage;
            stage = (Stage) signIn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("booking.fxml")),600,600));
            stage.setX(700);
            stage.setY(150);
        }
    }


    public void register (ActionEvent actionEvent) throws Exception {
        try {
            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource("sign up.fxml"));
            stage = (Stage) pushRegedit.getScene().getWindow();
            stage.setTitle("sign up");
            stage.setResizable(false);
            stage.setX(650);
            stage.setY(150);
            stage.setScene(new Scene(root, 600,600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reader (ActionEvent actionEvent) {
        choose.setText("reader");
        check = true;
    }


    public void librarian (ActionEvent actionEvent) {
        choose.setText("librarian");
        check = true;
    }

    public void createAccount (ActionEvent actionEvent) throws IOException {
        if (passReg.getText().equals(retypePassReg.getText()) && (!userReg.getText().isEmpty() && !passReg.getText().isEmpty()) && check) {
            System.out.println(userReg.getText()); // String from 'username' text field
            System.out.println(passReg.getText()); // String from 'password' text field

            Stage stage;
            stage = (Stage) signUp.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
            stage.setX(300);
            stage.setY(100);
        } else {
            incorrectReg.setVisible(true);
        }
    }

    public void back (ActionEvent actionEvent)throws IOException {
        Stage stage;
        stage = (Stage) signUp.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
        stage.setX(300);
        stage.setY(100);
    }

    public void backBook (ActionEvent actionEvent)throws IOException {
        Stage stage;
        stage = (Stage) booking.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("God.fxml")),1280,720));
        stage.setX(600);
        stage.setY(150);
    }
}