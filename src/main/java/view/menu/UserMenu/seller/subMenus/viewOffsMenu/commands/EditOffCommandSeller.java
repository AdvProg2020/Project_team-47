package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;

import java.util.ArrayList;
import java.util.Arrays;

public class EditOffCommandSeller extends Command {
    public EditOffCommandSeller(Menu menu) {
        super(menu);
        setSignature("edit [offId]");
        setRegex("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ViewToController.setViewMessage("edit off");

        ArrayList<String> messageInputs = new ArrayList<>();
        String offId = Arrays.asList(text.split("\\s")).get(1);
        messageInputs.add(offId);

        OutputCommands.enterField();
        OutputComments.offFields();
        getField(messageInputs);

        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getField(ArrayList<String> messageInputs) {
        String field = Menu.getInputCommandWithTrim();
        messageInputs.add(field);

        if (field.equals("products")) {
            getProducts();

            OutputCommands.enterType();
            OutputComments.typesOfProductSpecialPropertyEditing();

            messageInputs.add(Menu.getInputCommandWithTrim());
        } else {
            messageInputs.add("");

            OutputCommands.enterNewValue();
            ViewToController.setViewMessageObject(Menu.getInputCommandWithTrim());
        }
    }

    private void getProducts() {
        ArrayList<String> products = new ArrayList<>();
        OutputCommands.enterProductsIdAndWriteFinishToFinish();

        String product = Menu.getInputCommandWithTrim();
        while (!product.equalsIgnoreCase("finish")) {
            products.add(product);
            product = Menu.getInputCommandWithTrim();
        }

        ViewToController.setViewMessageObject(products);
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
