package controller;

import model.category.Category;
import model.discount.Off;
import model.others.Date;
import model.others.Product;
import model.others.request.Request;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerPanelController extends UserPanelController {
    public static void companyInfo() {
        Seller seller = (Seller) loggedUser;
        sendAnswer(seller.getCompanyName() + "\n" + seller.getCompanyInfo());
    }

    public static void salesHistory(String sortField, String direction) {
        if (checkSort(sortField, direction, "log")) {
            sendError("Can't sort log with this field or direction!!");
            return;
        }
        sendAnswer(((Seller) loggedUser).getAllSellLogsInfo(sortField, direction));
    }

    public static void manageProduct() {
        sendAnswer(((Seller) loggedUser).getAllProductInfo("seen-time", "ascending"));
    }

    public static void viewProduct(String id) {
        Product product = Product.getProductWithId(id);
        if (product == null || !((Seller) loggedUser).getAllProducts().contains(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }
        sendAnswer(product.getProductInfo());
    }

    public static void editProduct(String productId, String field, Object newValue, String editType) {
        Product product = Product.getProductWithId(productId);
        if (product == null || !((Seller) loggedUser).getAllProducts().contains(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        } else if (!canEditProduct(field, newValue, editType, product)) {
            sendError("Can't do that!!");
            return;
        }

        if (field.equals("category")) {
            newValue = Category.getMainCategoryByName((String) newValue);
        } else if (field.equals("sub-category")) {
            newValue = Category.getSubCategoryByName((String) newValue);
        }
        Request newRequest = new Request();
        ((Seller) loggedUser).editProduct(editType, field, newValue, product);
    }

    private static boolean canEditProduct(String field, Object newValue, String editType, Product product) {
        if (newValue instanceof String) {
            switch (field) {
                case "name":
                case "description":
                    return !((String) newValue).isEmpty();
                case "price":
                    try {
                        Double.parseDouble((String) newValue);
                        return true;
                    } catch (NumberFormatException e) {
                        sendError("Please enter valid number!!");
                        return false;
                    }
                case "number-of-product":
                    try {
                        Integer.parseInt((String) newValue);
                        return true;
                    } catch (NumberFormatException e) {
                        sendError("Please enter valid number!!");
                        return false;
                    }
                case "category":
                    if (Category.isThereMainCategory((String) newValue)) {
                        return true;
                    }
                    sendError("There isn't any category with this name!!");
                    return false;
                case "sub-category":
                    if (Category.isThereSubCategory((String) newValue)) {
                        return true;
                    }
                    sendError("There isn't any subcategory with this name!!");
                    return false;
            }
        } else if (newValue instanceof HashMap) {
            try {
                HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
                if (canEditProductSpecialProperties(specialProperties, editType, product)) {
                    return true;
                }
                sendError("Wrong properties!!");
                return false;
            } catch (Exception e) {
                sendError("Wrong properties!!");
                return false;
            }
        }
        return false;
    }

    private static boolean canEditProductSpecialProperties(HashMap<String, String> specialProperties, String editType, Product product) throws Exception {
        switch (editType) {
            case "append":
                return canAppendSpecialProperties(specialProperties, product);
            case "replace":
                return canReplaceSpecialProperties(specialProperties, product);
            case "change":
                return canChangeSpecialProperties(specialProperties, product);
            case "remove":
                return canRemoveSpecialProperties(specialProperties, product);
            default:
                return false;
        }
    }

    private static boolean canChangeSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        for (Map.Entry<String, String> temp : specialProperties.entrySet()) {
            if (!canChangeSpecialProperties(temp, product)) {
                sendError("Can't change this properties!!");
                return false;
            }
        }
        return true;
    }

    private static boolean canChangeSpecialProperties(Map.Entry<String, String> changingEntry, Product product) {
        for (Map.Entry<String, String> entry : product.getSpecialProperties().entrySet()) {
            if (changingEntry.getKey().equals(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    private static boolean canReplaceSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        Category category = product.getSubCategory();
        if (category == null)
            category = product.getMainCategory();
        for (String specialProperty : category.getSpecialProperties()) {
            if (!specialProperties.containsKey(specialProperty)) {
                sendError("Can't replace this properties!!");
                return false;
            }
        }
        return true;
    }

    private static boolean canRemoveSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        Category category = product.getSubCategory();
        if (category == null)
            category = product.getMainCategory();
        for (String specialProperty : category.getSpecialProperties()) {
            if (specialProperties.containsKey(specialProperty)) {
                sendError("Can't remove this properties!!");
                return false;
            }
        }
        return true;
    }

    private static boolean canAppendSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        for (Map.Entry<String, String> productEntry : product.getSpecialProperties().entrySet()) {
            for (Map.Entry<String, String> temp : specialProperties.entrySet()) {
                if (productEntry.getKey().equals(temp.getKey())) {
                    sendError("Cant't append this properties!!");
                    return false;
                }
            }
        }
        return true;
    }

    public static void viewBuyers(String productId) {
        Product product = Product.getProductWithId(productId);
        if (product == null || !((Seller) loggedUser).getAllProducts().contains(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }
        sendAnswer(((Seller) loggedUser).getBuyers(product));
    }

    public static void addProduct(HashMap<String, String> productInfo, HashMap<String, String> specialProperties) {
        if (checkAddingProductInformation(productInfo)) {
            return;
        }
        Category category = Category.getSubCategoryByName(productInfo.get("sub-category"));
        if (category == null) {
            category = Category.getMainCategoryByName(productInfo.get("category"));
        }
        for (String specialProperty : category.getSpecialProperties()) {
            if (!specialProperties.containsKey(specialProperty)) {
                sendError("Product should have at least its category's special properties!!");
                return;
            }
        }
        ((Seller) loggedUser).addProduct(productInfo, specialProperties);
    }

    private static boolean checkAddingProductInformation(HashMap<String, String> productInformationHashMap) {
        if (!productInformationHashMap.containsKey("name") ||
                !productInformationHashMap.containsKey("price") ||
                !productInformationHashMap.containsKey("number-in-stock") ||
                !productInformationHashMap.containsKey("category") ||
                !productInformationHashMap.containsKey("description") ||
                !productInformationHashMap.containsKey("sub-category") ||
                !productInformationHashMap.containsKey("company")) {
            sendError("Not enough information!!");
            return false;
        } else if (!Category.isThereMainCategory(productInformationHashMap.get("category"))) {
            sendError("There isn't category with this name!!");
            return false;
        }
        String subCategory = productInformationHashMap.get("sub-category");
        if (!subCategory.isEmpty() && !Category.isThereSubCategory(subCategory)) {
            sendError("There isn't sub category with this name!!");
            return false;
        }
        try {
            Integer.parseInt(productInformationHashMap.get("number-in-stock"));
            Double.parseDouble(productInformationHashMap.get("price"));
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return false;
        }
        return true;
    }

    public static void removeProduct(String productId) {
        Product product = Product.getProductWithId(productId);
        if (product == null || !((Seller) loggedUser).getAllProducts().contains(product)) {
            sendError("There isn't any product with this id in your selling list!!");
            return;
        }
        ((Seller) loggedUser).removeProduct(product);
    }

    public static void viewBalance() {
        double money = ((Seller) loggedUser).getMoney();
        sendAnswer(Double.toString(money));
    }

    public static void showSellerOffs(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "log")) {
            sendError("Can't sort with this field and direction!!");
        } else {
            sendError(((Seller) loggedUser).getAllSellLogsInfo(sortField, sortDirection));
        }
    }

    public static void showOff(String offId) {
        if (!((Seller) loggedUser).sellerHasThisOff(offId)) {
            sendError("You don't have this off!!");
        } else {
            sendAnswer(((Seller) loggedUser).getOff(offId));
        }
    }

    public static void editOff(String offId, String field, Object newValue, String type) {
        Off off = ((Seller) loggedUser).getOffById(offId);
        if (off == null) {
            sendError("There isn't off with this id in your offs!!");
            return;
        }
        if (!canEditOff(field, type, newValue, off)) {
            return;
        }
        ((Seller) loggedUser).editOff(off, field, newValue, type);
    }

    private static boolean canEditOff(String field, String type, Object newValue, Off off) {
        switch (field) {
            case "start-time":
            case "finish-time":
                return canEditOffTime(newValue, off, field);
            case "percent":
                try {
                    int temp = Integer.parseInt((String) newValue);
                    return temp < 100 && temp > 0;
                } catch (NumberFormatException e) {
                    sendError("Wrong number!!");
                    return false;
                }
            case "products":
                return canEditProductInOff(off, newValue, type);
        }
        return false;
    }

    private static boolean canEditProductInOff(Off off, Object newValue, String type) {
        ArrayList<String> productsId;
        try {
            productsId = (ArrayList<String>) newValue;
        } catch (ClassCastException e) {
            sendError("Wrong command!!");
            return false;
        }

        ArrayList<Product> products = new ArrayList<>();
        for (String id : productsId) {
            Product product = Product.getProductWithId(id);
            if (product == null) {
                sendError("There isnt' proudct with this id: " + product.getId() + " !!");
                return false;
            } else
                products.add(product);
        }

        switch (type) {
            case "append":
                for (Product product : products) {
                    if (off.isItInOff(product)) {
                        sendError("Can't append!!");
                        return false;
                    }
                }
                return true;
            case "remove":
                for (Product product : products) {
                    if (!off.isItInOff(product)) {
                        sendError("Can't remove these product!!");
                        return false;
                    }
                }
                return true;
            case "replace":
            default:
                return true;
        }
    }

    private static boolean canEditOffTime(Object newValue, Off off, String field) {
        int flag = 1;
        if (!(newValue instanceof String)) {
            sendError("Wrong command!!");
        } else if (!Date.isDateFormatValid((String) newValue)) {
            sendError("Wrong format for date!!");
        } else if (field.equals("start-time") && off.isOffStarted()) {
            sendError("This off started now and you can't change it's starting time!");
        } else if (field.equals("start-time") && Date.getDateWithString((String) newValue).before(Date.getCurrentDate())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && off.isOffFinished()) {
            sendError("This off finished now and you can't change it!!");
        } else if (field.equals("finish-time") && Date.getDateWithString((String) newValue).before(Date.getCurrentDate())) {
            sendError("Can't change finish time to this value!!");
        } else if (field.equals("start-time") && Date.getDateWithString((String) newValue).after(off.getDiscountFinishTime())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && Date.getDateWithString((String) newValue).before(off.getDiscountStartTime())) {
            sendError("Can't change finish time to this value!!");
        } else {
            flag = 0;
        }
        return flag == 0;
    }

    public static void addOff(HashMap<String, String> offInformationHashMap, ArrayList<String> products) {
        if (!offInformationHashMap.containsKey("start-time") ||
                !offInformationHashMap.containsKey("finish-time") ||
                !offInformationHashMap.containsKey("percent")) {

            sendError("Not enough information!!");
            return;
        } else if (!Date.isDateFormatValid(offInformationHashMap.get("start-time")) ||
                !Date.isDateFormatValid(offInformationHashMap.get("finish-time"))) {
            sendError("Wrong date format!!");

        } else if (Date.getDateWithString(offInformationHashMap.get("start-time")).before(Date.getCurrentDate()) ||
                Date.getDateWithString(offInformationHashMap.get("finish-time")).before(Date.getCurrentDate()) ||
                Date.getDateWithString(offInformationHashMap.get("start-time")).
                        after(Date.getDateWithString(offInformationHashMap.get("finish-time")))) {

            sendError("Wrong Date!!");
            return;
        }
        try {
            Integer.parseInt(offInformationHashMap.get("percent"));
        } catch (NumberFormatException e) {
            sendError("Please enter valid number for off percent!!");
            return;
        }
        for (String productId : products) {
            if (!((Seller) loggedUser).hasProduct(productId)) {
                sendError("You don't have product with this id: " + productId + "!!");
                return;
            }
        }
        ((Seller) loggedUser).addOff(offInformationHashMap, products);

    }
}
