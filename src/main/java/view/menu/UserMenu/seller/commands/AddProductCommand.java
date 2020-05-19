package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.HashMap;

public class AddProductCommand extends Command {
    public AddProductCommand(Menu menu) {
        super(menu);
        setSignature("add product");
        setRegex("^add product$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("add product");

        getProductInfo();
        getProductSpecialProperties();

        ViewToController.sendMessageToController();
    }

    private void getProductInfo() {
        HashMap<String, String> productInfo = new HashMap<>();

        OutputCommands.enterProductInfo();

        OutputCommands.enterName();
        productInfo.put("name", Menu.getInputCommandWithTrim());

        OutputCommands.enterPrice();
        productInfo.put("price", Menu.getInputCommandWithTrim());

        OutputCommands.enterNumberInStock();
        productInfo.put("number-in-stock", Menu.getInputCommandWithTrim());

        OutputCommands.enterCategory();
        productInfo.put("category", Menu.getInputCommandWithTrim());

        OutputCommands.enterDescription();
        productInfo.put("description", Menu.getInputCommandWithTrim());

        OutputCommands.enterSubCategory();
        productInfo.put("sub-category", Menu.getInputCommandWithTrim());

        OutputCommands.enterCompany();
        productInfo.put("company", Menu.getInputCommandWithTrim());

        ViewToController.setViewMessageFirstHashMapInputs(productInfo);
    }

    private void getProductSpecialProperties() {
        HashMap<String, String> specialProperties = new HashMap<>();

        OutputCommands.enterSpecialPropertyAndWriteFinishForFinish();
        String specialProperty = Menu.getInputCommandWithTrim();
        String specialPropertyValue;
        while (!specialProperty.equalsIgnoreCase("finish")) {
            OutputCommands.enterSpecialPropertyValue();

            specialPropertyValue = Menu.getInputCommandWithTrim();
            specialProperties.put(specialProperty, specialPropertyValue);

            specialProperty = Menu.getInputCommandWithTrim();
        }

        ViewToController.setViewMessageSecondHashMapInputs(specialProperties);
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
