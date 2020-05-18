package view.menu.UserMenu.manager.Commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateDiscountCodesCommand extends Command {
    public CreateDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("create discount code");
        setRegex("^create discount code$");
    }

    @Override
    public void doCommand(String text) {
        getDiscountInformation();
        getControllerAnswer();
    }

    private void getControllerAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void getDiscountInformation() {
        HashMap<String, String> messageHashMapInputs = new HashMap<>();
        ArrayList<String> messageInputUsernames = new ArrayList<>();

        OutputCommands.enterStartTime();
        messageHashMapInputs.put("start-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterFinishTime();
        messageHashMapInputs.put("finish-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterMaxUsableTime();
        messageHashMapInputs.put("max-usable-time", Menu.getInputCommandWithTrim());

        OutputCommands.enterMaxDiscountAmount();
        messageHashMapInputs.put("max-discount-amount", Menu.getInputCommandWithTrim());

        OutputCommands.enterPercent();
        messageHashMapInputs.put("percent", Menu.getInputCommandWithTrim());

        OutputCommands.enterUsernamesTillEnterKey();
        String inputUsername = Menu.getInputCommandWithTrim();
        while (!inputUsername.equals("\n")) {
            messageInputUsernames.add(inputUsername);
            inputUsername = Menu.getInputCommandWithTrim();
        }

        sendMessageToViewToController(messageHashMapInputs, messageInputUsernames);
    }

    private void sendMessageToViewToController(HashMap<String, String> messageHashMapInputs,
                                               ArrayList<String> messageInputUsernames) {
        ViewToController.setViewMessage("create discount code");
        ViewToController.setViewMessageFirstHashMapInputs(messageHashMapInputs);
        ViewToController.setViewMessageArrayListInputs(messageInputUsernames);
        ViewToController.sendMessageToController();
    }


}
