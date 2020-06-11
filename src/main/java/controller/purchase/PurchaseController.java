package controller.purchase;

import controller.Command;
import controller.Controller;
import model.discount.DiscountCode;
import model.ecxeption.Exception;
import model.ecxeption.user.UserTypeException;
import model.log.BuyLog;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;

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
    public ServerMessage processRequest(ClientMessage request) throws Exception {
        if (!(loggedUser instanceof Customer))
            throw new UserTypeException.NeedCustomerException();
        return super.processRequest(request);
    }
}//end purchase controller class
