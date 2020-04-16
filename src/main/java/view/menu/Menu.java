package view.menu;

import java.util.HashMap;
import java.util.regex.Matcher;

abstract public class Menu {
    protected static HashMap<String,String> commandRegexHashMap;
    private static boolean logged;
    abstract public void execute(String command);
    abstract  void commandProcess(String command);
    public static void help(){}
    public static Matcher getMatcher(String regex, String text){return null;}
    public static boolean isMatched(String regex,String text){return true;}

    public Menu() {
    }
}
