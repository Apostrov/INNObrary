import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    static ArrayList<User> users;
    static ArrayList<User> librarians;


    /** Main method of INNObrary */
    public static void main(String[] args) {
		users = new ArrayList<>();
        librarians = new ArrayList<>();

		for (int i = 0; i < 3; ++i) {
		    Patron nextPatron = new Patron("patron_" + (i + 1));
		    users.add(nextPatron);
        }
        librarians.add(new Librarian("librarian_1"));

        launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("God.fxml"));
            primaryStage.setTitle("INNObrary");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setMinHeight(0);
            primaryStage.setMinWidth(0);
            primaryStage.setResizable(false);
            primaryStage.setX(300);
            primaryStage.setY(100);
            primaryStage.show();
    }

    static void login (String username, String password) {

    }

    static void register (String name, String password, String address, String phone, boolean isFaculty) {

    }

}
