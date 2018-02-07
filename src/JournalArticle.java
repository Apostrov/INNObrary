import java.util.ArrayList;

public class JournalArticle extends Document {

    /** Initialisation of the journal article.
     *  @param title, authors, journal, issue, publisher, issue_editors, pub_date, price, copies. */
    JournalArticle(String title, ArrayList<String> authors, int price, int copies, boolean isReference){
        super(title, authors, price, copies, isReference);
    }

    /** Returns number of days you can check out the journal article.*/
    void checkOut(JournalArticle journal_article){

    }

    /** Getter for authors of the journal article.*/
    public ArrayList<String> getAuthors(){
        return authors;
    }

}
