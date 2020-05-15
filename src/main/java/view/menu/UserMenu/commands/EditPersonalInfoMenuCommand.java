package view.menu.UserMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class EditPersonalInfoMenuCommand extends Command {
    public EditPersonalInfoMenuCommand(Menu menu) {
        super(menu);
        setSignature("edit [field]");
        setRegex("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String field = Arrays.asList(text.split("\\s")).get(1);
        OutputCommands.enterField();
        String fieldNewValue = Menu.getInputCommandWithTrim();
        ViewToController.setViewMessage("edit personal info");
        ArrayList<String> messageValue = new ArrayList<>();
        messageValue.add(field);
        messageValue.add(fieldNewValue);
        ViewToController.setViewMessageArrayListInputs(messageValue);
        ViewToController.sendMessageToController();
    }
}
