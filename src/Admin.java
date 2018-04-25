package main.java;

class Admin extends User {

    Admin(String username, String password) {
        super(username, password, false);
        setPriority(8);
    }

    Admin (User userToCopy, int priority) {
        super(userToCopy);
        setPriority(priority);
    }

}
