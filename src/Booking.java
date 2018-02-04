public class Booking {

    int finishDay;
    Document doc;

    /** Represents a single offer of any document
     *  @param doc document to be booked
     *  @param delay certain time for what document will stay booked*/
    public Booking (Document doc, int delay) {
        this.doc = doc;
    }

    public Document getBookedDocument () {
        return doc;
    }

}
