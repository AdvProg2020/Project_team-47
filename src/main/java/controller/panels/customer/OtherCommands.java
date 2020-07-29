package controller.panels.customer;

import controller.Command;
import controller.Controller;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.common.NumberException;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.LogDoesntExist;
import model.ecxeption.user.UserTypeException;
import model.log.BuyLog;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;

import static controller.Controller.*;


public abstract class OtherCommands extends Command {
    public static ViewBalanceCommand getViewBalanceCommand() {
        return ViewBalanceCommand.getInstance();
    }

    public static ViewOrdersCommand getViewOrdersCommand() {
        return ViewOrdersCommand.getInstance();
    }

    public static ViewOrderCommand getViewOrderCommand() {
        return ViewOrderCommand.getInstance();
    }

    public static RateCommand getRateCommand() {
        return RateCommand.getInstance();
    }

    public static ViewCodesCommand getViewCodesCommand() {
        return ViewCodesCommand.getInstance();
    }

    public static RaiseMoneyCommand getRaiseMoneyCommand() {
        return RaiseMoneyCommand.getInstance();
    }

    protected void canUserDo() throws UserTypeException.NeedCustomerException {
        if (!(getLoggedUser() instanceof Customer)) {
            throw new UserTypeException.NeedCustomerException();
        }
    }
}

class ViewBalanceCommand extends OtherCommands {
    private static ViewBalanceCommand command;

    private ViewBalanceCommand() {
        this.name = "view balance customer";
    }

    public static ViewBalanceCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewBalanceCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException {
        canUserDo();
        return viewBalance();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewBalance() {
        return sendAnswer(((Customer) getLoggedUser()).getMoney());
    }

}

class RaiseMoneyCommand extends OtherCommands {
    private static RaiseMoneyCommand command;

    private RaiseMoneyCommand() {
        this.name = "raise_money \\d+";
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
        return viewBalance();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        //Controller.getLoggedUser()
    }

    private ServerMessage viewBalance() {
        return sendAnswer(((Customer) getLoggedUser()).getMoney());
    }

}


class ViewOrdersCommand extends OtherCommands {
    private static ViewOrdersCommand command;

    private ViewOrdersCommand() {
        this.name = "view orders";
    }

    public static ViewOrdersCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewOrdersCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException {
        canUserDo();
        return viewOrders();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    public ServerMessage viewOrders() {
        return sendAnswer(((Customer) getLoggedUser()).getAllOrdersInfo(), "log");
    }
}


class ViewOrderCommand extends OtherCommands {
    private static ViewOrderCommand command;

    private ViewOrderCommand() {
        this.name = "show order";
    }

    public static ViewOrderCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewOrderCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException, NullFieldException, LogDoesntExist {
        canUserDo();
        containNullField(request.getHashMap().get("order id"));
        return viewOrder(request.getHashMap().get("order id"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewOrder(String orderId) throws LogDoesntExist {
        BuyLog buyLog = ((Customer) getLoggedUser()).getBuyLog(orderId);
        if (buyLog == null) {
            throw new LogDoesntExist();
        } else {
            return sendAnswer(buyLog.getLogInfoForSending());
        }
    }
}


class RateCommand extends OtherCommands {
    private static RateCommand command;

    private RateCommand() {
        this.name = "score product";
    }

    public static RateCommand getInstance() {
        if (command != null)
            return command;
        command = new RateCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, UserTypeException.NeedCustomerException, ProductDoesntExistException, NumberException, CommonException {
        canUserDo();
        containNullField(request.getHashMap().get("product id"));
        rate(request.getHashMap().get("product id"), request.getFirstInt());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void rate(String productId, int score) throws ProductDoesntExistException, NumberException, CommonException {
        Product product = Product.getProductWithId(productId);
        if (score > 5 || score < 0) {
            throw new NumberException("Wrong score!!");
        } else if (!((Customer) getLoggedUser()).doesUserBoughtProduct(product)) {
            throw new CommonException("You should buy this product to rate it!!");
        } else {
            ((Customer) getLoggedUser()).rate(score, product);
        }
    }

}


class ViewCodesCommand extends OtherCommands {
    private static ViewCodesCommand command;

    private ViewCodesCommand() {
        this.name = "view discount codes customer";
    }

    public static ViewCodesCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewCodesCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException {
        canUserDo();
        return viewUserDiscountCode();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewUserDiscountCode() {
        return sendAnswer(((Customer) getLoggedUser()).getAllDiscountCodeInfo(), "code");
    }

}