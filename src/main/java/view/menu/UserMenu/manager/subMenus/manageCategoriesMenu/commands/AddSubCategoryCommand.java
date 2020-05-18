package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class AddSubCategoryCommand extends Command {
    public AddSubCategoryCommand(Menu menu) {
        super(menu);
        setSignature("add sub category [category]");
        setRegex("^add sub category [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String subCategoryName = Arrays.asList(text.split("\\s")).get(3);
        ViewToController.setViewMessage("add sub category");
        ViewToController.setFirstString(subCategoryName);

        OutputCommands.enterMainCategoryName();
        ViewToController.setSecondString(Menu.getInputCommandWithTrim());

        ArrayList<String> messageInputs = getSpecialProperties();
        ViewToController.setViewMessageArrayListInputs(messageInputs);

        ViewToController.sendMessageToController();
    }

    private ArrayList<String> getSpecialProperties() {
        OutputCommands.enterSpecialPropertyAndEnterKeyForFinish();
        ArrayList<String> specialProperties = new ArrayList<>();

        String specialProperty = Menu.getInputCommandWithTrim();
        while (!specialProperty.equals("\n")) {
            specialProperties.add(specialProperty);
            specialProperty = Menu.getInputCommandWithTrim();
        }

        return specialProperties;
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
