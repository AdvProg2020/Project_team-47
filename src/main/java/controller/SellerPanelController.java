package controller;

import model.category.Category;
import model.discount.Off;
import model.others.Date;
import model.others.Product;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerPanelController extends UserPanelController {
    public static void companyInfo() {
        Seller seller = (Seller) loggedUser;
        sendAnswer(seller.getCompanyName(), seller.getCompanyInfo());
    }

    public static void salesHistory(String sortField, String direction) {
        //this function will sending selling log info to the client

        if (checkSort(sortField, direction, "log")) {
            sendError("Can't sort log with this field or direction!!");
            return;
        }
        sendAnswer(((Seller) loggedUser).getAllSellLogsInfo(sortField, direction), "log");
    }

    public static void manageProduct() {
        //this function will sending seller products info by sorting by seen time

        sendAnswer(((Seller) loggedUser).getAllProductInfo("seen-time", "ascending"), "product");
    }

    public static void viewProduct(String id) {
        //this function will send a product info that is in the seller's selling list

        Product product = Product.getProductWithId(id);

        //checking that seller has this product or not
        if (!sellerHasProduct(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }

        //send product info(if seller has this product)
        sendAnswer(product.getProductInfo());
    }

    public static void editProduct(String productId, String field, Object newValue, String editType) {
        //this function use to edit product and new value could be a string or HashMap of
        //string to change special properties

        Product product = Product.getProductWithId(productId);

        if (!sellerHasProduct(product)) {
            //check that seller has product and seller can edit product with that field and new value
            sendError("You don't have this product in your selling list!!");
            return;
        } else if (!canEditProduct(field, newValue, editType, product)) {
            //if seller can't edit product with this field and new value the error will send by canEditProduct function
            return;
        }

        ((Seller) loggedUser).editProduct(editType, field, newValue, product);

        actionCompleted();
    }

    private static boolean canEditProduct(String field, Object newValue, String editType, Product product) {
        //check that product can be edited with intended field and value

        if (newValue instanceof String) {
            return canEditStringField(field, (String) newValue);
        } else if (newValue instanceof HashMap) {
            try {
                HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
                if (field.equalsIgnoreCase("special-property") &&
                        canEditProductSpecialProperties(specialProperties, editType, product)) {

                    return true;
                }
            } catch (Exception e) {
                sendError("Wrong properties!!");
            }
        }
        return false;
    }

    private static boolean canEditStringField(String field, String newValue) {
        switch (field) {
            case "name":
            case "description":
                if (newValue.isEmpty()) {
                    sendError("This field can't be empty!!");
                    return false;
                }
                return true;

            case "price":
                try {
                    Double.parseDouble(newValue);
                    return true;
                } catch (NumberFormatException e) {
                    sendError("Please enter valid number!!");
                    return false;
                }

            case "number-of-product":
                try {
                    Integer.parseInt(newValue);
                    return true;
                } catch (NumberFormatException e) {
                    sendError("Please enter valid number!!");
                    return false;
                }

            case "category":
                if (Category.isThereMainCategory(newValue)) {
                    return true;
                }
                sendError("There isn't any category with this name!!");
                return false;

            case "sub-category":
                if (Category.isThereSubCategory(newValue)) {
                    return true;
                }
                sendError("There isn't any subcategory with this name!!");
                return false;
        }
        return false;
    }

    private static boolean canEditProductSpecialProperties(HashMap<String, String> specialProperties, String editType, Product product) {
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
        //this function check that if special properties entered by use has all product special properties

        for (Map.Entry<String, String> temp : specialProperties.entrySet()) {
            if (!canChangeSpecialProperties(temp, product)) {
                sendError("Can't change this properties!!");
                return false;
            }
        }
        return true;
    }

    private static boolean canChangeSpecialProperties(Map.Entry<String, String> changingEntry, Product product) {
        for (Map.Entry<String, String> productEntry : product.getSpecialProperties().entrySet()) {
            if (changingEntry.getKey().equals(productEntry.getKey())) {
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
        if (!sellerHasProduct(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }

        sendAnswer(((Seller) loggedUser).getBuyers(product), "user");
    }

    public static void addProduct(HashMap<String, String> productInfo, HashMap<String, String> specialProperties) {
        //this function will check product info if it is valid then call addProduct method by seller to creating request

        if (checkAddingProductInformation(productInfo)) {
            return;
        }

        Category category = Category.getSubCategoryByName(productInfo.get("sub-category"));
        if (category == null) {
            category = Category.getMainCategoryByName(productInfo.get("category"));
        }
        assert category != null;
        if (!productPropertiesIsValid(specialProperties, category)) {
            return;
        }

        ((Seller) loggedUser).addProduct(productInfo, specialProperties);
    }

    private static boolean productPropertiesIsValid(HashMap<String, String> properties, Category category) {
        //this function will check category properties and
        //if product has all its category's properties it will return true

        for (String specialProperty : category.getSpecialProperties()) {
            if (!properties.containsKey(specialProperty)) {
                sendError("This product with this category should have this property:\n" + specialProperty);
                return false;
            }
        }
        return true;
    }

    private static boolean checkAddingProductInformation(HashMap<String, String> productInformationHashMap) {
        //check that if client send all information to creating product

        String[] productKey = {"name", "price", "number-in-stock", "category", "description", "sub-category", "company"};
        for (String key : productKey) {
            if (!productInformationHashMap.containsKey(key)) {
                sendError("Not enough information!!");
                return false;
            }
        }
        if (!Category.isThereMainCategory(productInformationHashMap.get("category"))) {
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
        product.removeSellerFromProduct((Seller) loggedUser);
    }

    public static void viewBalance() {
        double money = ((Seller) loggedUser).getMoney();
        sendAnswer(money);
    }

    public static void showSellerOffs(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "off")) {
            sendError("Can't sort with this field and direction!!");
        } else {
            sendAnswer(((Seller) loggedUser).getAllOffsInfo(sortField, sortDirection), "off");
        }
    }

    public static void showOff(String offId) {
        if (!((Seller) loggedUser).sellerHasThisOff(offId)) {
            sendError("You don't have this off!!");
        } else {
            sendAnswer(((Seller) loggedUser).getOffInfo(offId));
        }
    }

    public static void editOff(String offId, String field, Object newValue, String type) {
        //this function will check that if off can be edited with this value and
        //if yes then it calls editOff function on seller to creating request

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
                    if (temp < 100 && temp > 0) {
                        return true;
                    }
                    sendError("Percent should be between 0 and 100!!");
                    return false;
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

        //finding products by their id and save it to products array list
        ArrayList<Product> products = new ArrayList<>();
        for (String id : productsId) {
            Product product = Product.getProductWithId(id);
            if (product == null) {
                sendError("There isn't product with this id: " + id + " !!");
                return false;
            } else
                products.add(product);
        }

        if (type == null) {
            sendError("Wrong command!!");
            return false;
        }

        switch (type) {
            case "append":
                for (Product product : products) {
                    if (off.isItInOff(product)) {
                        products.remove(product);
                    }
                }
            case "replace":
                return true;
            case "remove":
                for (Product product : products) {
                    if (!off.isItInOff(product)) {
                        sendError("Some of this products doesn't exist on off's products!!");
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    private static boolean canEditOffTime(Object newValue, Off off, String field) {
        int flag = 1;
        if (!(newValue instanceof String)) {
            sendError("Wrong command!!");
        } else if (!Date.isDateFormatValid((String) newValue)) {
            sendError("Wrong format for date!!");
        }

        Date date = Date.getDateWithString((String) newValue);

        if (field.equals("start-time") && off.isOffStarted()) {
            sendError("This off started now and you can't change it's starting time!");
        } else if (field.equals("start-time") && date.before(Date.getCurrentDate())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && off.isOffFinished()) {
            sendError("This off finished now and you can't change it!!");
        } else if (field.equals("finish-time") && date.before(Date.getCurrentDate())) {
            sendError("Can't change finish time to this value!!");
        } else if (field.equals("start-time") && date.after(off.getDiscountFinishTime())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && date.before(off.getDiscountStartTime())) {
            sendError("Can't change finish time to this value!!");
        } else {
            flag = 0;
        }
        return flag == 0;
    }

    public static void addOff(HashMap<String, String> offInformationHashMap, ArrayList<String> products) {
        if (!isOffInfoValid(offInformationHashMap))
            return;

        for (String productId : products) {
            if (!((Seller) loggedUser).hasProduct(productId)) {
                sendError("You don't have product with this id: " + productId + "!!");
                return;
            }
        }
        ((Seller) loggedUser).addOff(offInformationHashMap, products);

    }

    private static boolean isOffInfoValid(HashMap<String, String> offInfo) {
        //check that if HashMap has all required key
        String[] offInfoKey = {"start-time", "finish-time", "percent"};
        for (String key : offInfoKey) {
            if (offInfo.containsKey(key)) {
                sendError("Not enough information!!");
                return false;
            }
        }
        if (!isOffDatesValid(offInfo.get("start-time"), offInfo.get("finish-time")))
            return false;
        else return isPercentValid(offInfo.get("percent"));

        //all error that could be happened checked and didn't find any error so function should return true
    }

    private static boolean isPercentValid(String percentString) {
        //check that if percent is valid
        try {
            int percent = Integer.parseInt(percentString);
            if (percent >= 100 || percent <= 0) {
                sendError("Percent should be a number between 0 and 100!!");
                return false;
            }
        } catch (NumberFormatException e) {
            sendError("Please enter valid number for off percent!!");
            return false;
        }
        return true;
    }

    private static boolean isOffDatesValid(String start, String finish) {
        //check that if starting and finishing date is valid

        if (!Date.isDateFormatValid(start) ||
                !Date.isDateFormatValid(finish)) {

            sendError("Wrong date format!!");
            return false;
        }


        Date startingDate = Date.getDateWithString(start);
        Date finishingDate = Date.getDateWithString(finish);
        Date currentDate = Date.getCurrentDate();
        if (startingDate.before(currentDate) ||
                finishingDate.before(currentDate) ||
                !startingDate.before(finishingDate)) {

            sendError("Wrong Date!!");
            return false;
        }

        return true;
    }

    private static boolean sellerHasProduct(Product product) {
        return (product != null && ((Seller) loggedUser).getAllProducts().contains(product));
    }
}//end SellerPanelController class
