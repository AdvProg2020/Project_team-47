package view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

public class EditSellerProductCommand extends Command {
    public EditSellerProductCommand(Menu menu) {
        super(menu);
        setSignature("edit [productId]");
        setSignature("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        //todo
    }

    private void getAnswer() {
        //todo
    }
}
