package controller.panels.manager;

import controller.Command;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.NotEnoughInformation;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;
import static controller.panels.UserPanelCommands.checkSort;
import static controller.panels.UserPanelCommands.isThereProperty;

public abstract class ManageCategoriesCommand extends Command {
    public static ShowCategoriesCommand getShowCategoriesCommand() {
        return ShowCategoriesCommand.getInstance();
    }

    public static ShowSubCategoriesCommand getShowSubCategoriesCommand() {
        return ShowSubCategoriesCommand.getInstance();
    }

    public static EditCategoryCommand getEditCategoryCommand() {
        return EditCategoryCommand.getInstance();
    }

    public static AddCategoryCommand getAddCategoryCommand() {
        return AddCategoryCommand.getInstance();
    }

    public static RemoveCategoryCommand getRemoveCategoryCommand() {
        return RemoveCategoryCommand.getInstance();
    }

    public static EditSubCategoryCommand getEditSubCategoryCommand() {
        return EditSubCategoryCommand.getInstance();
    }

    public static AddSubCategoryCommand getAddSubCategoryCommand() {
        return AddSubCategoryCommand.getInstance();
    }

    public static RemoveSubCategoryCommand getRemoveSubCategoryCommand() {
        return RemoveSubCategoryCommand.getInstance();
    }
}


class ShowCategoriesCommand extends ManageCategoriesCommand {
    private static ShowCategoriesCommand command;

    private ShowCategoriesCommand() {
        this.name = "manage categories";
    }

    public static ShowCategoriesCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowCategoriesCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return manageCategories(request.getHashMap().get("field"), request.getHashMap().get("direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage manageCategories(String sortField, String sortDirection) throws InvalidSortException {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "category")) {
            throw new InvalidSortException();
        } else
            return sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
    }

}//end ShowCategoriesCommand class


class ShowSubCategoriesCommand extends ManageCategoriesCommand {
    private static ShowSubCategoriesCommand command;

    private ShowSubCategoriesCommand() {
        this.name = "manage sub categories";
    }

    public static ShowSubCategoriesCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowSubCategoriesCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) {
        return null;
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }


}//end ShowSubCategoriesCommand class


class EditCategoryCommand extends ManageCategoriesCommand {
    private static EditCategoryCommand command;

    private EditCategoryCommand() {
        this.name = "edit main category";
    }

    public static EditCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new EditCategoryCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException, CommonException, NotEnoughInformation {
        containNullField(request.getHashMap().get("category name"), request.getHashMap().get("field"),
                request.getHashMap().get("new value"));
        editMainCategory(request.getHashMap().get("category name"), request.getHashMap().get("field"),
                request.getHashMap().get("new value"), request.getHashMap());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void editMainCategory(String categoryName, String field, String changeValue, HashMap<String, String> reqInfo) throws CommonException, CategoryDoesntExistException, NotEnoughInformation {
        MainCategory mainCategory = Category.getMainCategoryByName(categoryName);
        switch (field) {
            case "name" -> {
                if (Category.isThereCategory(changeValue))
                    throw new CommonException("There is category with this name!!");
                mainCategory.setName(changeValue);
            }
            case "add property" -> addPropertyToMainCategory(mainCategory, changeValue, reqInfo);
            case "remove property" -> removePropertyFromMainCategory(mainCategory, changeValue);
            default -> throw new CommonException("You can't change this!!");
        }
        mainCategory.updateDatabase();
    }

    private void removePropertyFromMainCategory(MainCategory mainCategory, String specialProperty) {
        mainCategory.removeSpecialProperties(specialProperty);
    }

    private void addPropertyToMainCategory(MainCategory mainCategory, String specialProperty, HashMap<String, String> reqInfo) throws NotEnoughInformation {
        //adding properties to main categories
        if (isThereProperty(mainCategory, specialProperty))
            return;

        String type = reqInfo.get("type");
        String unit = reqInfo.get("unit");
        if (type == null)
            throw new NotEnoughInformation();
        else if (type.equals("numeric") && unit == null)
            throw new NotEnoughInformation();
        else if (!(type.equals("text") || type.equals("numeric")))
            throw new NotEnoughInformation();

        switch (type) {
            case "numeric" -> addNumericProperty(mainCategory, specialProperty, unit);
            case "text" -> addTextProperty(mainCategory, specialProperty);
        }
    }

    private void addNumericProperty(MainCategory mainCategory, String specialProperty, String unit) {
        mainCategory.addNumericProperty(specialProperty, unit);
        for (SubCategory subCategory : mainCategory.getSubCategories()) {
            if (!isThereProperty(subCategory, specialProperty)) {
                subCategory.addNumericProperty(specialProperty, unit);
                subCategory.updateDatabase();
            }
        }
    }

    private void addTextProperty(MainCategory category, String property) {
        category.addTextProperty(property);
        for (SubCategory subCategory : category.getSubCategories()) {
            if (!isThereProperty(subCategory, property)) {
                subCategory.addTextProperty(property);
                subCategory.updateDatabase();
            }
        }
    }

}//end EditCategoryCommand class


class AddCategoryCommand extends ManageCategoriesCommand {
    private static AddCategoryCommand command;

    private AddCategoryCommand() {
        this.name = "add main category";
    }

