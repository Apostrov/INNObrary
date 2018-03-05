import java.util.ArrayList;

public class Main {

    static User activeUser = null;
    static ArrayList<User> users;
    static ArrayList<Document> documents;

    static LoginScreen login;
    static CabinetScreen cabinet;
    static RegistrationScreen register;
    static UserViewScreen userView;
    static UserAddScreen userAdd;
    static UserModifyScreen userMod;
    static DocAddScreen docAdd;
    static DocModifyScreen docMod;
    static ProfChangeScreen changeProf;

    /** Main method of INNObrary */
    public static void main(String[] args) {
        // Run-time storage
        users = DataBase.getAllUser();
        documents = DataBase.getAllDoc();

        users.add(new Librarian("admin", "admin"));

        // Load interface
        login = new LoginScreen();
        register = new RegistrationScreen();
        cabinet = new CabinetScreen(false);
        userView = new UserViewScreen(null);
        userAdd = new UserAddScreen();
        userMod = new UserModifyScreen(new User("", "", false));
        docAdd = new DocAddScreen("Book");
        docMod = new DocModifyScreen(new Document("","",1,1,false));
        changeProf = new ProfChangeScreen(new User("", "", false));

        login.setVisible(true);
        register.setVisible(false);
        cabinet.setVisible(false);
        userView.setVisible(false);
        userAdd.setVisible(false);
        userMod.setVisible(false);
        docAdd.setVisible(false);
        docMod.setVisible(false);
	}

	static User findUser (String username) {
        User user = null;
        for (int i = 0; i < users.size(); ++i)
            if (users.get(i).getUsername().equals(username)) user = users.get(i);
        return user;
    }

    static Document findDoc (String doc_title) {
        Document doc = null;
        for (int i = 0; i < documents.size(); ++i)
            if (documents.get(i).getTitle().equals(doc_title)) doc = documents.get(i);
        return doc;
    }

}
