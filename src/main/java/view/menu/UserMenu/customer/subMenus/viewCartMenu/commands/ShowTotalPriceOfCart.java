package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputComments;

public class ShowTotalPriceOfCart extends Command {
    public ShowTotalPriceOfCart(Menu menu) {
        super(menu);
        setSignature("show total price");
        setRegex("^show total price");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("show total price of cart");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            OutputComments.totalPrice();
            System.out.println(serverMessage.getNumber());
        } else {
            System.out.println(serverMessage.getFirstString());
        }

    }

}
