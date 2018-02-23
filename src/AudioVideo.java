import java.util.ArrayList;

public class AudioVideo extends Document {

    /** Initialisation of the audio/video material.
     *  @param title, authors, price, copies. */
    AudioVideo(String title, String authors, int price, int copies, boolean isReference){
        super(title, authors, price, copies, isReference);
    }

    /** Returns number of days you can check out the audio/video material.*/
    void checkOut(AudioVideo audiovideo){

    }

}
