import java.util.ArrayList;

public class User {

    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String address;
    private String phone;
    private boolean isFaculty;

    private ArrayList<Booking> bookings; // Current documents on hands
    private Object user_id;

    User (String username, String password, boolean isFaculty) {
        this.username = username;
        this.password = password;
        bookings = new ArrayList<>();
        this.isFaculty = isFaculty;
    }

    User (String username, String password, boolean isFaculty, String firstName, String secondName, String address, String phone) {
        this.username = username;
        this.password = password;
        this.isFaculty = isFaculty;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.phone = phone;
        bookings = new ArrayList<>();
    }

    // constructor for getting from DataBase
    User (Object user_id,String username, String password, boolean isFaculty, String firstName, String secondName, String address, String phone) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.isFaculty = isFaculty;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.phone = phone;
        bookings = new ArrayList<>();
    }

    /** Addition of new booking
     *  @param booking user's offer */
    void addBooking (Booking booking) { bookings.add(booking); }

    /** Returns all current bookings */
    ArrayList<Booking> getBookings () {
        return bookings;
    }

    void copyData (User user){
        setBookings(user.getBookings());
        setFaculty(user.isFaculty());
        setFirstName(user.getFirstName());
        setSecondName(user.getSecondName());
        setAddress(user.getAddress());
        setPhone(user.getPhone());
    }

    /** Returns all current bookings */
    void setBookings (ArrayList<Booking> bookings) {
        this.bookings = new ArrayList<>();
        this.bookings.addAll(bookings);
    }

    /** Returns booking from current bookings with id 'bookingID'
     *  @param bookingID id of booking that is need to return */
    Booking getBooking (int bookingID) {
        if (bookingID - 1 >= bookings.size()) {
            return null;
        } else {
            return bookings.get(bookingID - 1);
        }
    }

    /** Deletion of user's booking with id 'bookingID'
     *  @param bookingID id of booking that is need to delete */
    public void removeBooking (int bookingID) {
        if (bookingID - 1 < bookings.size()) {
            bookings.remove(bookingID - 1);
        }
    }

    public void sendRequest(Booking booking) {

    }

    public void removeRequest(Booking booking) {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) { this.password = password; }

    public boolean isFaculty() {
        return isFaculty;
    }

    public void setFaculty(boolean faculty) {
        isFaculty = faculty;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }
}
