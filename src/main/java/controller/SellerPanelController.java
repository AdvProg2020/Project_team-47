package controller;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerPanelController extends UserPanelController{
    public static void companyInfo(){}
    public static void salesHistory(){}
    public static void manageProduct(){}
    public static void viewProduct(String id){}
    public static void editProduct(String productId,String field,String newValue){}
    public static void viewBuyers(String productId){}
    public static void addProduct(HashMap<String,String> productInformationHashMap, ArrayList<String> specialProperties){}

    public static void removeProduct(String productId){}
    public static void showCategories(){}
    public static void showSellerOffs(){}
    public static void showOff(String offId){}
    public static void editOff(String offId,String intendedEdit,Object editingValue){}
    public static void addOff(HashMap<String,String> offInformationHashMap, ArrayList<String> products){}

}
