import java.util.ArrayList;

public class Document {

    String title; // title of the document
    String [] keywords; // keywords of the document
    ArrayList<String> authors; // authors of the document
    private int price; // price of some document
    private int copies; // number of copies of some document
    // TODO: position of document in library

    /** To delete the document*/
    public void delete(){

    }

    /** To add the document*/
    public void add(){

    }

    /** To modify the document*/
    public void modify(Document new_doc){

    }

    /** Returns result of searching documents by author */
    public Document[] searchFor_byAuthor(){
        Document[] result;
        return null;
    }

    /** Returns result of searching documents by title */
    public Document[] searchFor_byTitle(){
        Document[] result;
        return null;
    }

    /** Returns number of days of overdue */
    public int checkOverdue(){
        return 0;
    }

    /** Returns the value of the fine */
    public int overdueFine(){
        return checkOverdue() * 100;
    }

    /** Makes one copy of the document */
    public void make_copy(){
        copies++;
    }
}
