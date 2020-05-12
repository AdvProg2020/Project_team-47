package view.menu.User.customer.subMenus.viewCartMenu.subMenus;

import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

public class DiscountCodeInPurchasingCartMenu extends Menu {
    public DiscountCodeInPurchasingCartMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }
    public void manualExecute() {
        OutputQuestions.haveDiscountCode();
        String haveDiscountCode = getInputCommand();
        if (haveDiscountCode.equalsIgnoreCase("no")){
            new PaymentCartMenu("payment", this).manualExecute();
        } else if (haveDiscountCode.equalsIgnoreCase("yes")){
            getDiscountCode();

        } else {
            OutputErrors.invalidAnswer();
            manualExecute();
        }
    }

    private void getDiscountCode() {
        OutputCommands.enterDiscountCode();
        String discountCode = Menu.getInputCommand();
        if (!checkFromControllerIfDiscountCodeIsValid(discountCode)){
            OutputErrors.invalidDiscountCode();
            manualExecute();
        } else {
            getDiscountFromController(discountCode);
            OutputComments.DiscountAddSuccessful();
            new PaymentCartMenu("payment", this).manualExecute();
        }
    }

    private void getDiscountFromController(String discountCode) {
    }

    private boolean checkFromControllerIfDiscountCodeIsValid(String discountCode) {
        return true;
    }

}
