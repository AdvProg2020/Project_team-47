package view.menu.User.commands;

import view.command.Command;
import view.menu.Menu;

public class LoginAndRegisterCommandForUser extends Command {
    public LoginAndRegisterCommandForUser(Menu menu) {
        super(menu);
        setSignature("login/register");
        setRegex("^login/register$");
    }

    @Override
    public void doCommand(String text) {
        menu.findSubMenuWithName("login/register menu").autoExecute();
    }
}
