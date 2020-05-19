package model.user;

import controller.Controller;
import database.Database;
import database.UserData;
import model.discount.DiscountCode;
import model.log.BuyLog;
import model.others.Email;
import model.others.Filter;
import model.others.Product;
import model.others.Sort;
import model.send.receive.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

abstract public class User {
    static ArrayList<User> allUsers;
    private static TreeSet<String> usedUsernames;
    private static int managersNumber;
    private static HashSet<User> verificationList;

    static {
        allUsers = new ArrayList<>();
        usedUsernames = new TreeSet<>();
        verificationList = new HashSet<>();
    }

    private String sendCode;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;
    private ArrayList<Filter> offFilters;
    private ArrayList<Filter> productFilters;


    private String sortField;
    private String sortDirection;
    private Product productPage;
    private BuyLog purchaseLog;
    private DiscountCode purchaseCode;

    public User() {
        this.offFilters = new ArrayList<>();
        this.productFilters = new ArrayList<>();
    }


    public User(HashMap<String, String> userInfo) {
        this.offFilters = new ArrayList<>();
        this.productFilters = new ArrayList<>();
        this.username = userInfo.get("username");
        this.password = userInfo.get("password");
        this.phoneNumber = userInfo.get("phone-number");
        this.email = userInfo.get("email");
        this.firstName = userInfo.get("first-name");
        this.lastName = userInfo.get("last-name");
        this.type = userInfo.get("type");
        usedUsernames.add(this.username);
        Database.updateUsedUsernames(usedUsernames);
    }

