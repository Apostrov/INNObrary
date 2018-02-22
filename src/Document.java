import java.util.ArrayList;

public class Document {

    String title; // title of the document
    ArrayList<String> authors; // authors of the document
    private int price; // price of some document
    private int copies; // number of copies of some document
    int document_id; // id of the document
    String [] keywords; // keywords of the document
    private boolean isReference;
    private String year;
    private String edition;
    private String description;
    private String publisher;
    // TODO: position of document in library

    Document (String title, ArrayList<String> authors, int price, int copies, boolean isReference) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.copies = copies;
        this.isReference = isReference;
    }

    /** To delete the document*/
    public void delete(){

    }

    /** To add the document*/
    public void add(){

    }

    /** To modify the document*/
    public void modify(Document new_doc){

    }

    /** To return the document*/
    public void returnDoc(Document doc){

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

    public String getTitle () {
        return title;
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

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean reference) {
        isReference = reference;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

}
