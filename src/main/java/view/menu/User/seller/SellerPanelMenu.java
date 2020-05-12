package view.menu.User.seller;

import view.command.BackCommand;
import view.command.ExitCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.User.commands.ViewPersonalInfoCommand;
import view.menu.User.seller.commands.ManageProductsCommandsSeller;
import view.menu.User.seller.commands.ViewCompanyInformationCommand;
import view.menu.User.seller.commands.ViewSalesHistoryCommand;

public class SellerPanelMenu extends Menu {
    public SellerPanelMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }


    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        new ViewPersonalInfoCommand(this);
        new ViewCompanyInformationCommand(this);
        new ViewSalesHistoryCommand(this);
        new ManageProductsCommandsSeller(this);
        new ExitCommand(this);
        new HelpCommand(this);
    }
}