    public static User getUserInVerificationList(String username) {
        for (User user : verificationList) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isThereCustomersWithUsername(ArrayList<String> userNames) {
        for (String username : userNames) {
            User user = getUserByUsername(username);
            if (!(user instanceof Customer)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isThereSeller(Seller seller) {
        return allUsers.contains(seller);
    }

    public static boolean isThereSeller(String username) {
        return getUserByUsername(username) instanceof Seller;
    }

    public static boolean doesUsernameUsed(String username) {
        return usedUsernames.contains(username);
    }

    public static boolean isThereUserWithUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereUserWithEmail(String email) {
        for (User user : allUsers) {
            if (user.email.equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereUserWithPhone(String phoneNumber) {
        for (User user : allUsers) {
            if (user.phoneNumber.equalsIgnoreCase(phoneNumber)) {
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
        return Pattern.matches("0?9\\d{9}", phoneNumber);
    }

    public static boolean isEmailValid(String email) {
        return Pattern.matches("[a-zA-Z0-9._]+@[a-zA-Z0-9]+.[a-zA-Z]+", email);
    }

    public static boolean checkPasswordIsCorrect(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null)
            return false;
        return user.password.equals(password);
    }

    public static boolean isThereManager() {
        return managersNumber > 0;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private static void copyUserInfo(User destinationUser, User sourceUser) {
        //this function copy some field from source user to destination user
        //it uses when we want change role then we can create new user and copy last user info into new user
        destinationUser.email = sourceUser.email;
        destinationUser.firstName = sourceUser.firstName;
        destinationUser.lastName = sourceUser.lastName;
        destinationUser.password = sourceUser.password;
        destinationUser.phoneNumber = sourceUser.phoneNumber;
        destinationUser.username = sourceUser.username;
    }

    public static ArrayList<UserInfo> getAllUsers(String field, String direction) {
        ArrayList<UserInfo> usersInfo = new ArrayList<>();
        ArrayList<User> sortedUsers = Sort.sortUsers(field, direction, allUsers);
        for (User user : sortedUsers) {
            usersInfo.add(user.userInfoForSending());
        }
        return usersInfo;
    }

    static void managerAdded() {
        managersNumber++;
    }

    static void managerRemoved() {
        managersNumber--;
    }

    public static void setUsedUsernames(TreeSet<String> usedUsernames) {
        User.usedUsernames = usedUsernames;
    }

    public void resetProductFilters() {
        this.productFilters = new ArrayList<>();
    }

    public void resetOffFilter() {
        this.offFilters = new ArrayList<>();
    }

    public BuyLog getPurchaseLog() {
        return purchaseLog;
    }

    public void setPurchaseLog(BuyLog purchaseLog) {
        this.purchaseLog = purchaseLog;
    }

    public DiscountCode getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(DiscountCode purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public void removeProductFilter(Filter filter) {
        this.productFilters.remove(filter);
    }

    public void removeOffFilter(Filter filter) {
        this.offFilters.remove(filter);
    }

    public void addProductFilter(Filter filter) {
        this.productFilters.add(filter);
    }

    public void addOffFilter(Filter filter) {
        this.offFilters.add(filter);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Product getProductPage() {
        return productPage;
    }

    public void setProductPage(Product productPage) {
        this.productPage = productPage;
    }

    public ArrayList<Filter> getOffFilters() {
        return offFilters;
    }

    public ArrayList<Filter> getProductFilters() {
        return productFilters;
    }

    public void confirmEmail() {
        verificationList.remove(this);
        this.removeNotVerifiedFromDatabase();
        this.sendCode = "";
        this.addUser();
    }

    public boolean checkEmail(String email) {
        return this.email.equalsIgnoreCase(email);
    }

    public void emailVerification() {
        this.sendCode = Controller.idCreator();
        Email newEmail = new Email(this.email);
        newEmail.setMessage(this.sendCode);
        newEmail.sendVerificationEmail();
        verificationList.add(this);
        this.updateDatabase().addNorVerifiedUser();
    }

    public void sendForgotPasswordCode() {
        this.sendCode = Controller.idCreator();
        Email newEmail = new Email(this.email);
        newEmail.setMessage(this.sendCode);
        newEmail.sendForgotPasswordEmail();
        this.updateDatabase().update();
    }

    public void sendBuyingEmail(String logId) {
        Email newEmail = new Email(this.email);
        newEmail.setMessage(logId);
        newEmail.sendBuyingEmail();
    }

    public boolean sendCodeIsCorrect(String code) {
        String tempSendCode = this.sendCode;
        return tempSendCode.equalsIgnoreCase(code);
    }

    public boolean doesCodeSend() {
        return !this.sendCode.isEmpty();
    }

    public boolean checkPasswordIsCorrect(String password) {
        return this.password.equals(password);
    }

    public abstract void deleteUser();

    public abstract UserInfo userInfoForSending();

    public void userInfoSetter(UserInfo userInfo) {
        userInfo.setEmail(this.getEmail());
        userInfo.setFirstName(this.getFirstName());
        userInfo.setLastName(this.getLastName());
        userInfo.setPhoneNumber(this.getPhoneNumber());
        userInfo.setUsername(this.getUsername());
    }

    public void changeRole(String newRole) {

        switch (newRole) {
            case "manager":
                if (this instanceof Manager)
                    return;
                this.changeUserToManager();
                break;
            case "customer":
                if (this instanceof Customer)
                    return;
                this.changeUserToCustomer();
                break;
            case "seller":
                if (this instanceof Seller)
                    return;
                this.changeUserToSeller();
                break;
        }
        this.deleteUser();
    }

    void addUser() {
        allUsers.add(this);
        if (this instanceof Manager)
            User.managerAdded();
        this.updateDatabase().update();
    }

    private void changeUserToManager() {
        Manager manager = new Manager();
        copyUserInfo(manager, this);
        manager.setType("manager");
        manager.addUser();
    }

    private void changeUserToSeller() {
        Seller seller = new Seller();
        copyUserInfo(seller, this);
        seller.setType("seller");
        seller.addUser();
    }

    private void changeUserToCustomer() {
        Customer customer = new Customer();
        copyUserInfo(customer, this);
        customer.setType("customer");
        customer.addUser();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public abstract UserData updateDatabase();

    public void updateDatabase(UserData userData) {
        userData.setUsername(this.username);
        userData.setPassword(this.password);
        userData.setFirstName(this.firstName);
        userData.setLastName(this.lastName);
        userData.setEmail(this.email);
        userData.setPhoneNumber(this.phoneNumber);
        userData.setSendCode(this.sendCode);
    }

    public void addToVerificationListFromDatabase() {
        verificationList.add(this);
    }

    public void addToUsersFromDatabase() {
        User.allUsers.add(this);
        if (this instanceof Manager)
            User.managerAdded();
    }

    public void removeFromDatabase() {
        Database.removeUser(this.username);
    }

    public void removeNotVerifiedFromDatabase() {
        Database.removeNotVerifiedUser(this.username);
    }
}
