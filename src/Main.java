import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static User user; // Singleton of user

    /** Main method of INNObrary */
    public static void main(String[] args) {
        loadGlobalData();
        String username = getUsername();  // TODO: get username from input
        user = new User(login(username));
 
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
    private static int login (String username) {
        int cardID = 0;
        cardID = loadFromDB("key = username"); // TODO: load from database card ID based on 'username'
        return cardID;
    }

    /** Registration with name, password, address, phone number and whether person is faculty member or not
     *  @param name username
     *  @param password password
     *  @param address user's address
     *  @param  phone user's phone number
     *  @param isFaculty whether person is faculty member or not */
    private static int register (String name, String password, String address, String phone, boolean isFaculty) {

        Patron patron = new Patron(name, password, address, phone, isFaculty);
        DataBase.addUser(patron);
        return maxCardID++;
    }

    /** Makes a booking */
    private static void book (Document doc) {

    }

}
