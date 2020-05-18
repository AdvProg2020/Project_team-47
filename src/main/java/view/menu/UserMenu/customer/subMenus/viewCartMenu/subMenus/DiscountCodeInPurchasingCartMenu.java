package view.menu.UserMenu.customer.subMenus.viewCartMenu.subMenus;

import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

public class DiscountCodeInPurchasingCartMenu extends Menu {
    public DiscountCodeInPurchasingCartMenu(Menu previousMenu) {
        super(previousMenu);
        setName("discount code in purchasing cart menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }
    public void manualExecute() {
        //todo
        OutputQuestions.haveDiscountCode();
        String haveDiscountCode = getInputCommandWithTrim();
        if (haveDiscountCode.equalsIgnoreCase("no")){
            new PaymentCartMenu(this).manualExecute();
        } else if (haveDiscountCode.equalsIgnoreCase("yes")){
            getDiscountCode();

        } else {
            OutputErrors.invalidAnswer();
            manualExecute();
        }
    }

    private void getDiscountCode() {
        OutputCommands.enterDiscountCode();
        String discountCode = Menu.getInputCommandWithTrim();
        if (!checkFromControllerIfDiscountCodeIsValid(discountCode)){
            OutputErrors.invalidDiscountCode();
            manualExecute();
        } else {
            getDiscountFromController(discountCode);
            OutputComments.DiscountAddSuccessful();
            new PaymentCartMenu(this).manualExecute();
        }
    }

    private void getDiscountFromController(String discountCode) {
    }

    private boolean checkFromControllerIfDiscountCodeIsValid(String discountCode) {
        return true;
    }

}
