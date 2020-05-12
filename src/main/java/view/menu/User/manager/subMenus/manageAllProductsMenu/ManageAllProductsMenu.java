package view.menu.User.manager.subMenus.manageAllProductsMenu;

import view.menu.Menu;
import view.outputMessages.OutputErrors;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(String name, Menu previousMenu, Menu parentMenu) {
        super(name, previousMenu);
    }

    /*@Override
    public void execute() {
        String inputCommand = getInputCommand();
        if (!isInputCommandValid(inputCommand)){
            OutputErrors.invalidInputCommand();
            execute();
        } else {
            processInputCommand(inputCommand);
        }
    }*/

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        new RemoveProductCommand(this);
    }
}
