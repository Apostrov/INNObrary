public class Booking {

    int delay;
    Document doc;

    /** Represents a single offer of any document
     *  @param doc document to be booked
     *  @param delay certain time for what document will stay booked*/
    public Booking (Document doc, int delay) {
        this.doc = doc;
        this.delay = delay;
    }

    public int getDaysLeft () {
        return delay;
    }

    public Document getBookedDocument () {
        return doc;
    }

}
