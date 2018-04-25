package main.java;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    static Date date;

    static User activeUser = null;
    static ArrayList<User> users;
    static ArrayList<Document> documents;
    static ArrayList<String> logs;
    static ArrayList<Queue<User>> priorityQueues;

    static LoginScreen login;
    static CabinetScreen cabinet;
    static RegistrationScreen register;
    static UserViewScreen userView;
    static UserAddScreen userAdd;
    static UserModifyScreen userMod;
    static DocAddScreen docAdd;
    static DocModifyScreen docMod;
    static ProfChangeScreen changeProf;
    static DebtorViewScreen debtors;
    static RequestsScreen requests;
    static DateModifyScreen dateMod;
    static LogScreen logScreen;

    /** Main method of INNObrary */
    public static void main(String[] args) {
        // For debug
        date = DataBase.getDate();

        // Run-time storage
        users = DataBase.getAllUser();
        documents = DataBase.getAllDoc();
        logs = DataBase.getAllLogs();

        // Priority queue
        priorityQueues = new ArrayList<>();
        for (int i = 0; i < documents.size(); ++i) {
            ArrayList<User> reqUsers = new ArrayList<>();
            for (int j = 0; j < users.size(); ++j) {
                if (users.get(j).findBooking(documents.get(i).getTitle()) != null &&
                        !users.get(j).findBooking(documents.get(i).getTitle()).hasReceived()) {
                    reqUsers.add(users.get(j));
                }
            }
            priorityQueues.add(new PriorityQueue<>(priorityComparator));
            for (int j = 0; j < reqUsers.size(); ++j)
                priorityQueues.get(priorityQueues.size() - 1).add(reqUsers.get(j));
        }

        // Load interface
        login = new LoginScreen(); login.setLocationRelativeTo(null);
        register = new RegistrationScreen(); register.setLocationRelativeTo(null);
        cabinet = new CabinetScreen(false); cabinet.setLocationRelativeTo(null);
        userView = new UserViewScreen(null); userView.setLocationRelativeTo(null);
        userAdd = new UserAddScreen(); userAdd.setLocationRelativeTo(null);
        userMod = new UserModifyScreen(new User("", "", false), null); userMod.setLocationRelativeTo(null);
        docAdd = new DocAddScreen("Book"); docAdd.setLocationRelativeTo(null);
        docMod = new DocModifyScreen(new Document("","", new ArrayList<>(),1,1,false)); docMod.setLocationRelativeTo(null);
        changeProf = new ProfChangeScreen(new User("", "", false)); changeProf.setLocationRelativeTo(null);
        debtors = new DebtorViewScreen(); debtors.setLocationRelativeTo(null);
        requests = new RequestsScreen(); requests.setLocationRelativeTo(null);
        dateMod = new DateModifyScreen(); dateMod.setLocationRelativeTo(null);
        logScreen = new LogScreen(); logScreen.setLocationRelativeTo(null);

        // Show only the login screen first
        login.setVisible(true);
        register.setVisible(false);
        cabinet.setVisible(false);
        userView.setVisible(false);
        userAdd.setVisible(false);
        userMod.setVisible(false);
        docAdd.setVisible(false);
        docMod.setVisible(false);
        debtors.setVisible(false);
        requests.setVisible(false);
        dateMod.setVisible(false);
        logScreen.setVisible(false);
    }

	/** Returns user instance if the user with given username exists otherwise null */
	static User findUser (String username) {
        User user = null;
        for (int i = 0; i < users.size(); ++i)
            if (users.get(i).getUsername().equals(username)) user = users.get(i);
        return user;
    }

    /** Returns document instance if the document with given title exists otherwise null */
    static Document findDoc (String doc_title) {
        Document doc = null;
        for (int i = 0; i < documents.size(); ++i)
            if (documents.get(i).getTitle().equals(doc_title)) doc = documents.get(i);
        return doc;
    }

    /** Returns the number of copies of the document in the system and on hands. */
    static int actualCopies (String doc_title) {
        Document doc = findDoc(doc_title);
        if (doc != null) {
            int num = 0;
            for (int i = 0; i < users.size(); ++i) {
                if (users.get(i).findBooking(doc_title) != null) {
                    num++;
                }
            }
            return num + findDoc(doc_title).getCopies();
        }
        return 0;
    }

    // Is used for comparison of the user regarding their priorities
    static Comparator<User> priorityComparator = (u1, u2) -> (int) (u1.getPriority() - u2.getPriority());

}
