package main.java;

import java.util.ArrayList;

class AudioVideo extends Document {

    /** Initialisation of the audio/video material.
     *  @param title, authors, price, copies. */
    AudioVideo(String title, String authors, int price, int copies, boolean isReference){
        super(title, authors, price, copies, isReference);
    }

    /** Another constructor for database purposes. */
    AudioVideo (Object document_id, String title, String authors, int price,
                int copies, boolean isReference, ArrayList<String> keywords, String publisher,
                int edition, int year, boolean isBestSeller, boolean isOutstanding) {
        super(document_id, title, authors, price, copies, isReference, keywords, publisher, edition, year, isBestSeller, isOutstanding);
    }

}
