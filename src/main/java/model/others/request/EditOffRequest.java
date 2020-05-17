package model.others.request;

import controller.Controller;
import model.discount.Off;
import model.others.Product;
import model.send.receive.RequestInfo;

import java.util.ArrayList;

public class EditOffRequest extends MainRequest {
    private String offId;
    private String field;
    private String newValue;
    private ArrayList<String> newValueArrayList;

    public EditOffRequest() {
    }

    public EditOffRequest(String field, String newValue) {
        this.field = field;
        this.newValue = newValue;
    }

    public EditOffRequest(String field, ArrayList<String> newValueForProducts) {
        this.field = field;
        this.newValueArrayList = newValueForProducts;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setEditInfo(field, newValue, newValueArrayList);
    }

    @Override
    void accept(String type) {
        Off off = Off.getOffById(offId);
        switch (field) {
            case "start-time":
                editStartTime(off);
                break;
            case "finish-time":
                editFinishTime(off);
                break;
            case "percent":
                editPercent(off);
                break;
            case "products":
                editProducts(type, off);
                break;
        }
        off.setOffStatus("EDIT_ACCEPTED");
        off.updateDatabase();
    }

    @Override
    boolean update(String type) {
        if (!Off.isThereOff(offId))
            return false;
        switch (field) {
            case "start-time":
            case "finish-time":
            case "percent":
            case "products":
                return true;
        }
        return false;
    }

    @Override
    public void decline() {
        Off off = Off.getOffById(offId);
        off.setOffStatus("EDIT_DECLINED");
        off.updateDatabase();
    }

    private void editProducts(String type, Off off) {
        ArrayList<Product> products = new ArrayList<>();
        for (String id : newValueArrayList) {
            Product product = Product.getProductWithId(id);
            if (product != null) {
                products.add(product);
            }
        }
        switch (type) {
            case "edit-off append":
                appendProduct(off, products);
                break;
            case "edit-off replace":
                replaceProduct(off, products);
                break;
            case "edit-off remove":
                removeProduct(off, products);
                break;
        }
        updateOffDatabase(off, products);
    }

    private void updateOffDatabase(Off off, ArrayList<Product> products) {
        for (Product product : off.getProducts()) {
            product.updateDatabase();
        }
        for (Product product : products) {
            product.updateDatabase();
        }
    }

    private void removeProduct(Off off, ArrayList<Product> products) {
        for (Product product : products) {
            product.removeOff(off);
        }
        off.getProducts().removeAll(products);
    }

    private void replaceProduct(Off off, ArrayList<Product> products) {
        for (Product product : off.getProducts()) {
            product.removeOff(off);
        }
        off.setProducts(products);
        for (Product product : products) {
            product.addOff(off, off.getSeller());
        }
    }

    private void appendProduct(Off off, ArrayList<Product> products) {
        for (Product product : products) {
            if (!off.getProducts().contains(product)) {
                off.getProducts().add(product);
                product.addOff(off, off.getSeller());
            }
        }
    }

    private void editPercent(Off off) {
        off.setPercent(Integer.parseInt(newValue));
    }

    private void editFinishTime(Off off) {
        off.setFinishTime(Controller.getDateWithString(newValue));
    }

    private void editStartTime(Off off) {
        off.setStartTime(Controller.getDateWithString(newValue));
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

}
