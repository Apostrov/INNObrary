import java.util.ArrayList;

public class JournalArticle extends Document {
    String journal;
    String issue;
    String publisher;
    List <String> issue_editors;
    String pub_date;
    
    JournalArticle(String title, List<String> authors, String journal, String issue, String publisher, List <String> issue_editors, String pub_date){
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.issue = issue;
        this.publisher = publisher;
        this.issue_editors = issue_editors;
        this.pub_date = pub_date;
    }
    

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
