package view.menu.offsMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

public class ShowProductInOffCommand extends Command {
    public ShowProductInOffCommand(Menu menu) {
        super(menu);
        setSignature("show product [productId]");
        setRegex("^show product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(2);
        ViewToController.goToProductPage(productId);
        this.getMenu().findSubMenuWithName("product menu").autoExecute();
    }
}
