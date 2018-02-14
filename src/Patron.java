public class Patron extends User {

    private String address;
    private String phone;

    Patron (String username, String password, boolean isFaculty) {
        super(username, password, isFaculty);
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
