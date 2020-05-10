package view.menu.User.manager;

import view.command.InsideCommand.InsideCommand;
import view.menu.Menu;
import view.menu.User.UserPanelMenu;
import view.command.*;

import java.util.ArrayList;

public class ManagerPanelMenu extends UserPanelMenu {
    public ManagerPanelMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    public void execute() {

    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        /*Command upperCommand;
        Command upperCommand1;
        ArrayList<Command> subCommands;
        menuCommands.add(new InsideCommand("help", this, null));

        upperCommand = new MenuToControllerCommand
                ("view personal info", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("edit [field]", this, upperCommand, "[]"));
        upperCommand.setSubCommands(subCommands);

        upperCommand = new MenuToControllerCommand
                ("manage users", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("view [username]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("delete user [username]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("create manager profile", this, upperCommand));
        upperCommand.setSubCommands(subCommands);

        upperCommand = new MenuToControllerCommand
                ("manage all products", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("remove [productId]", this, upperCommand, "[]"));
        upperCommand.setSubCommands(subCommands);

        menuCommands.add(new MenuToControllerCommand
                ("create discount code", this, null));

        upperCommand = new MenuToControllerCommand
                ("manage users", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("view discount code [code]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("edit discount code[code]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("remove discount code[code]", this, upperCommand, "[]"));
        upperCommand.setSubCommands(subCommands);

        upperCommand = new MenuToControllerCommand
                ("manage requests", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();

        upperCommand1 = new MenuToControllerCommand
                ("manage requests", this, upperCommand);

        subCommands.add(upperCommand1);
        upperCommand.setSubCommands(subCommands);

        subCommands = upperCommand1.getSubCommands();
        subCommands.add(new MenuToControllerCommand("details [requestId]", this, upperCommand1, "[]"));
        subCommands.add(new MenuToControllerCommand("accept [requestId]", this, upperCommand1, "[]"));
        subCommands.add(new MenuToControllerCommand("decline [requestId]", this, upperCommand1, "[]"));
        upperCommand1.setSubCommands(subCommands);

        upperCommand = new MenuToControllerCommand
                ("manage categories", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("edit [category]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("add [category]", this, upperCommand, "[]"));
        subCommands.add(new MenuToControllerCommand("remove [category]", this, upperCommand, "[]"));
        upperCommand.setSubCommands(subCommands);*/
    }
}
