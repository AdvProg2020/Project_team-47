package view.menu.UserMenu.customer.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewOrdersCommand extends Command {
    public ViewOrdersCommand(Menu menu) {
        super(menu);
        setSignature("view orders");
        setRegex("^view orders$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController();
        if (ViewToController.getServerMessage().getType().equals("successful")) {
            menu.findSubMenuWithName("orders menu").autoExecute();
        }
    }

    private void sendMessageToViewToController() {
        ViewToController.setViewMessage("orders menu");
        ViewToController.sendMessageToController();
    }

    private void showCustomerOrders(ArrayList<String> customerOrders) {
    }

    private ArrayList<String> getCustomerOrdersFromController(String username) {
        return null;
    }
}
