package view.menu.User.customer.subMenus.viewCartMenu.subMenus;

import view.menu.Menu;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputFailure;

public class PaymentCartMenu extends Menu {
    public PaymentCartMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }

    public void manualExecute(){
        if (!checkIfCanPayFromController()) {
            OutputFailure.unsuccessfulPayment();
        } else {
            payWithController();
            OutputComments.purchaseSuccessful();
            OutputComments.moneyDeducted();
        }
    }

    private void payWithController() {
    }

    private boolean checkIfCanPayFromController() {
        return true;
    }
}
