package view;

abstract public class View {
    protected static String controllerAnswer;
    public static void printError(){}
    public void receiveAnswer(String controllerAnswer) {
        View.controllerAnswer = controllerAnswer;
    }
    public static boolean answerContainError() {return true;}
}
