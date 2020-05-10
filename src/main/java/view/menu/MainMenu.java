package view.menu;

import view.command.InsideCommand.HelpCommand;
import view.menu.User.UserPersonalInfoMenu;
import view.menu.allProducts.AllProductsMenu;
import view.menu.loginAndRegister.LoginAndRegisterMenu;
import view.menu.offs.AllOffsMenu;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputMessages;

public class MainMenu extends Menu{
    public MainMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    public void execute() {
        OutputMessages.welcomeCommand();
        OutputMessages.chooseMenu();
        printSubMenusWithNumber();
        System.out.print(subMenus.size() + ". ");
        OutputMessages.exit();
        while (true){
            int subMenuNumber = Integer.parseInt(Menu.getScanner().nextLine());
            goToInputMenu(subMenuNumber);
        }
    }

    private void goToInputMenu(int subMenuNumber) {
        if (subMenuNumber == subMenus.size()){
            System.exit(1);
        } else if (subMenuNumber == 2 && !isSignedIn){
            OutputErrors.notSignedIn();
            OutputMessages.enterInputAgain();
        } else {
            subMenus.get(subMenuNumber-1).execute();
        }
    }

    @Override
    protected void setSubMenus() {
        subMenus.add(new LoginAndRegisterMenu("login and register menu", this));
        subMenus.add(new UserPersonalInfoMenu("user personal info menu", this));
        subMenus.add(new AllProductsMenu("all products menu", this));
        subMenus.add(new AllOffsMenu("all offs menu", this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new HelpCommand(this));
    }

}
