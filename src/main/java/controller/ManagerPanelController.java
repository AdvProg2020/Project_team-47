package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ManagerPanelController extends UserPanelController {
    public static void manageUsers(){}
    public static void viewUser(String username){}
    public static void changeRole(String username,String role){}
    public static void deleteUser(String username){}
    public static void createManager(HashMap<String,String> managerInformationHashMap){}
    public static void manageAllProducts(){}
    public static void removeProduct(String productId){}
    public static void createDiscountCode(HashMap<String,String> discountInformationHashMap,
                                          ArrayList<String> users){}

    public static void viewAllDiscountCodes(){}
    public static void viewDiscountCode(String code){}
    public static void editDiscountCode(String code,String field,String newValue){}
    public static void removeDiscountCode(String code){}

    public static void manageRequest(){}
    public static void requestDetail(String id){}
    public static void acceptRequest(String id){}
    public static void declineRequest(String id){}

    public static void manageCategories(){}
    public static void addCategory(HashMap<String,String> categoryInformationHashMap,ArrayList<String> specialProperties){}
    public static void addSubCategory(HashMap<String,String> subCategoryInformationHashMap,ArrayList<String> specialProperties){}
    public static void removeCategory(String categoryName){}
    public static void editCategory(String categoryName, String field, Objects newValue){}
}
