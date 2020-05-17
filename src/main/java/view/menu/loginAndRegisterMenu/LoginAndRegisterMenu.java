package view.menu.loginAndRegisterMenu;

import view.command.HelpCommand;
import view.command.BackCommand;
import view.menu.Menu;
import view.menu.loginAndRegisterMenu.Commands.LoginCommand;
import view.menu.loginAndRegisterMenu.Commands.LogoutCommand;
import view.menu.loginAndRegisterMenu.Commands.RegisterCommand;

public class LoginAndRegisterMenu extends Menu {

    public LoginAndRegisterMenu(Menu previousMenu) {
        super(previousMenu);
        setName("login/register menu");
    }

    @Override
    protected void setSubMenus() {
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new RegisterCommand(this));
        menuCommands.add(new LoginCommand(this));
        menuCommands.add(new LogoutCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }

}
