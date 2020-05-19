package controller.purchase;

import controller.Command;
import controller.Controller;
import model.discount.DiscountCode;
import model.log.BuyLog;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class PurchaseController extends Controller {
    private static PurchaseController purchaseController;
    private ArrayList<Command> commands;

    private PurchaseController() {
        commands = new ArrayList<>();
        commands.add(PurchaseCommands.getPurchaseInitializerCommand());
        commands.add(PurchaseCommands.getPurchaseInformationGetter());
        commands.add(PurchaseCommands.getApplyDiscountCodeCommand());
        commands.add(PurchaseCommands.getPayCommand());
    }

    public static PurchaseController getInstance() {
        if (purchaseController != null)
            return purchaseController;
        purchaseController = new PurchaseController();
        return purchaseController;
    }


    void resetPurchasePage() {
        setPurchaseLog(new BuyLog());
        setCode(null);
    }

    BuyLog purchaseLog() {
        return loggedUser.getPurchaseLog();
    }

    DiscountCode purchaseCode() {
        return loggedUser.getPurchaseCode();
    }

    void setCode(DiscountCode discountCode) {
        loggedUser.setPurchaseCode(discountCode);
    }

    private void setPurchaseLog(BuyLog purchaseLog) {
        loggedUser.setPurchaseLog(purchaseLog);
    }

    @Override
    public void processRequest(ClientMessage request) {
        for (Command command : commands) {
            if (command.canDoIt(request.getRequest())) {
                command.process(request);
                return;
            }
        }
    }

    @Override
    public boolean canProcess(String request) {
        for (Command command : commands) {
            if (command.canDoIt(request))
                return true;
        }
        return false;
    }
}//end purchase controller class
