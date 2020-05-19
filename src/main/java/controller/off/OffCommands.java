package controller.off;

import controller.Command;
import model.discount.Off;
import model.others.Filter;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;

public abstract class OffCommands extends Command {
    public static ShowOffCommand getShowOffCommand() {
        return ShowOffCommand.getInstance();
    }

    public static InitializePage getInitializePage() {
        return InitializePage.getInstance();
    }

    protected void setSort(String sortField, String sortDirection) {
        OffController.getInstance().setSort(sortField, sortDirection);
    }

    protected String sortField() {
        return OffController.getInstance().sortField();
    }

    protected String sortDirection() {
        return OffController.getInstance().sortDirection();
    }

    protected ArrayList<Filter> filters() {
        return OffController.getInstance().filters();
    }
}

class ShowOffCommand extends OffCommands {
    private static ShowOffCommand command;

    private ShowOffCommand() {
        this.name = "show offs offs";
    }

    public static ShowOffCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowOffCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        showOffs();
    }

    private void showOffs() {
        sendAnswer(Off.getAllProductsInOffsInfo(sortField(), sortDirection(), filters()), "off");
    }
}

class InitializePage extends OffCommands {
    private static InitializePage command;

    private InitializePage() {
        this.name = "initialize all offs page";
    }

    public static InitializePage getInstance() {
        if (command != null)
            return command;
        command = new InitializePage();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        initializeOffsPage();
    }

    private void initializeOffsPage() {
        resetSort();
        resetFilters();
        actionCompleted();
    }

    private void resetFilters() {
        OffController.getInstance().resetFilters();
    }


    private void resetSort() {
        setSort(null, null);
    }


}


