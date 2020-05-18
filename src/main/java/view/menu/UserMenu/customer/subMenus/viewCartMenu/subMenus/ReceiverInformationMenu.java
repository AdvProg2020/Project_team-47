package view.menu.UserMenu.customer.subMenus.viewCartMenu.subMenus;

import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;

import java.util.regex.Pattern;

public class ReceiverInformationMenu extends Menu {
    public ReceiverInformationMenu(Menu previousMenu) {
        super(previousMenu);
        setName("receiver information menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }

    public void manualExecute(){
        //todo
        getReceiverAddress();
        getReceiverPhoneNumber();
        new DiscountCodeInPurchasingCartMenu(this).manualExecute();
    }


    private boolean isEmailValid(String email) {
        return Pattern.compile("^[^\\s]+@[^\\s]+\\.[^\\s]+$").matcher(email).find();
    }

    private void getReceiverPhoneNumber() {
        OutputCommands.enterPhoneNumber();
        String phoneNumber = Menu.getInputCommandWithTrim();
        if (!isPhoneNumberValid(phoneNumber)){
            OutputErrors.invalidPhoneNumber();
            getReceiverPhoneNumber();
        } else {
            sendPhoneNumberToController(phoneNumber);
        }
    }

    private void sendPhoneNumberToController(String phoneNumber) {
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }

    private void getReceiverAddress() {
        OutputCommands.enterAddress();
        String address = Menu.getInputCommandWithTrim();
        if (!isAddressValid(address)){
            OutputErrors.invalidAddress();
            getReceiverAddress();
        } else {
            sendAddressToController(address);
        }
    }

    private void sendAddressToController(String address) {
    }

    private boolean isAddressValid(String address) {
        return true;
    }
}
