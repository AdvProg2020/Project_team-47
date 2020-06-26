package model.others.request;

import controller.Controller;
import model.discount.Off;
import model.ecxeption.common.DateException;
import model.ecxeption.common.OffDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.send.receive.RequestInfo;

public class EditOffRequest extends MainRequest {
    private String offId;
    private String field;
    private String newValue;
    private String changeType;

    public EditOffRequest() {
    }

    public EditOffRequest(String field, String newValue) {
        this.field = field;
        this.newValue = newValue;
    }

    public EditOffRequest(String field, String newValue, String changeType) {
        this.field = field;
        this.newValue = newValue;
        this.changeType = changeType;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setEditInfo(field, newValue, changeType, offId);
    }

    @Override
    void accept() {
        Off off = null;
        try {
            off = Off.getOffById(offId);
        } catch (OffDoesntExistException e) {
            return;
        }
        switch (field) {
            case "start-time" -> editStartTime(off);
            case "finish-time" -> editFinishTime(off);
            case "percent" -> editPercent(off);
            case "products" -> editProducts(off);
        }
        off.setOffStatus("EDIT_ACCEPTED");
        off.updateDatabase();
    }

    @Override
    boolean update() {
        if (!Off.isThereOff(offId))
            return false;
        return switch (field) {
            case "start-time", "finish-time", "percent", "products" -> true;
            default -> false;
        };
    }

    @Override
    public void decline() {
        Off off = null;
        try {
            off = Off.getOffById(offId);
        } catch (OffDoesntExistException ignored) {
            return;
        }
        off.setOffStatus("EDIT_DECLINED");
        off.updateDatabase();
    }

    private void editProducts(Off off) {
        try {
            Product product = Product.getProductWithId(newValue);
            switch (changeType) {
                case "remove" -> off.removeProduct(product);
                case "add" -> off.addProduct(product);
            }
            product.updateDatabase();
        } catch (ProductDoesntExistException ignored) {
        }
    }

    private void editPercent(Off off) {
        off.setPercent(Integer.parseInt(newValue));
    }

    private void editFinishTime(Off off) {
        try {
            off.setFinishTime(Controller.getDateWithString(newValue));
        } catch (DateException e) {
            e.printStackTrace();
        }
    }

    private void editStartTime(Off off) {
        try {
            off.setStartTime(Controller.getDateWithString(newValue));
        } catch (DateException e) {
            e.printStackTrace();
        }
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

}
