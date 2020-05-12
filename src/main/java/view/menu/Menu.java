package view.menu;

import view.command.Command;
import view.outputMessages.OutputErrors;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {
    private static Scanner scanner;
    private Menu previousMenu;
    private String name;
    protected ArrayList<Command> menuCommands;
    protected ArrayList<Menu> subMenus;


    static {
        scanner = new Scanner(System.in);
    }
    public Menu(String name, Menu previousMenu) {
        this.previousMenu = previousMenu;
        this.name = name;
        menuCommands = new ArrayList<>();
        subMenus = new ArrayList<>();
        menuCommands = new ArrayList<>();
        setSubMenus();
        addCommands();
    }

    public ArrayList<Command> getMenuCommands() {
        return menuCommands;
    }

    public ArrayList<Menu> getSubMenus() {
        return subMenus;
    }

    public Menu getPreviousMenu() {
        return previousMenu;
    }

    public String getName() {
        return name;
    }

    public void autoExecute(){
        String inputCommand;
        while (true) {
            inputCommand = getInputCommand();
            if (!isInputCommandValid(inputCommand)) {
                System.out.println("salam");
                OutputErrors.invalidInputCommand();
            } else {
                processInputCommand(inputCommand).doCommand(inputCommand);
            }
        }
    }
    public Command processInputCommand(String inputCommand){
        for (Command menuCommand : menuCommands) {
            if (Pattern.compile(menuCommand.getRegex()).
                    matcher(inputCommand).find()){
                return menuCommand;
            }
        }
        return null;
    }
    public boolean isInputCommandValid(String inputCommand){
        for (Command menuCommand : menuCommands) {
            if (Pattern.compile(menuCommand.getRegex()).
                    matcher(inputCommand).find()){
                return true;
            }
        }
        return false;
    }
    protected abstract void setSubMenus();
    protected abstract void addCommands();
    public Menu findSubMenuWithName(String name){
        return subMenus.stream().filter(subMenu -> subMenu.getName()
                .equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public static String getInputCommand(){
        return scanner.nextLine().trim();
    }
}
