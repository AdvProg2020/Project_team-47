package view;

import view.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    private Menu previousMenu;
    private ArrayList<Command> menuCommands;
    protected static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }
    public Menu(String name, Menu previousMenu) {
        this.name = name;
        this.previousMenu = previousMenu;
        menuCommands = new ArrayList<>();
        addCommands();
    }



    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public void execute(){
        String inputCommand = scanner.nextLine().trim();
        if (!Command.isInputCommandValid(inputCommand, menuCommands)) {
            SystemOutputMessages.invalidInputCommand();
        } else {
            Objects.requireNonNull(Command.findCommand(inputCommand, menuCommands)).doCommand();
        }
    }

    protected abstract void addCommands();


    public void help(){
        for (Command command : menuCommands) {
            System.out.println(command.getSignature());
        }
    }
}
