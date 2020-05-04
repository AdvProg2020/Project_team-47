package controller;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.discount.DiscountCode;
import model.others.Date;
import model.others.Product;
import model.others.request.Request;
import model.user.Customer;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerPanelController extends UserPanelController {
    public static void manageUsers(String field, String direction) {
        if (!checkSort(field, direction, "user")) {
            sendError("Can't sort with this field and direction!!");
            return;
        }
        sendAnswer(User.getAllUsers(null, null));
    }

    public static void viewUser(String username) {
        if (!User.isThereUserWithUsername(username)) {
            sendError("There isn't any account with this username!!");
        } else {
            User user = User.getUserByUsername(username);
            assert user != null;
            sendAnswer(user.userInfoForSending());
        }
    }

    public static void changeRole(String username, String role) {
        if (!User.isThereUserWithUsername(username)) {
            sendError("There isn't any account with this username!!");
            return;
        }
        User user = User.getUserByUsername(username);
        assert user != null;
        if ((!role.equals("manager")) && (!role.equals("customer")) && (!role.equals("seller"))) {
            sendError("There isn't this type of account in this shop!!");
            return;
        }
        user.changeRole(role);
        sendAnswer("Role changed successfully.");
    }

    public static void deleteUser(String username) {
        if (!User.isThereUserWithUsername(username)) {
            sendError("There isn't any account with this username!!");
            return;
        }
        User user = User.getUserByUsername(username);
        assert user != null;
        user.deleteUser();
    }

    public static void createManager(HashMap<String, String> managerInformationHashMap) {
        if (!managerInformationHashMap.containsKey("username") ||
                !managerInformationHashMap.containsKey("password") ||
                !managerInformationHashMap.containsKey("first-name") ||
                !managerInformationHashMap.containsKey("last-name") ||
                !managerInformationHashMap.containsKey("email") ||
                !managerInformationHashMap.containsKey("phone-number")) {
            sendError("Not enough information!!");
        } else {
            managerInformationHashMap.put("type", "manager");
            LoginController.registerUser(managerInformationHashMap, "manager");
            sendAnswer("Manager Created successfully.");
        }
    }

    public static void manageAllProducts(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "product")) {
            sendError("Can't sort with this field and direction!!");
            return;
        }
        sendAnswer(Product.getAllProductInfo(sortField, sortDirection));
    }

    public static void removeProduct(String productId) {
        if (!Product.isThereProduct(productId)) {
            sendError("There isn't any product with this id!!");
            return;
        }
        Product.removeProduct(productId);
        sendAnswer("Product removed successfully.");
    }

    public static void createDiscountCode(HashMap<String, String> discountInfo,
                                          ArrayList<String> usernames) {
        if (discountCodeInfoHasError(discountInfo, usernames))
            return;

        int maxUsableTime, maxDiscountAmount, percent;
        try {
            percent = Integer.parseInt(discountInfo.get("percent"));
            maxDiscountAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
            maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return;
        }

        DiscountCode discountCode = new DiscountCode();
        ArrayList<Customer> users = new ArrayList<>();
        for (String username : usernames) {
            Customer user = (Customer) User.getUserByUsername(username);
            users.add(user);
            assert user != null;
            user.addDiscountCode(discountCode);
        }
        discountCode.setDiscountStartTime(Date.getDateWithString(discountInfo.get("start-time")));
        discountCode.setDiscountFinishTime(Date.getDateWithString(discountInfo.get("finish-time")));
        discountCode.setMaxDiscountAmount(maxDiscountAmount);
        discountCode.setMaxUsableTime(maxUsableTime);
        discountCode.setUsersAbleToUse(users);
        discountCode.setDiscountPercent(percent);
        sendAnswer("Discount code created successfully.\nDiscount Code: " + discountCode.getDiscountCode());
    }

    private static boolean discountCodeInfoHasError(HashMap<String, String> discountInfo, ArrayList<String> usernames) {
        if (!discountInfo.containsKey("start-time") ||
                !discountInfo.containsKey("finish-time") ||
                !discountInfo.containsKey("max-usable-time") ||
                !discountInfo.containsKey("max-discount-amount") ||
                !discountInfo.containsKey("percent")) {
            sendAnswer("Not enough information!!");
            return true;
        } else if (User.isThereCustomersWithUsername(usernames)) {
            sendError("There isn't any customer for some of username you entered!!");
            return true;
        } else if (!Date.isDateFormatValid(discountInfo.get("start-time")) ||
                !Date.isDateFormatValid(discountInfo.get("finish-time")) ||
                !Date.getDateWithString(discountInfo.get("start-time")).before(Date.getDateWithString("finsih-time"))) {

            sendError("Dates are invalid!!");
            return true;
        }
        return false;
    }

    public static void viewAllDiscountCodes(String field, String direction) {
        if (!checkSort(field, direction, "discount-code")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(DiscountCode.getAllDiscountCodeInfo(field, direction));
    }

    public static void viewDiscountCode(String code) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
        } else
            sendAnswer(DiscountCode.getDiscountById(code).discountInfoForSending());
    }

    public static void editDiscountCode(String code, String field, String newValue) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
            return;
        }
        DiscountCode discountCode = (DiscountCode) DiscountCode.getDiscountById(code);
        int temp;
        switch (field) {
            case "start-time":
                if (!Date.isDateFormatValid(newValue)) {
                    sendError("Please enter right value!!");
                    return;
                }
                discountCode.setDiscountStartTime(Date.getDateWithString(newValue));
                return;
            case "finish-time":
                if (!Date.isDateFormatValid(newValue)) {
                    sendError("Please enter right value!!");
                    return;
                }
                discountCode.setDiscountFinishTime(Date.getDateWithString(newValue));
                return;
            case "max-amount":
                try {
                    temp = Integer.parseInt(newValue);
                    discountCode.setMaxDiscountAmount(temp);
                } catch (NumberFormatException e) {
                    sendError("Please enter a valid number!!");
                    return;
                }
            case "percent":
                try {
                    temp = Integer.parseInt(newValue);
                    discountCode.setDiscountPercent(temp);
                } catch (NumberFormatException e) {
                    sendError("Please enter a valid number!!");
                    return;
                }
            case "max-usable-time":
                try {
                    temp = Integer.parseInt(newValue);
                    discountCode.setMaxUsableTime(temp);
                } catch (NumberFormatException e) {
                    sendError("Please enter a valid number!!");
                    return;
                }
            default:
                sendError("You can't change this field!!");
        }
    }

    public static void removeDiscountCode(String code) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
            return;
        }
        DiscountCode discountCode = (DiscountCode) DiscountCode.getDiscountById(code);
        discountCode.remove();
    }

    public static void manageRequest(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "request")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Request.allRequestInfo(sortField, sortDirection));
    }

    public static void requestDetail(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        }
        Request request = Request.getRequestById(id);
        assert request != null;
        sendAnswer(request.detail());
    }

    public static void acceptRequest(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        } else if (!Request.canDoRequest(id)) {
            Request.declineNewRequest(id);
            sendError("This request can't be accept now due to some change in data!!");
        }
        Request.acceptNewRequest(id);
        sendAnswer("Action completed.");
    }

    public static void declineRequest(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        }
        Request.declineNewRequest(id);
        sendAnswer("Action completed.");
    }

    public static void manageCategories(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection));
    }

    public static void addCategory(String name, ArrayList<String> specialProperties) {
        if (Category.isThereMainCategory(name)) {
            sendError("There is a category with this name!!");
            return;
        }
        Category category = new MainCategory();
        category.setName(name);
        category.setSpecialProperties(specialProperties);
    }

    public static void addSubCategory(String subCategoryName, String mainCategoryName, ArrayList<String> specialProperties) {
        if (Category.isThereSubCategory(subCategoryName)) {
            sendError("There is a sub category with this name!!");
        } else if (!Category.isThereMainCategory(mainCategoryName)) {
            sendError("There isn't any category with this name!!");
        } else {
            MainCategory mainCategory = Category.getMainCategoryByName(mainCategoryName);
            for (String specialProperty : mainCategory.getSpecialProperties()) {
                if (!specialProperties.contains(specialProperty)) {
                    sendError("Subcategory should have it's main category's special properties!!");
                    return;
                }
            }
            SubCategory subCategory = new SubCategory();
            subCategory.setName(subCategoryName);
            subCategory.setMainCategory(mainCategory);
            subCategory.setSpecialProperties(specialProperties);
            mainCategory.addSubCategory(subCategory);
        }
    }

    public static void removeMainCategory(String categoryName) {
        if (!Category.isThereMainCategory(categoryName)) {
            sendError("There isn't any category with this name!!");
            return;
        }
        Category.removeMainCategory(categoryName);
    }

    public static void removeSubCategory(String subCategoryName) {
        if (!Category.isThereSubCategory(subCategoryName)) {
            sendError("There isn't any subcategory with this name!!");
            return;
        }
        Category.removeSubCategory(subCategoryName);
    }

    public static void editMainCategory(String categoryName, String field, String changeValue) {
        if (!Category.isThereMainCategory(categoryName)) {
            sendError("There isn't any category with this name!!");
            return;
        }
        MainCategory mainCategory = Category.getMainCategoryByName(categoryName);
        switch (field) {
            case "name":
                mainCategory.setName(changeValue);
                break;
            case "add properties":
                mainCategory.addSpecialProperties(changeValue);
                for (Category subCategory : mainCategory.getSubCategories()) {
                    subCategory.addSpecialProperties(changeValue);
                }
                break;
            case "remove properties":
                mainCategory.removeSpecialProperties(changeValue);
                for (Category subCategory : mainCategory.getSubCategories()) {
                    subCategory.removeSpecialProperties(changeValue);
                }
                break;
            default:
                sendError("You can't change this!!");
        }
    }

    public static void editSubCategory(String categoryName, String field, String changeValue) {
        if (!Category.isThereSubCategory(categoryName)) {
            sendError("There isn't any category with this name!!");
            return;
        }
        SubCategory subCategory = Category.getSubCategoryByName(categoryName);
        switch (field) {
            case "name":
                subCategory.setName(changeValue);
                break;
            case "add properties":
                subCategory.addSpecialProperties(changeValue);
                break;
            case "remove properties":
                if (subCategory.getMainCategory().getSpecialProperties().contains(changeValue)) {
                    subCategory.getMainCategory().getSpecialProperties().remove(changeValue);
                    for (Category category : subCategory.getMainCategory().getSubCategories()) {
                        category.removeSpecialProperties(changeValue);
                    }
                } else
                    subCategory.removeSpecialProperties(changeValue);
                break;
            default:
                sendError("You can't change this!!");
        }
    }
}
