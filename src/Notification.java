package main.java;

import java.sql.Date;

class Notification {

    private int type;
    private String doc;
    private String user;
    private Date date;
    private int fine;

    Notification (int type, String doc, String user, Date date, int fine) {
        this.type = type;
        this.doc = doc;
        this.user = user;
        this.date = date;
        this.fine = fine;
    }

    int getType() {
        return type;
    }

    String getDoc() {
        return doc;
    }

    Date getDate() {
        return date;
    }

    int getFine() {
        return fine;
    }

    String getUser() {
        return user;
    }
}
