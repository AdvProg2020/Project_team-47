package controller.bankRelated;

import bank.StoreToBankConnection;
import controller.Command;
import controller.Controller;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.User;

import java.io.IOException;

public abstract class BankRelatedCommands extends Command {
    public static RaiseMoneyCommand getRaiseMoneyCommand() {
        return RaiseMoneyCommand.getInstance();
    }
    public static LowerWalletMoneyCommand getLowerWalletMoneyCommand() {
        return LowerWalletMoneyCommand.getInstance();
    }

}

class RaiseMoneyCommand extends BankRelatedCommands {

    private static RaiseMoneyCommand command;

    private RaiseMoneyCommand() {
        this.name = "raise_balance \\d+";
    }

    public static RaiseMoneyCommand getInstance() {
        if (command != null)
            return command;
        command = new RaiseMoneyCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        User user = Controller.getLoggedUser();
        try {
            ServerMessage answer = StoreToBankConnection.getInstance()
                    .getToken(user.getUsername(), user.getPassword());
            if (answer.getType().equals("Successful")) {
                user.setToken(answer.getToken());
            } else {
                System.out.println("error in getting token");
            }

            answer = StoreToBankConnection.getInstance()
                    .createReceipt("" + user.getToken().getId()
                    , "move", request.getType().split("\\s")[1]
                    , user.getUsername(), Controller.SHOP_NAME);
            if (answer.getType().equals("Error")) {
                System.out.println("error in creating receipt");
            }
            answer = StoreToBankConnection.getInstance().pay("" + answer.getReceipt().getReceiptId());
            if (answer.getType().equals("Error")) {
                System.out.println("error in paying");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Controller.actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        int money = Integer.parseInt(request.getType().split("\\s")[1]);
        if (money <= 0) {
            throw new Exception("money should be positive");
        }
    }
}

class LowerWalletMoneyCommand extends BankRelatedCommands {

    private static LowerWalletMoneyCommand command;

    private LowerWalletMoneyCommand() {
        this.name = "lower_wallet_money \\d+";
    }

    public static LowerWalletMoneyCommand getInstance() {
        if (command != null)
            return command;
        command = new LowerWalletMoneyCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        System.out.println("lower money command found");
        checkPrimaryErrors(request);
        User user = Controller.getLoggedUser();
        try {
            ServerMessage answer = StoreToBankConnection.getInstance()
                    .getToken(user.getUsername(), user.getPassword());
            if (answer.getType().equals("Successful")) {
                user.setToken(answer.getToken());
            } else {
                System.out.println("error in getting token");
            }
            answer = StoreToBankConnection.getInstance()
                    .createReceipt("" + user.getToken().getId()
                            , "move", request.getType().split("\\s")[1]
                            , Controller.SHOP_NAME, user.getUsername());
            if (answer.getType().equals("Successful")) {
                answer = StoreToBankConnection.getInstance().pay("" + answer.getReceipt().getReceiptId());
                if (answer.getType().equals("Error")) {
                    System.out.println("error in paying");
                }

            } else {
                System.out.println("error in creating receipt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Controller.actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        int money = Integer.parseInt(request.getType().split("\\s")[1]);
        System.out.println("money amount is : " + money);
        if (money <= 0) {
            throw new Exception("money should be positive");
        }

        if (money > Controller.getLoggedUser().getAllowedMoney()) {
            System.out.println("this amount of money is not allowed");
            throw new Exception("this amount of money is not allowed");
        }
    }
}
