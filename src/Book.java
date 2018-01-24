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
        return "";
    }

    public List<String> getAuthors(){
        return null;
    }

    public String getPublisher() {
        return "";
    }

    public Integer getEdition() {
        return 0;
    }

    public Integer getYear() {
        return 0;
    }
}
