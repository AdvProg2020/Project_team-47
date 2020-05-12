package view.menu.User.customer.subMenus.viewCartMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class IncreaseProductInCart extends Command {
    public IncreaseProductInCart(Menu menu) {
        super(menu);
        setSignature("increase [productId]");
        setRegex("^increase [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String id = Arrays.asList(text.split("\\s")).get(1);
        if (!isIdInCart(id)) {
            OutputErrors.wrongId();
        } else {
            addProductToCartInController(id);
        }
    }

    static boolean isIdInCart(String id) {
        return true;
    }


    private void addProductToCartInController(String productId) {
    }
}
