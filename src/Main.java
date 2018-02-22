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
    static DocumentAddScreen docAdd;

    /** Main method of INNObrary */
    public static void main(String[] args) {
        // For test cases
        users = new ArrayList<>();
        documents = new ArrayList<>();
        String[] books = {
                "War and peace", "1984", "Three comrades", "The Catcher in the Rye",
                "Pride and Prejudice", "To Kill a Mockingbird", "The Great Gatsby",
                "Catch-22", "Animal Farm", "Great Expectations", "Jane Eyre",
                "Wuthering Heights", "Lord of the Flies", "Little Women", "Anna Karenina",
                "Brave New World", "The Grapes of Wrath", "Gone with the Wind", "Charlotte's Web"
        };

        for (int i = 0; i < 3; ++i) {
            Patron nextPatron = new Patron("patron_" + (i + 1), "123", false, "1" + i, "1" + i, "1" + i, "1" + i);
            users.add(nextPatron);
        }
        Patron facultyPatron = new Patron("faculty_1", "123", true, "1f", "1f", "1f", "1f");
        users.add(facultyPatron);
        users.add(new Librarian("librarian_1", "123"));
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Egor");
        for (int i = 0; i < 19; ++i){
            documents.add(new Book(books[i], authors, 1, 2007, "Egor pub. house", 100, 1, false, false));
        }
        documents.get(0).setCopies(0);
        documents.get(1).setReference(true);
        if (documents.get(2) instanceof Book) ((Book) documents.get(2)).setBS(true);
        documents.get(3).setCopies(2);
        documents.get(4).setCopies(3);
        documents.get(5).setCopies(4);

        // Load interface
        login = new LoginScreen();
        register = new RegistrationScreen();
        cabinet = new CabinetScreen(false);
        userView = new UserViewScreen(null);
        userAdd = new UserAddScreen();
        docAdd = new DocumentAddScreen("Book");

        login.setVisible(true);
        register.setVisible(false);
        cabinet.setVisible(false);
        userView.setVisible(false);
        userAdd.setVisible(false);
        docAdd.setVisible(false);
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
