package main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class Document {

    private String title; // title of the document
    private String authors; // authors of the document
    private int price; // price of some document
    private int copies; // number of copies of some document
    private Object document_id; // id of the document
    private boolean isReference;
    private ArrayList<String> keywords;
    private int year;
    private String publisher;
    private int edition;
    private boolean isBestSeller;
    private boolean isOutstanding;

    Document (String title, String authors, ArrayList<String> keywords, int price, int copies, boolean isReference) {
        this.title = title;
        this.authors = authors;
        this.keywords = new ArrayList<>();
        this.keywords.addAll(keywords);
        this.price = price;
        this.copies = copies;
        this.isReference = isReference;
        isOutstanding = false;
    }

    // Constructor for the DataBase purposes
    Document (Object document_id, String title, String authors, int price,
              int copies, boolean isReference, ArrayList<String> keywords, String publisher,
              int edition, int year, boolean isBestSeller, boolean isOutstanding){
        this(title, authors, keywords, price, copies, isReference);
        this.document_id = document_id;
        this.keywords = keywords;
        this.publisher = publisher;
        this.edition = edition;
        this.year = year;
        this.isBestSeller = isBestSeller;
        this.isOutstanding = isOutstanding;
    }

    /** Returns the title of the document. */
    String getTitle () {
        return title;
    }

    /** Sets the title of the document. */
    void setTitle (String title) {
        this.title = title;
    }

    /** Returns whether the document is reference or not. */
    boolean isReference() {
        return isReference;
    }

    /** Sets the 'reference state' of the document. */
    void setReference(boolean reference) {
        isReference = reference;
    }

    /** Returns the copies of the document. */
    int getCopies() {
        return copies;
    }

    /** Sets the number of copies of the document. */
    void setCopies(int copies) {
        this.copies = copies;
    }

    /** Returns the price of the document. */
    int getPrice() {
        return price;
    }

    /** Getter for authors of the document.*/
    String getAuthors(){
        return authors;
    }

    /** Sets the authors of the document. */
    void setAuthors(String authors){
        this.authors = authors;
    }

    /** Sets the price of the document. */
    void setPrice(int price) {
        this.price = price;
    }

    /** Returns the keywords of the document. */
    ArrayList<String> getKeywords() {
        return keywords;
    }

    /** Sets new keywords. */
    void setKeywords(String keywords) {
        this.keywords.clear();
        this.keywords.addAll(Arrays.asList(keywords.split(", ")));
    }

    /** Returns the year of publishing. */
    int getYear() {
        return year;
    }

    /** Sets the year of publishing. */
    void setYear(int year) { this.year = year; }

    /** Sets the id of the document (used for the database) */
    void setDocument_id(Object document_id) {
        this.document_id = document_id;
    }

    /** Returns the id of the document (used for the database) */
    Object getDocument_id(){
        return document_id;
    }

    /** Returns the publisher of the document. */
    String getPublisher() {
        return publisher;
    }

    /** Sets the publisher of the document. */
    void setPublisher(String publisher) { this.publisher = publisher; }

    /** Returns the edition of the document. */
    int getEdition() {
        return edition;
    }

    /** Sets the edition of the document. */
    void setEdition(int edition) { this.edition = edition; }

    /** Returns whether the document is bestseller or not. */
    boolean isBestSeller() { return isBestSeller; }

    /** Sets the 'bestseller state' of the document. */
    void setBestSeller(boolean isBestSeller) { this.isBestSeller = isBestSeller; }

    /** Returns whether the document has outstanding request. */
    boolean isOutstanding() {
        return isOutstanding;
    }

    /** Places or removes the outstanding request for the document. */
    void setOutstanding(boolean outstanding) {
        isOutstanding = outstanding;
    }
}
