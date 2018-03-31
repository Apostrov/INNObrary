public class Notification {
    private int type;
    private Document document;

    public Notification(int type){
        this.type = type;
    }

    public Notification(int type, Document document){
        this(type);
        this.document = document;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
