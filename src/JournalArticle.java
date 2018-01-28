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
        return title;
    }

    public List<String> getAuthors(){
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public Integer getIssue() {
        return issue;
    }

    public List<String> getEditors() {
        return issue_editors;
    }
    
    public String getDate() {
        return pub_date;
    }
    
    public String getPublisher() {
        return publisher;
    }
}
