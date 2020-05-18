package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;

public class DisableFilterCommand extends Command {
    public DisableFilterCommand(Menu menu) {
        super(menu);
        setSignature("disable filter [a selected filter]");
        setRegex("^disable filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }

    private void getAnswer() {
        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }
}
