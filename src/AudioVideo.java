public class AudioVideo extends Document {
    
     /** Initialisation of the audio/video material.
     *  @param title, authors. */
    AudioVideo(String title, List<String> authors){
        this.title = title;
        this.authors = authors;
    }
    
     /** Returns number of days you can check out the audio/video material.
     *  @param book. */
    int checkOut(Document doc){
        //2 weeks
    }
    
     /** Getter for title of the audio/video material.
     *  @param title. */
    public String getTitle(){
        return title;
    }

     /** Getter for authors of the audio/video material.
     *  @param authors. */
    public List<String> getAuthors(){
        return authors;
    }
    
}
