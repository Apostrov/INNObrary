import java.util.ArrayList;

public class JournalArticle extends Document {
    String journal; // title of the journal
    String issue; // title of the issue
    String publisher; // name of the publisher
    ArrayList <String> issue_editors; // names of the issue editors
    String pub_date; // publition date of the journal article


    /** Initialisation of the journal article.
     *  @param title, authors, journal, issue, publisher, issue_editors, pub_date, price, copies. */
    JournalArticle(String title, ArrayList<String> authors, String journal, String issue, String publisher, ArrayList <String> issue_editors, String pub_date, int price, int copies){
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.issue = issue;
        this.publisher = publisher;
        this.issue_editors = issue_editors;
        this.pub_date = pub_date;
        this.price = price;
        this.copies = copies;
    }

    /** Returns number of days you can check out the journal article.*/
    void checkOut(JournalArticle journal_article){
        //2 weeks
    }

    /** Getter for title of the journal article.*/
    public String getTitle(){
        return title;
    }

    /** Getter for authors of the journal article.*/
    public ArrayList<String> getAuthors(){
        return authors;
    }

    /** Getter for journal of the journal article.*/
    public String getJournal() {
        return journal;
    }

    /** Getter for issue of the journal article.*/
    public String getIssue() {
        return issue;
    }

    /** Getter for editors of the issue of the journal article.*/
    public ArrayList<String> getEditors() {
        return issue_editors;
    }

    /** Getter for publition date of the journal article.*/
    public String getDate() {
        return pub_date;
    }

    /** Getter for publisher of the journal article.*/
    public String getPublisher() {
        return publisher;
    }
}
