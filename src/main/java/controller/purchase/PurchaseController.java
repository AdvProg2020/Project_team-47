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
}//end purchase controller class
