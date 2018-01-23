public class Main {
    // i think we don't need maxCardID, because db can say size
    public static int maxCardID = 0; // For new users (when person creates new user, it will increase by 1)
    // java already have class Date, maybe we use it
    public static int day = 0; // For booking system, fine overdue and so on

    private static User user; // Singleton of user

    /** Main method of INNObrary */
    public static void main(String[] args) {
        loadGlobalData();

        // Somewhere here:
        // Listen to button clicks
        //
        // If 'login' button is pressed then:
        //   1) Send data to 'login' method and save the returned value somewhere
        //   2) Open 'Booking system' page
        //
        // If 'register' button is pressed then:
        //   1) Send data to 'register' method and save the returned value somewhere
        //   2) Open 'Booking system' page
        //
        // If 'book a document' button is pressed then:
        //   1) Send data to 'book' method add new booking based on selected document to the users current bookings
        //   2) ...
        //
        // If 'logout' button is pressed
        //   1) Save all data (load all important data to database)
        //   2) ...
        //
        // If 'exit' button is pressed
        //   1) Save all data (load all important data to database)
        //   2) ...

        String username = getUsername();  // TODO: get username from input
        user = new User(login(username));

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

    /** Toggles to the next day */
    private static void nextDay () {
        day++;
    }

    /** Load some global data from database. */
    private static void loadGlobalData() {
        maxCardID = loadFromDB("key = maxCardID"); // TODO: load saved value from data base
    }

}
