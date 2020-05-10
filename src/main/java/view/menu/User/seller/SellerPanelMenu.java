package view.menu.User.seller;

import view.command.InsideCommand.InsideCommand;
import view.menu.Menu;
import view.menu.User.UserPanelMenu;
import view.command.*;
import java.util.ArrayList;

public class SellerPanelMenu extends UserPanelMenu {
    public SellerPanelMenu(String name, Menu previousMenu) {
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
        ArrayList<Command> subCommands;
        menuCommands.add(new InsideCommand("help", this, null));

        upperCommand = new MenuToControllerCommand
                ("view personal info", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();
        subCommands.add(new MenuToControllerCommand("edit [field]", this, upperCommand, "[]"));
        upperCommand.setSubCommands(subCommands);

        menuCommands.add(new MenuToControllerCommand("view company information", this, null));

        menuCommands.add(new MenuToControllerCommand("view sales history", this, null));

        upperCommand = new MenuToControllerCommand
                ("view personal info", this, null);
        menuCommands.add(upperCommand);
        subCommands = upperCommand.getSubCommands();*/



    }
}
