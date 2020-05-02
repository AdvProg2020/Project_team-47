package model.user;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

abstract public class User {
    protected static ArrayList<User> allUsers;
    private static int managersNumber;

    static {
        allUsers = new ArrayList<>();
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;

    public User() {
        this.addUser();
    }

    public User(HashMap<String, String> userInfo) {
        this.addUser();
        this.username = userInfo.get("username");
        this.password = userInfo.get("password");
        this.phoneNumber = userInfo.get("phone-number");
        this.email = userInfo.get("email");
        this.firstName = userInfo.get("first-name");
        this.lastName = userInfo.get("last-name");
        this.type = userInfo.get("type");
    }

    public static boolean isThereUsersWithUsername(ArrayList<String> userNames) {
        for (String username : userNames) {
            if (!isThereUserWithUsername(username)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isThereUserWithUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereUserWithEmail(String email) {
        for (User user : allUsers) {
            if (user.email.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereUserWithPhone(String phoneNumber) {
        for (User user : allUsers) {
            if (user.phoneNumber.equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsernameValid(String username) {
        if (username.length() < 5)
            return false;
        return Pattern.matches("[a-zA-Z0-9]+", username);
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() < 8)
            return false;
        return Pattern.matches("[a-zA-Z0-9!@#$%^&*-_=]+", password);
    }

    public static boolean isPhoneValid(String phoneNumber) {
        return Pattern.matches("09\\d{9}", phoneNumber);
    }

    public static boolean isEmailValid(String email) {
        return Pattern.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z]+", email);
    }

    public static boolean checkPasswordIsCorrect(String username, String password) {
        User user = getUserByUsername(username);
        assert user != null;
        return user.password.equals(password);
    }

    public static boolean isThereManager() {
        return managersNumber > 0;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void deleteUser(String username) {
        User user = getUserByUsername(username);
        deleteUser(user);
    }

    public static void deleteUser(User user) {
        allUsers.remove(user);
        if (user instanceof Manager)
            User.managersNumber--;
    }

    private static void copyUserInfo(User destinationUser, User sourceUser) {
        destinationUser.setEmail(sourceUser.getEmail());
        destinationUser.setFirstName(sourceUser.getFirstName());
        destinationUser.setLastName(sourceUser.getLastName());
        destinationUser.setPassword(sourceUser.getPassword());
        destinationUser.setPhoneNumber(sourceUser.getPhoneNumber());
        destinationUser.setUsername(sourceUser.getUsername());
    }

    public static String getAllUsers(String field, String direction) {
        ArrayList<String> usersInfo = new ArrayList<>();
        for (User user : allUsers) {
            usersInfo.add(user.userInfoForSending());
        }
        Gson user = new Gson();
        return user.toJson(usersInfo);
    }

    public static void managerAdded() {
        managersNumber++;
    }

    abstract public String userInfoForSending();

    public void changeRole(String newRole) {
        switch (newRole) {
            case "manager":
                this.changeUserToManager();
                break;
            case "customer":
                this.changeUserToCustomer();
                break;
            case "seller":
                this.changeUserToSeller();
                break;
        }
    }

    void addUser() {
        allUsers.add(this);
        if (this instanceof Manager)
            User.managersNumber++;
    }

    private void changeUserToManager() {
        Manager manager = new Manager();
        copyUserInfo(manager, this);
        manager.setType("manager");
        deleteUser(this);
        manager.addUser();
    }

    private void changeUserToSeller() {
        Seller seller = new Seller();
        copyUserInfo(seller, this);
        seller.setType("seller");
        deleteUser(this);
        seller.addUser();
    }

    private void changeUserToCustomer() {
        Customer customer = new Customer();
        copyUserInfo(customer, this);
        customer.setType("customer");
        deleteUser(this);
        customer.addUser();
    }

    @Override
    public String toString() {
        return "User{}";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
