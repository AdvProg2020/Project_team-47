package model.ecxeption;

public class DebugException extends Exception {
    public DebugException() {
        super("Shouldn't happen!!");
    }
}
