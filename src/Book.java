import java.util.ArrayList;

public class Book extends Document {

    int edition; // edition of the book
    int edition_year; // year of release of the book
    String publisher; // publisher of the book
    private boolean isBS; // is the book best seller or not

    /** Initialisation of the book.
     *  @param title, authors, edition, edition_year, publisher, price, copies. */
    Book(String title, ArrayList<String> authors, int edition, int edition_year, String publisher, int price, int copies, boolean isBS, boolean isReference){
        super(title, authors, price, copies, isReference);
        this.edition = edition;
        this.edition_year = edition_year;
        this.publisher = publisher;
        this.isBS = isBS;
    }

    /** Returns number of days you can check out the book.*/
    void checkOut(Book book){

    }

    /** Getter for authors of the book.*/
    public ArrayList<String> getAuthors(){
        return authors;
    }

    /** Getter for publisher of the book.*/
    public String getPublisher() {
        return publisher;
    }

    /** Getter for edition of the book.*/
    public String getEdition() {
        return String.valueOf(edition);
    }

    /** Getter for edition year of the book.*/
    public String getYear() {
        return String.valueOf(edition_year);
    }

    public boolean isBS() {
        return isBS;
    }

    public void setBS (boolean BS) {
        isBS = BS;
    }
}
