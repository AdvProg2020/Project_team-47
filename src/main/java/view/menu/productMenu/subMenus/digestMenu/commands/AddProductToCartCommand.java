package view.menu.productMenu.subMenus.digestMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

public class AddProductToCartCommand extends Command {
    public AddProductToCartCommand(Menu menu) {
        super(menu);
        setSignature("add to cart");
        setRegex("^add to cart$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("add to cart");
        getSeller();
        ViewToController.sendMessageToController();
    }

    private void getSeller() {
        OutputCommands.enterSellerUsername();
        ViewToController.setFirstString(Menu.getInputCommandWithTrim());
    }

    private void getAnswer() {
    }
}
