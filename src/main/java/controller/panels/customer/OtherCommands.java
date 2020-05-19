package controller.panels.customer;

import controller.Command;
import controller.Error;
import model.log.BuyLog;
import model.others.Product;
import model.send.receive.ClientMessage;
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

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Customer)) {
            sendError(Error.NEED_CUSTOMER.getError());
            return false;
        }
        return true;
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        viewBalance();
    }

    private void viewBalance() {
        sendAnswer(((Customer) getLoggedUser()).getMoney());
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        viewOrders();
    }

    public void viewOrders() {
        sendAnswer(((Customer) getLoggedUser()).getAllOrdersInfo(), "log");
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        viewOrder(request.getArrayList().get(0));
    }

    private void viewOrder(String orderId) {
        BuyLog buyLog = ((Customer) getLoggedUser()).getBuyLog(orderId);
        if (buyLog == null) {
            sendError("You don't have any order with this id!!");
        } else {
            sendAnswer(buyLog.getLogInfoForSending());
        }
    }
}


class RateCommand extends OtherCommands {
    private static RateCommand command;

    private RateCommand() {
        this.name = "show products in cart";
    }

    public static RateCommand getInstance() {
        if (command != null)
            return command;
        command = new RateCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstString()))
            return;
        rate(request.getFirstString(), request.getFirstInt());
    }

    private void rate(String productId, int score) {
        Product product = Product.getProductWithId(productId);
        if (score > 5 || score < 0) {
            sendError("Wrong score!!");
        } else if (product == null) {
            sendError("There isn't any product with this id!!");
        } else if (!((Customer) getLoggedUser()).doesUserBoughtProduct(product)) {
            sendError("You should buy this product to rate it!!");
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        viewUserDiscountCode();
    }

    private void viewUserDiscountCode() {
        sendAnswer(((Customer) getLoggedUser()).getAllDiscountCodeInfo(), "code");
    }

}