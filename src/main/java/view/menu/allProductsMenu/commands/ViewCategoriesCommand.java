package view.menu.allProductsMenu.commands;

import model.send.receive.CategoryInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

import java.util.ArrayList;

public class ViewCategoriesCommand extends Command {
    public ViewCategoriesCommand(Menu menu) {
        super(menu);
        setSignature("view categories");
        setRegex("^view categories$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view categories");

        OutputQuestions.sortList();
        String answer = Menu.getInputCommandWithTrim();
        if (answer.equalsIgnoreCase("yes")) {
            OutputCommands.enterSortField();
            ViewToController.setFirstString(Menu.getInputCommandWithTrim());

            OutputCommands.enterSortDirection();
            ViewToController.setSecondString(Menu.getInputCommandWithTrim());

            ViewToController.sendMessageToController();
        } else if (answer.equalsIgnoreCase("no")) {
            ViewToController.setFirstString("");
            ViewToController.setSecondString("");

            ViewToController.sendMessageToController();
        } else {
            OutputErrors.invalidAnswer();
            sendMessage();
        }
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showCategories(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showCategories(ServerMessage serverMessage) {
        ArrayList<CategoryInfo> categoryInfoArrayList = serverMessage.getCategoryInfoArrayList();

        for (CategoryInfo categoryInfo : categoryInfoArrayList) {
            if (categoryInfo.getType().equals("main")) {
                System.out.println("name : " + categoryInfo.getName());
                System.out.println("sub categories : ");

                int index;
                for (String subCategory : categoryInfo.getSubCategories()) {
                    index = categoryInfo.getSubCategories().indexOf(subCategory) + 1;
                    System.out.println(index + ". " + subCategory);
                }
            }
        }

    }
}
