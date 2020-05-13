package view.menu.UserMenu.customer.subMenus.ordersMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class RateOrderCommand extends Command {
    public RateOrderCommand(Menu menu) {
        super(menu);
        setSignature("rate [productId] [1-5]");
        setRegex("^rate [^\\s]+ [12345]$");
    }

    @Override
    public void doCommand(String text) {
        String orderId = Arrays.asList(text.split("\\s")).get(2);
        int rateNumber = Integer.parseInt(Arrays.asList(text.split("\\s")).get(3));
        if (!ShowOrderCommand.checkIfOrderIdIsValidWithController(orderId)) {
            OutputErrors.invalidId();
        } else {
            rateOrder(orderId, rateNumber);
        }
    }

    private void rateOrder(String orderId, int rateNumber) {
    }
}
