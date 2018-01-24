import java.util.ArrayList;

public class JournalArticle extends Document {
    String journal;
    String issue;
    String publisher;
    List <String> issue_editors;
    String publication_date;

    int checkOut(Document doc){
        //2 weeks
    }
    
    public String getTitle(){
        return "";
    }

    public List<String> getAuthors(){
        return null;
    }

    public String getJournal() {
        return "";
    }

    public Integer getIssue() {
        return 0;
    }

    public List<String> getEditors() {
        return 0;
    }
    
    public String getDate() {
        return "";
    }
    
    public String getPublisher() {
        return "";
    }
}
