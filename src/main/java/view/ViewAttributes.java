package view;

public class ViewAttributes {
    private static String userType;
    private static double userMoney;
    private static boolean userSignedIn;
    private static boolean managerRegister;
    private static String username;
    private static String password;
    private static String productId;

    static {
        userType = "customer";
        userMoney = 0;
        userSignedIn = false;
        managerRegister = false;
        username = "";
        password = "";
        productId = "";
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        ViewAttributes.userType = userType;
    }

    public static double getUserMoney() {
        return userMoney;
    }

    public static void setUserMoney(double userMoney) {
        ViewAttributes.userMoney = userMoney;
    }

    public static boolean isManagerRegister() {
        return managerRegister;
    }

    public static void setManagerRegister(boolean managerRegister) {
        ViewAttributes.managerRegister = managerRegister;
    }

    public static boolean isUserSignedIn() {
        return userSignedIn;
    }

    public static void setUserSignedIn(boolean userSignedIn) {
        ViewAttributes.userSignedIn = userSignedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ViewAttributes.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ViewAttributes.password = password;
    }

    public static String getProductId() {
        return productId;
    }

    public static void setProductId(String productId) {
        ViewAttributes.productId = productId;
    }
}
