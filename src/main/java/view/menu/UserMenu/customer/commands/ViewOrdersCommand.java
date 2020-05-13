package view.menu.UserMenu.customer.commands;

import view.ViewAttributes;
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
        ArrayList<String> customerOrders = getCustomerOrdersFromController(ViewAttributes.getUsername());
        showCustomerOrders(customerOrders);
        menu.findSubMenuWithName("orders menu").autoExecute();
    }

    private void showCustomerOrders(ArrayList<String> customerOrders) {
    }

    private ArrayList<String> getCustomerOrdersFromController(String username) {
        return null;
    }
}
