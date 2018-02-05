import java.util.ArrayList;

public class User {

    private String name;
    private String address;
    private String phone;
    private boolean isFaculty;

    private ArrayList<Booking> bookingHistory; // History of all bookings
    private ArrayList<Booking> currentBookings; // Current documents on hands

    User (String username) {
        bookingHistory = new ArrayList<>();
        currentBookings = new ArrayList<>();
    }

    /** Addition of new booking
     *  @param booking user's offer */
    void addBooking (Booking booking) { currentBookings.add(booking); }

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

}
