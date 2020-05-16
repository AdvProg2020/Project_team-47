package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMainCategoryCommand extends Command {
    public AddMainCategoryCommand(Menu menu) {
        super(menu);
        setSignature("add main category [category]");
        setRegex("^add main category [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String categoryName = Arrays.asList(text.split("\\s")).get(3);
        ViewToController.setViewMessage("add main category");
        ViewToController.setFirstString(categoryName);

        ArrayList<String> messageInputs = getSpecialProperties();
        ViewToController.setViewMessageArrayListInputs(messageInputs);

        ViewToController.sendMessageToController();
    }

    private ArrayList<String> getSpecialProperties() {
        ArrayList<String> specialProperties = new ArrayList<>();
        //todo
        return specialProperties;
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("successful")) {

        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
