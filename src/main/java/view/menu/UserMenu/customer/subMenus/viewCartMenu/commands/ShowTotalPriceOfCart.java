package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowTotalPriceOfCart extends Command {
    public ShowTotalPriceOfCart(Menu menu) {
        super(menu);
        setSignature("show total price");
        setRegex("^show total price");
    }

    @Override
    public void doCommand(String text) {
        double cartPrice = getTotalPriceOFCartFromController();
        printCartPrice(cartPrice);
    }

    private void printCartPrice(double cartPrice) {
        System.out.println(cartPrice);
    }

    private double getTotalPriceOFCartFromController() {
        return 0.0;
    }
}
