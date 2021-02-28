package bgu.spl.net.impl.BGRSServer;

public class Student {
    private String name;
    private String password;
    private boolean isAdmin;

    public Student(String _name, String _password, boolean admin){
        name=_name;
        password=_password;
        isAdmin=admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
