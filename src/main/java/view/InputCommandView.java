package view;

import view.menu.*;

import java.util.HashMap;
import java.util.Scanner;

public class InputCommandView extends View {
    private static HashMap<String, Menu> menuHashMap;
    private static Menu currentMenu;
    private static Scanner scanner;
    public static void run() {}

    private String getCommandWithTrim() {
        String command = scanner.nextLine();
        return command.trim();
    }
    private String getCommand() {
        return scanner.nextLine();
    }
}
