package view.menu.UserMenu.manager.Commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;

import java.util.HashMap;

public class GiveGiftCommand extends Command {
    public GiveGiftCommand(Menu menu) {
        super(menu);
        setSignature("give gift");
        setRegex("^give gift$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("give gift");
        HashMap<String, String> messageInputs = new HashMap<>();

        OutputCommands.enterNumberOfUsers();
        String userNumberInput = Menu.getInputCommandWithTrim();

        if (!userNumberInput.matches("\\d+")) {
            OutputErrors.invalidInputCommand();
            return;
        }

        ViewToController.setFirstString(userNumberInput);

        OutputCommands.enterStartTime();
        messageInputs.put("start-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterFinishTime();
        messageInputs.put("finish-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterMaxUsableTime();
        messageInputs.put("max-usable-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterMaxDiscountAmount();
        messageInputs.put("max-discount-amount", Menu.getInputCommandWithTrim());

        OutputCommands.enterPercent();
        messageInputs.put("percent", Menu.getInputCommandWithTrim());

        ViewToController.setViewMessageFirstHashMapInputs(messageInputs);
        ViewToController.sendMessageToController();

        getAnswer();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            String discountCode = serverMessage.getFirstString();
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
