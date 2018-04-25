package main.java;

class Librarian extends User {

    Librarian(String username, String password, int priority) {
        super(username, password, false);
        setPriority(priority);
    }

    Librarian (User userToCopy, int priority) {
        super(userToCopy);
        setPriority(priority);
    }

}
