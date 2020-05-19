package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

import java.util.HashMap;

public class PurchaseCart extends Command {
    public PurchaseCart(Menu menu) {
        super(menu);
        setSignature("purchase");
        setRegex("^purchase");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("purchase cart");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            getReceiverInformation();
            serverMessage = ViewToController.getServerMessage();
            if (serverMessage.getType().equals("Successful")) {
                getDiscountCode();
                serverMessage = ViewToController.getServerMessage();
                if (serverMessage.getType().equals("Successful")) {
                    //unsure
                } else {
                    System.out.println(serverMessage.getFirstString());
                }
            } else {
                System.out.println(serverMessage.getFirstString());
            }
        } else {
            System.out.println(serverMessage.getFirstString());
            if (serverMessage.getFirstString().equals("you should log in first")) {
                OutputComments.goingToLoginMenu();
                this.getMenu().findSubMenuWithName("login/register menu").autoExecute();
            } else {
                //unsure
            }
        }
    }

    private void getDiscountCode() {
        ViewToController.setViewMessage("using discount code");

        OutputQuestions.haveDiscountCode();
        String answer = Menu.getInputCommandWithTrim();
        if (answer.equalsIgnoreCase("yes")) {
            OutputCommands.enterDiscountCode();
            ViewToController.setFirstString(Menu.getInputCommandWithTrim());
            ViewToController.sendMessageToController();
        } else if(answer.equalsIgnoreCase("no")) {
            ViewToController.setFirstString("");
            ViewToController.sendMessageToController();
        } else {
            OutputErrors.invalidAnswer();
            getDiscountCode();
        }
    }

    private void getReceiverInformation() {
        HashMap<String, String> messageInputs = new HashMap<>();
        ViewToController.setViewMessage("get receiver information");

        OutputCommands.enterAddress();
        messageInputs.put("address", Menu.getInputCommandWithTrim());

        OutputCommands.enterPostalCode();
        messageInputs.put("postal-code", Menu.getInputCommandWithTrim());

        OutputCommands.enterPhoneNumber();
        messageInputs.put("phone-number", Menu.getInputCommandWithTrim());

        OutputCommands.enterOtherRequests();
        messageInputs.put("other-requests", Menu.getInputCommandWithTrim());

        ViewToController.setViewMessageFirstHashMapInputs(messageInputs);
        ViewToController.sendMessageToController();

    }
}
