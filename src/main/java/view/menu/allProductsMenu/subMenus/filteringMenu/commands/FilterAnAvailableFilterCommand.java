package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import controller.Controller;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterAnAvailableFilterCommand extends Command {
    public FilterAnAvailableFilterCommand(Menu menu) {
        super(menu);
        setSignature("filter [an available filter]");
        setRegex("^filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(Arrays.asList(text.split("\\s")).get(1));

        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :
                ViewToController.setViewMessage("filter an available filter products");
                getProductsFilterMessageInputs(messageInputs);
                break;
            case "offs" :
                ViewToController.setViewMessage("filter an available filter offs");
                getOffsFilterMessageInputs(messageInputs);
                break;
        }

        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getOffsFilterMessageInputs(ArrayList<String> messageInputs) {
        if (messageInputs.get(0).equals("time") || messageInputs.get(0).equals("percent")) {

            OutputCommands.enterFirstFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());

            OutputCommands.enterSecondFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());

        } else {

            OutputCommands.enterFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());

            messageInputs.add("");
        }
    }

    private void getProductsFilterMessageInputs(ArrayList<String> messageInputs) {
        OutputCommands.enterFilterType();
        messageInputs.add(Menu.getInputCommandWithTrim());

        if (messageInputs.get(1).substring(0, 8).equals("equation")) {

            OutputCommands.enterFirstFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());

            OutputCommands.enterSecondFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());
        } else {
            OutputCommands.enterFilterValue();
            messageInputs.add(Menu.getInputCommandWithTrim());

            messageInputs.add("");
        }
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
