package view.menu;

import view.command.Command;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {
    private static Scanner scanner;
    private Menu previousMenu;
    private String name;
    protected ArrayList<Command> menuCommands;
    protected ArrayList<Menu> subMenus;
    protected boolean isSignedIn;

    static {
        scanner = new Scanner(System.in);
    }
    public Menu(String name, Menu previousMenu) {
        this.previousMenu = previousMenu;
        this.name = name;
        menuCommands = new ArrayList<>();
        subMenus = new ArrayList<>();
        menuCommands = new ArrayList<>();
        isSignedIn = false;
        setSubMenus();
        addCommands();
    }

    public ArrayList<Command> getMenuCommands() {
        return menuCommands;
    }

    public String getName() {
        return name;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public abstract void execute();
    protected abstract void setSubMenus();
    protected abstract void addCommands();
    protected void printSubMenusWithNumber(){
        for (int i = 0; i < subMenus.size(); i++) {
            System.out.println(i+1 + ". " + subMenus.get(i).getName());
        }
    }
}
