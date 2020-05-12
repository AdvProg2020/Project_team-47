package view.menu.User.customer.subMenus.viewCartMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class ViewProductInCart extends Command {
    public ViewProductInCart(Menu menu) {
        super(menu);
        setSignature("view [productId]");
        setRegex("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(1);
        if (!IncreaseProductInCart.isIdInCart(productId)){
            OutputErrors.wrongId();
        } else {
            goToProductMenu(productId);
        }
    }

    private void goToProductMenu(String productId) {
    }
}