    public static AddCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new AddCategoryCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("name"), request.getProperties());
        checkPrimaryErrors(request);
        addCategory(request.getHashMap().get("name"), request.getProperties());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        if (Category.isThereCategory(request.getHashMap().get("name")))
            throw new CommonException("There is a category with this name!!");
        for (SpecialProperty property : request.getProperties()) {
            if (property.isItValid())
                throw new CommonException("This property isn't valid: " + property.getKey() + "!!");
        }
    }

    private void addCategory(String name, ArrayList<SpecialProperty> specialProperties) {
        Category category = new MainCategory();
        category.setName(name);
        category.setSpecialProperties(specialProperties);
        category.updateDatabase();
    }

}//end AddCategoryCommand class


class RemoveCategoryCommand extends ManageCategoriesCommand {
    private static RemoveCategoryCommand command;

    private RemoveCategoryCommand() {
        this.name = "remove main category";
    }

    public static RemoveCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new RemoveCategoryCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException {
        containNullField(request.getHashMap().get("category name"));
        removeMainCategory(request.getHashMap().get("category name"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void removeMainCategory(String categoryName) throws CategoryDoesntExistException {
        if (!Category.isThereMainCategory(categoryName)) throw new CategoryDoesntExistException();
        Category.removeMainCategory(categoryName);
    }

}//end RemoveCategoryCommand class


class EditSubCategoryCommand extends ManageCategoriesCommand {
    private static EditSubCategoryCommand command;

    private EditSubCategoryCommand() {
        this.name = "edit sub category";
    }

    public static EditSubCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new EditSubCategoryCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException,
            CommonException, NotEnoughInformation {
        containNullField(request.getHashMap().get("category name"), request.getHashMap().get("field"),
                request.getHashMap().get("new value"));
        editSubCategory(request.getHashMap().get("category name"), request.getHashMap().get("field"),
                request.getHashMap().get("new value"), request.getHashMap());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void editSubCategory(String categoryName, String field, String changeValue, HashMap<String, String> reqInfo) throws CategoryDoesntExistException, CommonException, NotEnoughInformation {
        if (!Category.isThereSubCategory(categoryName)) throw new CategoryDoesntExistException();

        SubCategory subCategory = Category.getSubCategoryByName(categoryName);
        switch (field) {
            case "name" -> {
                if (Category.isThereCategory(changeValue) || changeValue.isEmpty())
                    throw new CommonException("There is category with this name!!");
                subCategory.setName(changeValue);
            }
            case "add property" -> addPropertyToSubCategory(subCategory, changeValue, reqInfo);
            case "remove property" -> removePropertyFromSubCategory(subCategory, changeValue);
            default -> throw new CommonException("You can't change this!!");
        }
        subCategory.updateDatabase();
    }

    private void addPropertyToSubCategory(SubCategory subCategory, String specialProperty, HashMap<String, String> reqInfo) throws NotEnoughInformation {
        if (!isThereProperty(subCategory, specialProperty)) return;
        String type = reqInfo.get("type");
        String unit = reqInfo.get("unit");
        if (type == null)
            throw new NotEnoughInformation();
        else if (type.equals("numeric") && unit == null)
            throw new NotEnoughInformation();
        else if (!(type.equals("text") || type.equals("numeric")))
            throw new NotEnoughInformation();

        switch (type) {
            case "numeric" -> subCategory.addNumericProperty(specialProperty, unit);
            case "text" -> subCategory.addTextProperty(specialProperty);
        }
    }


    private void removePropertyFromSubCategory(SubCategory subCategory, String specialProperty) {
        if (isThereProperty(subCategory, specialProperty))
            subCategory.removeSpecialProperties(specialProperty);
    }

}//end EditSubCategoryCommand class


class AddSubCategoryCommand extends ManageCategoriesCommand {
    private static AddSubCategoryCommand command;

    private AddSubCategoryCommand() {
        this.name = "add sub category";
    }

    public static AddSubCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new AddSubCategoryCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException, CommonException {
        containNullField(request.getHashMap().get("sub category name"), request.getHashMap().get("main category name"),
                request.getProperties());
        addSubCategory(request.getHashMap().get("sub category name"), request.getHashMap().get("main category name"),
                request.getProperties());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    public void addSubCategory(String subCategoryName, String mainCategoryName, ArrayList<SpecialProperty> specialProperties) throws CategoryDoesntExistException, CommonException {
        if (Category.isThereCategory(subCategoryName)) {
            throw new CommonException("There isn't any category with this name!!");
        } else {
            MainCategory mainCategory = Category.getMainCategoryByName(mainCategoryName);
            for (SpecialProperty specialProperty : mainCategory.getSpecialProperties())
                if (!specialProperties.contains(specialProperty)) specialProperties.add(specialProperty);

            SubCategory subCategory = new SubCategory();
            subCategory.setName(subCategoryName);
            subCategory.setMainCategory(mainCategory);
            subCategory.setSpecialProperties(specialProperties);
            mainCategory.addSubCategory(subCategory);
            mainCategory.updateDatabase();
            subCategory.updateDatabase();
        }
    }
}//end AddSubCategoryCommand class


class RemoveSubCategoryCommand extends ManageCategoriesCommand {
    private static RemoveSubCategoryCommand command;

    private RemoveSubCategoryCommand() {
        this.name = "remove sub category";
    }

    public static RemoveSubCategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new RemoveSubCategoryCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException {
        containNullField(request.getHashMap().get("sub category name"));
        removeSubCategory(request.getHashMap().get("sub category name"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void removeSubCategory(String subCategoryName) throws CategoryDoesntExistException {
        if (!Category.isThereSubCategory(subCategoryName)) throw new CategoryDoesntExistException();
        Category.removeSubCategory(subCategoryName);
    }

}//end RemoveSubCategoryCommand class
