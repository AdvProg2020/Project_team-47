package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveMainCategoryCommand extends Command {
    public RemoveMainCategoryCommand(Menu menu) {
        super(menu);
        setSignature("remove main category [category]");
        setRegex("^remove main category [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("successful")) {

        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void sendMessage(String text) {
        String categoryName = Arrays.asList(text.split("\\s")).get(3);
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(categoryName);
        ViewToController.setViewMessage("remove main category");
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }
}
