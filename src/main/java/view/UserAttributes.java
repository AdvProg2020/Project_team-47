package view;

public class UserAttributes {
    private static String type;
    private static double money;
    private static boolean signedIn;
    private static boolean managerRegister;
    private static String username;
    private static String password;
    static {
        type = "customer";
        money = 0;
        signedIn = false;
        managerRegister = false;
        username = "";
        password = "";
    }

    public static String getType() {
        return type;
    }

    public static double getMoney() {
        return money;
    }

    public static boolean isManagerRegister() {
        return managerRegister;
    }

    public static void setManagerRegister(boolean managerRegister) {
        UserAttributes.managerRegister = managerRegister;
    }

    public static boolean isSignedIn() {
        return signedIn;
    }

    public static void setType(String type) {
        UserAttributes.type = type;
    }

    public static void setMoney(double money) {
        UserAttributes.money = money;
    }

    public static void setSignedIn(boolean signedIn) {
        UserAttributes.signedIn = signedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUsername(String username) {
        UserAttributes.username = username;
    }

    public static void setPassword(String password) {
        UserAttributes.password = password;
    }
}
