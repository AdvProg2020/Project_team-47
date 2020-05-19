package controller.panels.manager;

import controller.Command;
import controller.Error;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.send.receive.ClientMessage;
import model.user.Manager;

import java.util.ArrayList;

import static controller.Controller.*;
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

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Manager)) {
            sendError(Error.NEED_MANGER.getError());
            return false;
        }
        return true;
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        manageCategories(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void manageCategories(String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
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
    public void process(ClientMessage request) {
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)))
            return;
        editMainCategory(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2));
    }

    private void editMainCategory(String categoryName, String field, String changeValue) {
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
        mainCategory.updateDatabase();
    }

    private void removePropertyFromMainCategory(MainCategory mainCategory, String specialProperty) {
        mainCategory.removeSpecialProperties(specialProperty);
        actionCompleted();
    }

    private void addPropertyToMainCategory(MainCategory mainCategory, String specialProperty) {
        //adding properties to main categories
        if (!isThereProperty(mainCategory, specialProperty)) {
            mainCategory.addSpecialProperties(specialProperty);
        }

        //adding properties to sub categories
        for (Category subCategory : mainCategory.getSubCategories()) {
            if (!isThereProperty(mainCategory, specialProperty)) {
                subCategory.addSpecialProperties(specialProperty);
                subCategory.updateDatabase();
            }
        }

        actionCompleted();
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstString(), request.getArrayList()))
            return;
        addCategory(request.getFirstString(), request.getArrayList());
    }

    private void addCategory(String name, ArrayList<String> specialProperties) {
        if (Category.isThereCategory(name)) {
            sendError("There is a category with this name!!");
            return;
        }
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        removeMainCategory(request.getArrayList().get(0));
    }

    private void removeMainCategory(String categoryName) {
        if (!Category.isThereMainCategory(categoryName)) {
            sendError("There isn't any category with this name!!");
            return;
        }
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)))
            return;
        editSubCategory(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2));
    }

    private void editSubCategory(String categoryName, String field, String changeValue) {
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
        subCategory.updateDatabase();
    }

    private void addPropertyToSubCategory(SubCategory subCategory, String specialProperty) {
        if (!isThereProperty(subCategory, specialProperty)) {
            subCategory.addSpecialProperties(specialProperty);
        }
        actionCompleted();
    }

    private void removePropertyFromSubCategory(SubCategory subCategory, String specialProperty) {
        if (isThereProperty(subCategory, specialProperty))
            subCategory.removeSpecialProperties(specialProperty);

        actionCompleted();
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstString(), request.getSecondString(), request.getArrayList()))
            return;
        addSubCategory(request.getFirstString(), request.getSecondString(), request.getArrayList());
    }

    public void addSubCategory(String subCategoryName, String mainCategoryName, ArrayList<String> specialProperties) {
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getSecondString()))
            return;
        removeSubCategory(request.getSecondString());
    }

    private void removeSubCategory(String subCategoryName) {
        if (!Category.isThereSubCategory(subCategoryName)) {
            sendError("There isn't any subcategory with this name!!");
            return;
        }
        Category.removeSubCategory(subCategoryName);
    }

}//end RemoveSubCategoryCommand class
