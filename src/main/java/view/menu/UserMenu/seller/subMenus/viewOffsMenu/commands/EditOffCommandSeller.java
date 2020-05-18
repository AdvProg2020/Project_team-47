package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

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
        String offId = Arrays.asList(text.split("\\s")).get(1);
        ArrayList<String> messageInput = new ArrayList<>();
        ViewToController.setViewMessage("edit off");

        messageInput.add(offId);


    }

    private void getAnswer() {
    }
}

/*private void sendMessage(String text) {
        ViewToController.setViewMessage("edit product");
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(productId);

        OutputCommands.enterField();
        OutputComments.productFields();
        getField(messageInputs);


        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getField(ArrayList<String> messageInputs) {
        String field = Menu.getInputCommandWithTrim();
        messageInputs.add(field);

        if (field.equals("special-property")) {
            getSpecialProperty();

            OutputCommands.enterType();
            OutputComments.typesOfProductSpecialPropertyEditing();

            messageInputs.add(Menu.getInputCommandWithTrim());
        } else {
            messageInputs.add("");

            OutputCommands.enterNewValue();
            ViewToController.setViewMessageObject(Menu.getInputCommandWithTrim());
        }
    }

    private void getSpecialProperty() {
        HashMap<String, String> specialProperty = new HashMap<>();
        OutputCommands.enterSpecialPropertyAndItsValueAndEnterKeyForFinish();

        String specialPropertyName = Menu.getInputCommandWithTrim();
        String specialPropertyValue;

        while (!specialPropertyName.equals("\n")) {
            specialPropertyValue = Menu.getInputCommandWithTrim();
            specialProperty.put(specialPropertyName, specialPropertyValue);

            OutputCommands.enterSpecialPropertyAndItsValueAndEnterKeyForFinish();
            specialPropertyName = Menu.getInputCommandWithTrim();
        }

        ViewToController.setViewMessageObject(specialProperty);
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
*/
