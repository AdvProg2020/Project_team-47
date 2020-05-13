package view.menu.UserMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

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
        ViewToController.editField(field);
    }
}
