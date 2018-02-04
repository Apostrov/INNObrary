import java.util.ArrayList;

public class Book extends Document {
    int edition; // edition of the book
    int edition_year; // year of release of the book
    String publisher; // publisher of the book
    
    /** Initialisation of the book.
     *  @param title, authors, edition, edition_year, publisher. */
    Book(String title, ArrayList<String> authors, int edition, int edition_year, String publisher){
        this.title = title;
        this.authors = authors;
        this.edition = edition;
        this.edition_year = edition_year;
        this.publisher = publisher;
    }
    
    /** Returns number of days you can check out the book.*/
    void checkOut(Book book){
        
    }
    
     /** Getter for title of the book.*/
    public String getTitle(){
        return title;
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
    public Integer getEdition() {
        return edition;
    }

     /** Getter for edition year of the book.*/
    public Integer getYear() {
        return edition_year;
    }
}
