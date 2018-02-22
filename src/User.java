import java.util.ArrayList;

public class User {

    private String username;
    private String firstName;
    private String secondName;
    private String password;
    String address;
    String phone;
    private boolean isFaculty;

    private ArrayList<Booking> currentBookings; // Current documents on hands

    User (String username, String password, boolean isFaculty) {
        this.username = username;
        this.password = password;
        currentBookings = new ArrayList<>();
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
        currentBookings = new ArrayList<>();
    }

    /** Addition of new booking
     *  @param booking user's offer */
    void addBooking (Booking booking) { currentBookings.add(booking); }

    /** Returns all current bookings */
    ArrayList<Booking> getBookings () {
        return currentBookings;
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
        currentBookings = new ArrayList<>();
        for (int i = 0; i < bookings.size(); ++i) {
            currentBookings.add(bookings.get(i));
        }
    }

    /** Returns booking from current bookings with id 'bookingID'
     *  @param bookingID id of booking that is need to return */
    Booking getBooking (int bookingID) {
        if (bookingID - 1 >= currentBookings.size()) {
            return null;
        } else {
            return currentBookings.get(bookingID - 1);
        }
    }

    /** Deletion of user's booking with id 'bookingID'
     *  @param bookingID id of booking that is need to delete */
    public void removeBooking (int bookingID) {
        if (bookingID - 1 < currentBookings.size()) {
            currentBookings.remove(bookingID - 1);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword () {
        return password;
    }

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
}
