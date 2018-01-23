public class Patron {
    private String name, password, address, phone;
    private boolean isFaculty;
    public Patron(String name, String password, String address, String phone, boolean isFaculty){
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.isFaculty = isFaculty;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getFaculty() {
        return isFaculty;
    }
}
