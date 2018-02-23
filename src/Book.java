public class Book extends Document {

    private int edition; // edition of the book
    private int edition_year; // year of release of the book
    private String publisher; // publisher of the book
    private boolean isBS; // is the book best seller or not

    /** Initialisation of the book.
     *  @param title, authors, edition, edition_year, publisher, price, copies. */
    Book(String title, String authors, int edition, int edition_year, String publisher, int price, int copies, boolean isBS, boolean isReference){
        super(title, authors, price, copies, isReference);
        this.edition = edition;
        this.edition_year = edition_year;
        this.publisher = publisher;
        this.isBS = isBS;
    }

    /** Returns number of days you can check out the book.*/
    void checkOut (Book book) {}

    /** Getter for publisher of the book.*/
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /** Getter for edition of the book.*/
    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    /** Getter for edition year of the book.*/
    public int getYear() {
        return edition_year;
    }

    public void setYear(int year) {
        edition_year = year;
    }

    public boolean isBS() {
        return isBS;
    }

    public void setBS (boolean BS) {
        isBS = BS;
    }
}
