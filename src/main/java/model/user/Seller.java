package model.user;

import com.google.gson.Gson;
import model.discount.Off;
import model.log.Log;
import model.others.Date;
import model.others.Product;
import model.others.Sort;
import model.others.request.EditOffRequest;
import model.others.request.EditProductRequest;
import model.others.request.Request;
import model.send.receive.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Seller extends User {
    private String companyName;
    private String companyInfo;
    private ArrayList<Log> sellLogs;
    private ArrayList<Product> allProducts;
    private ArrayList<Off> allOff;
    private double money;
    private String address;


    public Seller() {
        super();
    }

    public Seller(HashMap<String, String> userInfo) {
        super(userInfo);
        this.companyInfo = userInfo.get("company-info");
        this.companyName = userInfo.get("company-name");
    }

    public String getAllSellLogsInfo(String sortField, String sortDirection) {
        ArrayList<Log> sellInfo = Sort.sortLogs(sortField, sortDirection, this.sellLogs);
        ArrayList<String> sellInfoArrayList = new ArrayList<>();
        assert sellInfo != null;
        for (Log log : sellInfo) {
            sellInfoArrayList.add(log.getLogInfoForSending());
        }
        return (new Gson()).toJson(sellInfoArrayList);
    }


    public String getAllProductInfo(String sortField, String sortDirection) {
        ArrayList<Product> products = Sort.sortProduct(sortField, sortDirection, this.allProducts);
        ArrayList<String> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            productsInfo.add(product.getProductInfo());
        }
        return (new Gson()).toJson(productsInfo);
    }


    public String getAllOffsInfo(String sortField, String sortDirection) {
        ArrayList<Off> offs = Sort.sortOffs(sortField, sortDirection, this.allOff);
        ArrayList<String> offsInfo = new ArrayList<>();
        assert offs != null;
        for (Off off : offs) {
            offsInfo.add(off.discountInfoForSending());
        }
        return (new Gson()).toJson(offsInfo);
    }

    public boolean sellerHasThisOff(String offId) {
        for (Off off : this.allOff) {
            if (off.getOffId().equals(offId)) {
                return true;
            }
        }
        return false;
    }

    public String getOff(String offId) {
        for (Off off : this.allOff) {
            if (off.getOffId().equals(offId)) {
                return off.discountInfoForSending();
            }
        }
        return null;
    }

    public void editProduct(String type, String field, Object newValue, Product product) {
        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType(type);
        EditProductRequest editProductRequest = new EditProductRequest();
        if (newValue instanceof HashMap) {
            HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
            editProductRequest.setNewValueHashMap(specialProperties);
        } else
            editProductRequest.setNewValue((String) newValue);
        editProductRequest.setField(field);
        editProductRequest.setProduct(product);
        editProductRequest.setSeller(this);
        request.setMainRequest(editProductRequest);
    }

    public void editOff(String offId, String field, Object newValue, String type) {
        Off off = Off.getOffById(offId);
        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType(type);
        EditOffRequest editOffRequest;
        if (newValue instanceof ArrayList) {
            ArrayList productIdArray = (ArrayList) newValue;
            ArrayList<String> productsId = new ArrayList<>();
            for (Object product : productIdArray) {
                productsId.add((String) product);
            }
            editOffRequest = new EditOffRequest(field, productsId);
        } else if (newValue instanceof String) {
            editOffRequest = new EditOffRequest(field, (String) newValue);
        } else
            editOffRequest = new EditOffRequest();
        editOffRequest.setOff(off);
        request.setMainRequest(editOffRequest);
    }


    public String getBuyers(Product product) {
        HashSet<Customer> buyers = new HashSet<>();
        for (Log sellLog : this.sellLogs) {
            if (sellLog.isThereProductInLog(product)) {
                buyers.add((Customer) sellLog.getCustomer());
            }
        }
        ArrayList<String> buyersInfo = new ArrayList<>();
        for (Customer buyer : buyers) {
            buyersInfo.add(buyer.userInfoForSending());
        }
        return (new Gson()).toJson(buyersInfo);
    }


    private void addSellLog(Log sellLog) {
        this.sellLogs.add(sellLog);
    }


    @Override
    public String userInfoForSending() {
        UserInfo user = new UserInfo();
        user.setCompanyInfo(this.companyInfo);
        user.setCompanyName(this.companyName);
        user.setEmail(this.getEmail());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setMoney(this.money);
        user.setPhoneNumber(this.getPhoneNumber());
        user.setUsername(this.getUsername());
        return (new Gson()).toJson(user);
    }

    public void removeProduct(Product product) {
        this.allProducts.remove(product);
        product.getSellers().remove(this);
        product.getPrice().remove(this);
        product.getNumberOfProductSellerHas().remove(this);
    }

    public void buy(Log sellLog) {
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public ArrayList<Log> getSellLogs() {
        return sellLogs;
    }

    public void setSellLogs(ArrayList<Log> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ArrayList<Off> getAllOff() {
        return allOff;
    }

    public void setAllOff(ArrayList<Off> allOff) {
        this.allOff = allOff;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
