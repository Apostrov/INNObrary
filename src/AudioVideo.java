import java.util.ArrayList;

public class AudioVideo extends Document {

    /** Initialisation of the audio/video material.
     *  @param title, authors, price, copies. */
    AudioVideo(String title, ArrayList<String> authors, int price, int copies, boolean isReference){
        super(title, authors, price, copies, isReference);
    }

    /** Returns number of days you can check out the audio/video material.*/
    void checkOut(AudioVideo audiovideo){

    }

    /** Getter for title of the audio/video material.*/
    public String getTitle(){
        return title;
    }

    /** Getter for authors of the audio/video material.*/
    public ArrayList<String> getAuthors(){
        return authors;
    }

}
