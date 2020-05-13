package view.menu.UserMenu.customer.subMenus.ordersMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class ShowOrderCommand extends Command {
    public ShowOrderCommand(Menu menu) {
        super(menu);
        setSignature("show order [orderId]");
        setRegex("^show order [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String orderId = Arrays.asList(text.split("\\s")).get(2);
        if (!checkIfOrderIdIsValidWithController(orderId)) {
            OutputErrors.invalidId();
        } else {
            showOrderDetails(orderId);
        }
    }

    private void showOrderDetails(String orderId) {
    }

    static boolean checkIfOrderIdIsValidWithController(String orderId) {
        return true;
    }
}
