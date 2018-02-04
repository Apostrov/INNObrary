import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** Main method of INNObrary */
    public static void main(String[] args) {
        // String username = getUsername();  // TODO: get username from input
		launch(args);
	}

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
    }
	
    /** Login with username and return user's card ID
     *  @param username unique name that belongs only to single user
     *  @return corresponding card ID to username */
    private static void login (String username) {
        // TODO:
    }

    /** Registration with name, password, address, phone number and whether person is faculty member or not
     *  @param name username
     *  @param password password
     *  @param address user's address
     *  @param  phone user's phone number
     *  @param isFaculty whether person is faculty member or not */
    private static void register (String name, String password, String address, String phone, boolean isFaculty) {
        Patron patron = new Patron(name, password, address, phone, isFaculty);
        DataBase.addUser(patron);
    }

}
