import java.util.ArrayList;

public class Document {

    String title;
    String [] keywords;
    List<String> authors;
    int price;
    int copies;
    // TODO: position of document in library

    void delete(){

    }

    void add(){

    }

    void modify(Document new_doc){

    }

    Document[] searchFor_byAuthor(){
        Document[] result;
        return result;
    }

    Document[] searchFor_byTitle(){
        Document[] result;
        return result;
    }

    Document search(){

    }

    /** Returns number of days of overdue */
    int checkOverdue(){
        return 0;
    }


    int overdueFine(){
        return checkOverdue() * 100;
    }

    void make_copy(){
        copies++;
    }
}
