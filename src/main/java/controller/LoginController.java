package controller;

import model.user.Seller;
import model.user.User;

import java.util.HashMap;

public class LoginController extends Controller {
    public static void login(String username,String password){}
    public static void register(HashMap<String,String> registerInformationHashMap){}
    public static void logout(){}
    public static boolean passwordHasEnoughStrength(String pass){return true;}
    public static boolean passwordIsValid(String password){return true;}
    public static boolean usernameIsValid(String username){return true;}
    public static boolean emailIsValid(String email){return true;}
    public static boolean phoneNumberIsValid(String email){return true;}
}
