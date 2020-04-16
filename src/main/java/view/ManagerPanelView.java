package view;

import controller.UserPanelController;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerPanelView extends UserPanelView {

    public static void manageUsers(){}
    public static void manageProducts(){}
    public static void createDiscountCode(){}
    public static void viewDiscountCodes(){}
    public static void editDiscountCodeForManager(String code){}
    public static void manageAllProducts(){}
    public static void manageRequest(){}
    public static void manageCategories(){}
    private static void createManager(){}


    private static void viewUser(String username){}
    private static void changeRole(String username,String role){}
    private static void deleteUser(String username){}
    private static void createManager(HashMap<String,String> managerInformationHashMap){}

    private static void removeProduct(String productId){}

    private static void createDiscountCode(HashMap<String,String> discountInformationHashMap
            ,ArrayList<String> users){}

    private static void showDiscountCode(String code){}
    private static void editDiscountCode(String code,String field,String newValue){}
    private static void removeDiscountCode(String code){}

    private static void requestDetail(String id){}
    private static void acceptRequest(String id){}
    private static void declineRequest(String id){}

    private static void editCategory(String categoryName,String field,Object newValue){}
    private static void addCategory(HashMap<String,String> categoryInformationHashMap
            , ArrayList<String> specialProperties){}
    private static void addSubCategory(HashMap<String,String> subCategoryInformationHashMap
            , ArrayList<String> specialProperties){}
    private static void removeCategory(String categoryName){}

}
