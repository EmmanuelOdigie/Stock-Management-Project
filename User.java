package stockManagement;

public class User {
    private int id;
    private String name;
    private String email;
    private String pass;
    private int balance;

    public User(int id, String name, String email, String pass, int balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
