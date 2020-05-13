package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ShowProductsInCartCommand extends Command {
    public ShowProductsInCartCommand(Menu menu) {
        super(menu);
        setSignature("show products");
        setRegex("^show products$");
    }

    @Override
    public void doCommand(String text) {
        ArrayList<String> cartProducts = getCartProductsFromController();
        printAllProducts(cartProducts);
    }

    private void printAllProducts(ArrayList<String> cartProducts) {
        for (String cartProduct : cartProducts) {
            System.out.println(cartProduct);
        }
    }

    private ArrayList<String> getCartProductsFromController() {
        return null;
    }
}
