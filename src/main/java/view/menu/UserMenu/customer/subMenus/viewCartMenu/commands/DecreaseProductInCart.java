package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class DecreaseProductInCart extends Command {
    public DecreaseProductInCart(Menu menu) {
        super(menu);
        setSignature("decrease [productId]");
        setRegex("^decrease [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String id = Arrays.asList(text.split("\\s")).get(1);
        if (!IncreaseProductInCart.isIdInCart(id)) {
            OutputErrors.wrongId();
        } else {
            removeProductFromCartInController(id);
        }
    }

    private void removeProductFromCartInController(String id) {
    }
}
