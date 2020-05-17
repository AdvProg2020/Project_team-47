package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.HashMap;

public class AddOffCommandSeller extends Command {
    public AddOffCommandSeller(Menu menu) {
        super(menu);
        setSignature("add off");
        setRegex("^add off$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("add off");

        getOffInformation();
        getOffProductsId();

        ViewToController.sendMessageToController();
    }

    private void getOffInformation() {
        HashMap<String, String> offInformation = new HashMap<>();

        OutputCommands.enterStartTime();
        offInformation.put("start-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterFinishTime();
        offInformation.put("finish-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterPercent();
        offInformation.put("percent", Menu.getInputCommandWithTrim());

        ViewToController.setViewMessageFirstHashMapInputs(offInformation);
    }

    private void getOffProductsId() {
        ArrayList<String> productsId = new ArrayList<>();
        OutputCommands.enterProductsIdAndEnterKeyToFinish();

        String productId = Menu.getInputCommandWithTrim();
        while (!productId.equals("\n")) {
            productsId.add(productId);
            productId = Menu.getInputCommandWithTrim();
        }

        ViewToController.setViewMessageArrayListInputs(productsId);
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {

        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
