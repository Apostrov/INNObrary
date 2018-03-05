public class Booking {

    private int duration;
    private Document doc;
    private boolean reqByLib;
    private boolean reqByUser;

    /** Represents a single offer of any document
     *  @param doc document to be booked
     *  @param duration certain time for what document will stay booked*/
    public Booking (Document doc, int duration) {
        this.doc = doc;
        this.duration = duration;
        reqByLib = false;
        reqByUser = false;
    }

    public boolean hasRequestedByLib(){
        return reqByLib;
    }

    public boolean hasRequestedByUser(){
        return reqByUser;
    }

    public void libRequest(){
        reqByLib = true;
    }

    public void userRequest(){
        reqByUser = true;
    }

    public int getTimeLeft() {
        return duration;
    }

    public Document getDoc () {
        return doc;
    }

}
