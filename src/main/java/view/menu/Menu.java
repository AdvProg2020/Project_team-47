package view.menu;

import view.command.Command;
import view.outputMessages.OutputErrors;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    protected ArrayList<Command> menuCommands;
    protected ArrayList<Menu> subMenus;
    private Menu previousMenu;
    private String name;

    public Menu(Menu previousMenu) {
        this.previousMenu = previousMenu;
        menuCommands = new ArrayList<>();
        subMenus = new ArrayList<>();
        menuCommands = new ArrayList<>();
        setSubMenus();
        addCommands();
    }

    public static String getInputCommandWithTrim() {
        return scanner.nextLine().trim();
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

    public void setName(String name) {
        this.name = name;
    }

    public void autoExecute() {
        String inputCommand;

        while (true) {
            inputCommand = getInputCommandWithTrim();
            if (!isInputCommandValid(inputCommand)) {
                OutputErrors.invalidInputCommand();
            } else {
                processInputCommand(inputCommand).doCommand(inputCommand);
            }
        }
    }

    public Command processInputCommand(String inputCommand) {
        for (Command menuCommand : menuCommands) {
            if (Pattern.compile(menuCommand.getRegex()).
                    matcher(inputCommand).find()) {
                return menuCommand;
            }
        }
        return null;
    }

    public boolean isInputCommandValid(String inputCommand) {
        for (Command menuCommand : menuCommands) {
            if (Pattern.compile(menuCommand.getRegex()).
                    matcher(inputCommand).find()) {
                return true;
            }
        }
        return false;
    }

    protected abstract void setSubMenus();

    protected abstract void addCommands();

    public Menu findSubMenuWithName(String name) {
        return subMenus.stream().filter(subMenu -> subMenu.getName()
                .equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
