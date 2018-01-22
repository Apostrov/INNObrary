import java.util.ArrayList;

public class User {
    // TODO: getter for all info like name, addres and other
    private String name;
    private String address;
    private String phone;
    private boolean isFaculty;

    private int cardID; // User's card ID
    private ArrayList<Booking> bookingHistory; // History of all bookings
    private ArrayList<Booking> currentBookings; // Current documents on hands

    /** Initialisation of user.
     *  @param cardID unique for different users value. */
    User (int cardID) {
        this.cardID = cardID;

        // TODO: load user's data depending on card ID
        name = loadFromDB("key = name");
        address = loadFromDB("key = address");
        phone = loadFromDB("key = phone");
        isFaculty = loadFromDB("key = is_faculty");

        bookingHistory = new ArrayList<>();
        loadFromDB("key = booking history"); // TODO: somehow need to load from database
        currentBookings = new ArrayList<>();
        loadFromDB("key = current bookings"); // TODO: somehow need to load from database
    }

    /** Addition of new booking
     *  @param booking user's offer */
    public void addBooking (Booking booking) { currentBookings.add(booking); }

    /** Deletion of user's booking with id 'bookingID'
     *  @param bookingID id of booking that is need to delete */
    public void removeBooking (int bookingID) {

    }

}
