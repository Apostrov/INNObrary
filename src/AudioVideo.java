import java.util.ArrayList;

public class AudioVideo extends Document {
    
     /** Initialisation of the audio/video material.
     *  @param title, authors. */
    AudioVideo(String title, ArrayList<String> authors){
        this.title = title;
        this.authors = authors;
    }
    
     /** Returns number of days you can check out the audio/video material.*/
    void checkOut(AudioVideo audiovideo){
        //2 weeks
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
