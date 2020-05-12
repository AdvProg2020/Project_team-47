package view.menu.loginAndRegister;

import view.UserAttributes;
import view.command.HelpCommand;
import view.command.BackCommand;
import view.menu.Menu;
import view.menu.loginAndRegister.Commands.LoginCommand;
import view.menu.loginAndRegister.Commands.LogoutCommand;
import view.menu.loginAndRegister.Commands.RegisterCommand;

public class LoginAndRegisterMenu extends Menu {

    public LoginAndRegisterMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new RegisterCommand(this));
        menuCommands.add(new LoginCommand(this));
        menuCommands.add(new LogoutCommand(this));
        menuCommands.add(new BackCommand(this));
        menuCommands.add(new HelpCommand(this));
    }

}
