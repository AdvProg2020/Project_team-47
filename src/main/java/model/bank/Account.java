package model.bank;

public class Account {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private double money;
    private final int id;

    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        money = 0;
        id = Bank.getInstance().getAccounts().size();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double money) {
        this.money = this.money + money;
    }
}
