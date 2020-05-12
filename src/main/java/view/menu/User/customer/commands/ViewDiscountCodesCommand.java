package view.menu.User.customer.commands;

import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewDiscountCodesCommand extends Command {
    public ViewDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("view discount codes");
        setRegex("^view discount codes$");
    }

    @Override
    public void doCommand(String text) {
        ArrayList<String> discountCodes = new ArrayList<>();
        discountCodes = getCustomerDiscountCodes();
        printDiscountCodes(discountCodes);
    }

    private void printDiscountCodes(ArrayList<String> discountCodes) {
        for (String discountCode : discountCodes) {
            System.out.println(discountCode);
        }
    }

    private ArrayList<String> getCustomerDiscountCodes() {
        return null;
    }
}
