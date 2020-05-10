package view.menu.loginAndRegister;

import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputMessages;

public class LoginAndRegisterMenu extends Menu {
    private static boolean isManagerRegistered;

    public LoginAndRegisterMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
        isManagerRegistered = false;
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }

    @Override
    public void execute() {
        /*String inputCommand;
        while (true) {
            inputCommand = Menu.getScanner().nextLine().trim();
            if (!Command.isInputCommandValid(inputCommand, menuCommands)) {
                OutputErrors.invalidInputCommand();
            } else {
                Command.findCommand(inputCommand, menuCommands).doCommand();
            }


        }*/
    }
}
