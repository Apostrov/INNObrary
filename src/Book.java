import java.util.List;

public class Book extends Document {
    int edition;
    int edition_year;
    String publisher;
    
    Book(String title, List<String> authors, int edition, int edition_year, String publisher){
        this.title = title;
        this.authors = authors;
        this.edition = edition;
        this.edition_year = edition_year;
        this.publisher = publisher;
    }
    

    void checkOut(Document doc){

    }

    public String getTitle(){
        return title;
    }

    public List<String> getAuthors(){
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getEdition() {
        return edition;
    }

    public Integer getYear() {
        return edition_year;
    }
}
