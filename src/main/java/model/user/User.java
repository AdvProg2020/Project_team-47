package model.user;

import controller.Controller;
import database.Database;
import database.UserData;
import model.bank.Token;
import model.discount.DiscountCode;
import model.ecxeption.user.UserNotExistException;
import model.ecxeption.user.WrongPasswordException;
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
    private static final HashSet<User> verificationList;
    static ArrayList<User> allUsers;
    private static TreeSet<String> usedUsernames;
    private static int managersNumber;

    static {
        allUsers = new ArrayList<>();
        usedUsernames = new TreeSet<>();
        verificationList = new HashSet<>();
    }

    private byte[] avatar;
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
    private Token token;

    public User() {
        this.offFilters = new ArrayList<>();
        this.productFilters = new ArrayList<>();
    }

    public User(HashMap<String, String> userInfo, byte[] avatar) {
        this.offFilters = new ArrayList<>();
        this.productFilters = new ArrayList<>();
        this.username = userInfo.get("username");
        this.password = userInfo.get("password");
        this.phoneNumber = userInfo.get("phone-number");
        this.email = userInfo.get("email");
        this.firstName = userInfo.get("first-name");
        this.lastName = userInfo.get("last-name");
        this.type = userInfo.get("type");
        this.avatar = avatar;
        usedUsernames.add(this.username);
        Database.updateUsedUsernames(usedUsernames);
    }

    public static User getUserInVerificationList(String username) throws UserNotExistException {
        for (User user : verificationList) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        throw new UserNotExistException();
    }

    public static void isThereCustomersWithUsername(ArrayList<String> userNames) throws UserNotExistException {
        for (String username : userNames) {
            try {
                User user = getUserByUsername(username);
                if (!(user instanceof Customer)) {
                    throw new UserNotExistException();
                }
            } catch (UserNotExistException e) {
                throw new UserNotExistException("There isn't any customer with this username: " + username);
            }
        }
    }

    public static boolean isThereSeller(Seller seller) {
        return allUsers.contains(seller);
    }

    public static boolean isThereSeller(String username) {
        try {
            return getUserByUsername(username) instanceof Seller;
        } catch (UserNotExistException e) {
            return false;
        }
    }

    public static void setManagersNumber(int managersNumber) {
        User.managersNumber = managersNumber;
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

    public static boolean isThereManager() {
        return managersNumber > 0;
    }

    public static User getUserByUsername(String username) throws UserNotExistException {
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UserNotExistException();
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
        if (usedUsernames == null)
            return;
        User.usedUsernames = usedUsernames;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
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

    public void checkPassword(String password) throws WrongPasswordException {
        if (!this.password.equals(password)) {
            throw new WrongPasswordException();
        }
    }

    public abstract void deleteUser();

    public abstract UserInfo userInfoForSending();

    public void userInfoSetter(UserInfo userInfo) {
        userInfo.setFile(this.avatar);
        userInfo.setEmail(this.getEmail());
        userInfo.setFirstName(this.getFirstName());
        userInfo.setLastName(this.getLastName());
        userInfo.setPhoneNumber(this.getPhoneNumber());
        userInfo.setUsername(this.getUsername());
    }

    void addUser() {
        allUsers.add(this);
        if (this instanceof Manager)
            User.managerAdded();
        this.updateDatabase().update();
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

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public abstract UserData updateDatabase();

    public void updateDatabase(UserData userData) {
        userData.setFile(this.avatar);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return username.equalsIgnoreCase(user.username);
    }

    @Override
    public int hashCode() {
        return username.toLowerCase().hashCode();
    }

    public void removeNotVerifiedFromDatabase() {
        Database.removeNotVerifiedUser(this.username);
    }

    public double getAllowedMoney() {
        if (this instanceof Seller) {
            Seller seller = (Seller) this;
            return seller.getMoney() - Controller.getLeastWalletMoney();
        }
        if (this instanceof Customer) {
            Customer customer = (Customer) this;
            return customer.getMoney() - Controller.getLeastWalletMoney();
        }
        //todo amir
        return 10000;
    }
}
