package view.menu.User.customer.subMenus.viewCartMenu.subMenus;

import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;

import java.util.regex.Pattern;

public class ReceiverInformationMenu extends Menu {
    public ReceiverInformationMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {

    }

    public void manualExecute(){
        getReceiverAddress();
        getReceiverPhoneNumber();
        getReceiverEmail();
        new DiscountCodeInPurchasingCartMenu("discount code", this).manualExecute();
    }

    private void getReceiverEmail() {
        OutputCommands.enterEmail();
        String email = Menu.getInputCommand();
        if (!isEmailValid(email)){
            OutputErrors.invalidEmail();
            getReceiverEmail();
        } else {
            sendEmailToController(email);
        }
    }

    private void sendEmailToController(String email) {
    }

    private boolean isEmailValid(String email) {
        return Pattern.compile("^[^\\s]+@[^\\s]+\\.[^\\s]+$").matcher(email).find();
    }

    private void getReceiverPhoneNumber() {
        OutputCommands.enterPhoneNumber();
        String phoneNumber = Menu.getInputCommand();
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
        String address = Menu.getInputCommand();
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
