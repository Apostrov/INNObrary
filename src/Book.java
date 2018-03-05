public class Book extends Document {

    /** Initialisation of the book.
     *  @param title, authors, edition, edition_year, publisher, price, copies. */
    Book(String title, String authors, int edition, int edition_year, String publisher, int price, int copies, boolean isBestSeller, boolean isReference){
        super(title, authors, price, copies, isReference);
        setEdition(edition);
        setYear(edition_year);
        setBestSeller(isBestSeller);
        setPublisher(publisher);
    }

    Book (Object document_id, String title, String authors, int price,
          int copies, boolean isReference, String description, String publisher,
          int edition, int year, boolean isBestSeller) {
        super(document_id, title, authors, price, copies, isReference, description, publisher, edition, year, isBestSeller);
    }

    /** Returns number of days you can check out the book.*/
    void checkOut (Book book) {}

}
