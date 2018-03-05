public class Document {

    private String title; // title of the document
    private String authors; // authors of the document
    private int price; // price of some document
    private int copies; // number of copies of some document
    private Object document_id; // id of the document
    String [] keywords; // keywords of the document
    private boolean isReference;
    private String description;
    private int year;
    private String publisher;
    private int edition;
    private boolean isBestSeller;
    // TODO: position of document in library


    Document (String title, String authors, int price, int copies, boolean isReference) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.copies = copies;
        this.isReference = isReference;
    }

    // constructor for getting from DataBase
    Document (Object document_id, String title, String authors, int price,
              int copies, boolean isReference, String description, String publisher,
              int edition, int year, boolean isBestSeller){
        this(title, authors, price, copies, isReference);
        this.document_id = document_id;
        this.description = description;
        this.publisher = publisher;
        this.edition = edition;
        this.year = year;
        this.isBestSeller = isBestSeller;
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

    public void setTitle (String title) {
        this.title = title;
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

    /** Getter for authors of the document.*/
    public String getAuthors(){
        return authors;
    }

    public void setAuthors(String authors){
        this.authors = authors;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) { this.year = year; }

    public void setDocument_id(Object document_id) {
        this.document_id = document_id;
    }

    public Object getDocument_id(){
        return document_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) { this.edition = edition; }

    public boolean isBestSeller() { return isBestSeller; }

    public void setBestSeller(boolean isBestSeller) { this.isBestSeller = isBestSeller; }

}
