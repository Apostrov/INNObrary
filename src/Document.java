import java.util.ArrayList;

public class Document {

    private String title;
    private String [] keywords;
    private List<String> authors;
    private int price;
    private int copies;
    // TODO: position of document in library

    public void delete(){

    }

    public void add(){

    }

    public void modify(Document new_doc){

    }

    public Document[] searchFor_byAuthor(){
        Document[] result;
        return result;
    }

    public Document[] searchFor_byTitle(){
        Document[] result;
        return result;
    }

    public Document search(){

    }

    /** Returns number of days of overdue */
    public int checkOverdue(){
        return 0;
    }


    public int overdueFine(){
        return checkOverdue() * 100;
    }

    public void make_copy(){
        copies++;
    }
}
