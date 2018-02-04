import java.util.ArrayList;

public class JournalArticle extends Document {
    String journal; // title of the journal
    String issue; // title of the issue
    String publisher; // name of the publisher
    ArrayList <String> issue_editors; // names of the issue editors
    String pub_date; // publition date of the journal article
    
    
    /** Initialisation of the journal article.
     *  @param title, authors, journal, issue, publisher, issue_editors, pub_date. */
    JournalArticle(String title, ArrayList<String> authors, String journal, String issue, String publisher, ArrayList <String> issue_editors, String pub_date){
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.issue = issue;
        this.publisher = publisher;
        this.issue_editors = issue_editors;
        this.pub_date = pub_date;
    }
    
     /** Returns number of days you can check out the journal article.
     *  @param journal_article. */
    int checkOut(JournalArticle journal_article){
        //2 weeks
    }
    
     /** Getter for title of the journal article.
     *  @param title. */
    public String getTitle(){
        return title;
    }

     /** Getter for authors of the journal article.
     *  @param authors. */
    public ArrayList<String> getAuthors(){
        return authors;
    }

     /** Getter for journal of the journal article.
     *  @param journal. */
    public String getJournal() {
        return journal;
    }

     /** Getter for issue of the journal article.
     *  @param issue. */
    public Integer getIssue() {
        return issue;
    }

     /** Getter for editors of the issue of the journal article.
     *  @param issue_editors. */
    public ArrayList<String> getEditors() {
        return issue_editors;
    }
    
     /** Getter for publition date of the journal article.
     *  @param pub_date. */ 
    public String getDate() {
        return pub_date;
    }
    
     /** Getter for publisher of the journal article.
     *  @param publisher. */ 
    public String getPublisher() {
        return publisher;
    }
}
