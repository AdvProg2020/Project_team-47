package view.command;

import view.Menu;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class Command {
    private Menu menu;
    private String signature;
    private String regex;

    public Command(String signature, Menu menu, String regex) {
        this.menu = menu;
        this.signature = signature;
        this.regex = regex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public static boolean isInputCommandValid(String inputCommand, ArrayList<Command> commands) {
        for (Command command : commands) {
            if (command.isInputCommandThisCommand(inputCommand)){
                return true;
            }
        }
        return false;
    }

    private  boolean isInputCommandThisCommand(String inputCommand) {
        return Pattern.compile(this.regex).matcher(inputCommand).find();
    }

    public static Command findCommand(String inputCommand, ArrayList<Command> commands) {
        for (Command command : commands) {
            if (command.isInputCommandThisCommand(inputCommand)){
                return command;
            }
        }
        return null;
    }

    public abstract void doCommand();


}
