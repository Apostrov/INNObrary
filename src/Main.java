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

    /**
     * Here we create a scene
     * @param primaryStage  main interface window
     * @throws Exception
     */
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

    static void login (String username, String password) {
        User user = new User(username);
    }

    static void register (String name, String password, String address, String phone, boolean isFaculty) {

    }

}
