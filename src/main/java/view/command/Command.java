package view.command;

import view.menu.Menu;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Command {
    protected Menu menu;
    private String signature;
    private String regex;

    public Command(Menu menu) {
        this.menu = menu;
    }

    public static boolean isInputCommandValid(String inputCommand, ArrayList<Command> commands) {
        for (Command command : commands) {
            if (command.isInputCommandThisCommand(inputCommand)) {
                return true;
            }
        }
        return false;
    }

    public static Command findCommand(String inputCommand, ArrayList<Command> commands) {
        for (Command command : commands) {
            if (command.isInputCommandThisCommand(inputCommand)) {
                return command;
            }
        }
        return null;
    }

    public static Command findCommandWithSignature(String inputCommand, ArrayList<Command> commands) {
        return commands.stream().filter(command -> command.getSignature()
                .equalsIgnoreCase(inputCommand)).findFirst().orElse(null);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Menu getMenu() {
        return menu;
    }

    private boolean isInputCommandThisCommand(String inputCommand) {
        return Pattern.compile(this.regex).matcher(inputCommand).find();
    }

    public abstract void doCommand(String text);


}
