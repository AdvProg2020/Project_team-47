package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class EditMainCategoryCommand extends Command {
    public EditMainCategoryCommand(Menu menu) {
        super(menu);
        setSignature("edit main category [category]");
        setRegex("^edit main category [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String categoryName = Arrays.asList(text.split("\\s")).get(3);
        ViewToController.setViewMessage("edit main category");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(categoryName);
        OutputCommands.enterField();
        messageInputs.add(Menu.getInputCommandWithTrim());
        OutputCommands.enterNewValue();
        messageInputs.add(Menu.getInputCommandWithTrim());
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
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
