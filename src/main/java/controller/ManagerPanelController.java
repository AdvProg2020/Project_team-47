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
        sendAnswer(User.getAllUsers(field, direction), "user");
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
        actionCompleted();
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
        managerInformationHashMap.put("type", "manager");
        if (!LoginController.checkRegisterInfoKey(managerInformationHashMap)) {
            sendError("Not enough information!!");
        } else {
            LoginController.registerUser(managerInformationHashMap, "manager");
            actionCompleted();
        }
    }

    public static void manageAllProducts(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "product")) {
            sendError("Can't sort with this field and direction!!");
            return;
        }
        sendAnswer(Product.getAllProductInfo(sortField, sortDirection), "product");
    }

    public static void removeProduct(String productId) {
        if (!Product.isThereProduct(productId)) {
            sendError("There isn't any product with this id!!");
            return;
        }
        Product.removeProduct(productId);
        actionCompleted();
    }

    public static void createDiscountCode(HashMap<String, String> discountInfo, ArrayList<String> usernames) {
        if (discountCodeInfoHasError(discountInfo, usernames))
            return;

        ArrayList<Customer> users = new ArrayList<>();
        for (String username : usernames) {
            Customer user = (Customer) User.getUserByUsername(username);
            users.add(user);
        }

        createDiscountCodeAfterChecking(discountInfo, users);
    }

    private static void createDiscountCodeAfterChecking(HashMap<String, String> discountInfo, ArrayList<Customer> users) {
        int maxUsableTime, maxDiscountAmount, percent;
        percent = Integer.parseInt(discountInfo.get("percent"));
        maxDiscountAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
        maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));

        DiscountCode discountCode = new DiscountCode();
        discountCode.setDiscountStartTime(Date.getDateWithString(discountInfo.get("start-time")));
        discountCode.setDiscountFinishTime(Date.getDateWithString(discountInfo.get("finish-time")));
        discountCode.setMaxDiscountAmount(maxDiscountAmount);
        discountCode.setMaxUsableTime(maxUsableTime);
        discountCode.setUsersAbleToUse(users);
        discountCode.setDiscountPercent(percent);
        for (Customer user : users) {
            user.addDiscountCode(discountCode);
        }

        sendAnswer(discountCode.getDiscountCode(), null);
    }

    private static boolean discountCodeInfoHasError(HashMap<String, String> discountInfo, ArrayList<String> usernames) {
        //checking that discount info HashMap contains all required key
        String[] discountKey = {"start-time", "finish-time", "max-usable-time", "max-discount-amount", "percent"};
        for (String key : discountKey) {
            if (!discountInfo.containsKey(key)) {
                sendError("Not enough information!!");
                return true;
            }
        }

        //check that users who could use code exists and check start and finish time
        if (User.isThereCustomersWithUsername(usernames)) {
            sendError("There isn't any customer for some of username you entered!!");
            return true;
        } else if (!discountDateIsValid(discountInfo.get("start-time"), discountInfo.get("finish-time"))) {
            sendError("Dates are invalid!!");
            return true;
        }

        //check that integer value entered correctly
        try {
            int percent = Integer.parseInt(discountInfo.get("percent"));
            int maxDiscountAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
            int maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));
            if (percent >= 100 || percent <= 0) {
                sendError("Percent should be a number between 0 and 100!!");
                return true;
            } else if (maxDiscountAmount <= 0) {
                sendError("Max discount amount should be positive!!");
                return true;
            } else if (maxUsableTime <= 0) {
                sendError("Max usable time should be positive!!");
                return true;
            }
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return true;
        }

        return false;
    }

    private static boolean discountDateIsValid(String start, String finish) {
        //this function will check that discount starting and finishing time entered correctly

        if (!(Date.isDateFormatValid(start) && Date.isDateFormatValid(finish))) {
            sendError("Wrong format for date!!");
            return false;
        }

        Date startDate = Date.getDateWithString(start);
        Date finishDate = Date.getDateWithString(finish);

        if (startDate.before(Date.getCurrentDate())) {
            sendError("Can't set start time to this value!!");
            return false;
        } else if (finishDate.before(Date.getCurrentDate())) {
            sendError("Can't set finish time to this value!!");
            return false;
        } else if (!startDate.before(finishDate)) {
            sendError("Start time should be before finish time!!");
            return false;
        }
        return true;
    }

    public static void viewAllDiscountCodes(String field, String direction) {
        if (!checkSort(field, direction, "discount-code")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(DiscountCode.getAllDiscountCodeInfo(field, direction), "code");
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

        DiscountCode discountCode = DiscountCode.getDiscountById(code);
        switch (field) {
            case "start-time":
                editCodeTime(discountCode, newValue, "Starting time");
                return;
            case "finish-time":
                editCodeTime(discountCode, newValue, "Finishing time");
                return;
            case "max-discount-amount":
                editCodeIntegerValues(discountCode, "max-discount-amount", newValue);
                break;
            case "percent":
                editCodeIntegerValues(discountCode, "percent", newValue);
                break;
            case "max-usable-time":
                editCodeIntegerValues(discountCode, "max-usable-time", newValue);
                break;
            default:
                sendError("You can't change this field!!");
        }
    }

    private static void editCodeTime(DiscountCode discountCode, String timeString, String type) {
        if (!Date.isDateFormatValid(timeString)) {
            sendError("Please enter right value!!");
            return;
        }
        Date time = Date.getDateWithString(timeString);
        if (time.before(Date.getCurrentDate())) {
            sendError(type + " can't change to this value!!");
            return;
        }
        switch (type) {
            case "Starting time":
                discountCode.setDiscountStartTime(time);
                break;
            case "Finishing time":
                discountCode.setDiscountFinishTime(time);
                break;
        }
        actionCompleted();
    }

    private static void editCodeIntegerValues(DiscountCode discountCode, String type, String integerString) {
        int integer;
        try {
            integer = Integer.parseInt(integerString);
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return;
        }

        if (integer <= 0) {
            sendError("Please enter a positive value!!");
            return;
        }
        switch (type) {
            case "max-usable-time":
                discountCode.setMaxUsableTime(integer);
                break;
            case "max-discount-amount":
                discountCode.setMaxDiscountAmount(integer);
                break;
            case "percent":
                if (integer >= 100) {
                    sendError("Percent should be a number between 0 and 100!!");
                    return;
                }
                discountCode.setDiscountPercent(integer);
                break;
        }
        actionCompleted();
    }

    public static void removeDiscountCode(String code) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
            return;
        }
        DiscountCode discountCode = DiscountCode.getDiscountById(code);
        discountCode.remove();
    }

    public static void manageRequest(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "request")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Request.allRequestInfo(sortField, sortDirection), "request");
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
        actionCompleted();
    }

    public static void declineRequest(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        }
        Request.declineNewRequest(id);
        actionCompleted();
    }

    public static void manageCategories(String sortField, String sortDirection) {
        if (!checkSort(sortField, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
    }

    public static void addCategory(String name, ArrayList<String> specialProperties) {
        if (Category.isThereCategory(name)) {
            sendError("There is a category with this name!!");
            return;
        }
        Category category = new MainCategory();
        category.setName(name);
        category.setSpecialProperties(specialProperties);
    }

    public static void addSubCategory(String subCategoryName, String mainCategoryName, ArrayList<String> specialProperties) {
        if (Category.isThereCategory(subCategoryName)) {
            sendError("There is a category with this name!!");
        } else if (!Category.isThereMainCategory(mainCategoryName)) {
            sendError("There isn't any category with this name!!");
        } else {
            MainCategory mainCategory = Category.getMainCategoryByName(mainCategoryName);
            for (String specialProperty : mainCategory.getSpecialProperties()) {
                if (!specialProperties.contains(specialProperty)) {
                    specialProperties.add(specialProperty);
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
        assert mainCategory != null;
        switch (field) {
            case "name":
                mainCategory.setName(changeValue);
                actionCompleted();
                break;
            case "add property":
                addPropertyToMainCategory(mainCategory, changeValue);
                break;
            case "remove property":
                removePropertyFromMainCategory(mainCategory, changeValue);
                break;
            default:
                sendError("You can't change this!!");
        }
    }

    private static void removePropertyFromMainCategory(MainCategory mainCategory, String specialProperty) {
        mainCategory.removeSpecialProperties(specialProperty);
        actionCompleted();
    }

    private static void addPropertyToMainCategory(MainCategory mainCategory, String specialProperty) {
        //adding properties to main categories
        if (!mainCategory.getSpecialProperties().contains(specialProperty)) {
            mainCategory.addSpecialProperties(specialProperty);
        }

        //adding properties to sub categories
        for (Category subCategory : mainCategory.getSubCategories()) {
            if (!subCategory.getSpecialProperties().contains(specialProperty)) {
                subCategory.addSpecialProperties(specialProperty);
            }
        }

        actionCompleted();
    }

    public static void editSubCategory(String categoryName, String field, String changeValue) {
        if (!Category.isThereSubCategory(categoryName)) {
            sendError("There isn't any category with this name!!");
            return;
        }

        SubCategory subCategory = Category.getSubCategoryByName(categoryName);
        assert subCategory != null;
        switch (field) {
            case "name":
                subCategory.setName(changeValue);
                actionCompleted();
                break;
            case "add property":
                addPropertyToSubCategory(subCategory, changeValue);
                actionCompleted();
                break;
            case "remove property":
                removePropertyFromSubCategory(subCategory, changeValue);
                break;
            default:
                sendError("You can't change this!!");
        }
    }

    private static void addPropertyToSubCategory(SubCategory subCategory, String specialProperty) {
        if (!subCategory.getSpecialProperties().contains(specialProperty)) {
            subCategory.addSpecialProperties(specialProperty);
        }
        actionCompleted();
    }

    private static void removePropertyFromSubCategory(SubCategory subCategory, String specialProperty) {
        MainCategory mainCategory = subCategory.getMainCategory();
        if (mainCategory.getSpecialProperties().contains(specialProperty)) {
            mainCategory.removeSpecialProperties(specialProperty);
        } else if (subCategory.getSpecialProperties().contains(specialProperty))
            subCategory.removeSpecialProperties(specialProperty);

        actionCompleted();
    }
}//end ManagerPanelController
