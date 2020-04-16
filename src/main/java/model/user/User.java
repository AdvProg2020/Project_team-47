package model.user;

import java.util.ArrayList;

abstract public class User {
    protected static ArrayList<User> allUsers;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;

    public User() {
    }

    abstract public String userInfoForSending();

    public static boolean isThereUsersWithUsername(ArrayList<String> username){return true;}

    public static boolean isThereUserWithUsername(String username){return true;}
    public static boolean isThereUserWithEmail(String email){return true;}
    public static boolean isThereUserWithPhone(String phoneNumber){return true;}

    public static boolean isUsernameValid(String username){return true;}
    public static boolean isPasswordValid(String password){return true;}
    public static boolean isPhoneValid(String phoneNumber){return true;}
    public static boolean isEmailValid(String email){return true;}

    public static boolean checkPasswordIsCorrect(String username,String password){return true;}

    public static boolean isThereManager(){return true;}

    public static User getUserByUsername(String username){return null;}


    public static ArrayList<User> sortUsersBy(String field,String direction){return null;}


    public static String getAllUsers(String field,String direction){return null;}

    public static void deleteUser(String username){}

    public void changeRole(String role){}







    @Override
    public String toString() {
        return "User{}";
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

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }
}
