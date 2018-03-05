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
        login = new LoginScreen(); login.setLocationRelativeTo(null);
        register = new RegistrationScreen(); register.setLocationRelativeTo(null);
        cabinet = new CabinetScreen(false); cabinet.setLocationRelativeTo(null);
        userView = new UserViewScreen(null); userView.setLocationRelativeTo(null);
        userAdd = new UserAddScreen(); userAdd.setLocationRelativeTo(null);
        userMod = new UserModifyScreen(new User("", "", false)); userMod.setLocationRelativeTo(null);
        docAdd = new DocAddScreen("Book"); docAdd.setLocationRelativeTo(null);
        docMod = new DocModifyScreen(new Document("","",1,1,false)); docMod.setLocationRelativeTo(null);
        changeProf = new ProfChangeScreen(new User("", "", false)); changeProf.setLocationRelativeTo(null);

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
