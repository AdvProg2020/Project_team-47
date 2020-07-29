package model.user;

import bank.StoreToBankConnection;
import controller.Controller;
import database.UserData;
import model.discount.Off;
import model.ecxeption.common.DateException;
import model.ecxeption.common.OffDoesntExistException;
import model.log.Log;
import model.others.Product;
import model.others.Sort;
import model.others.request.AddOffRequest;
import model.others.request.Request;
import model.send.receive.LogInfo;
import model.send.receive.OffInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends User {
    private final ArrayList<Log> sellLogs;
    private final ArrayList<Product> allProducts;
    private final ArrayList<Off> allOff;
    private String companyName;
    private String companyInfo;
    private double money;


    public Seller() {
        super();
        sellLogs = new ArrayList<>();
        allProducts = new ArrayList<>();
        allOff = new ArrayList<>();
    }


    public Seller(HashMap<String, String> userInfo, byte[] avatar) {
        super(userInfo, avatar);
        this.companyInfo = userInfo.get("company-info");
        this.companyName = userInfo.get("company-name");
        sellLogs = new ArrayList<>();
        allProducts = new ArrayList<>();
        allOff = new ArrayList<>();
        this.money = 0;
        try {
            StoreToBankConnection.getInstance().createAccount(this.getFirstName()
                    , this.getLastName(), this.getUsername()
                    , this.getPassword(), this.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser() {
        allUsers.remove(this);
        for (Product product : this.allProducts) {
            product.removeSellerFromProduct(this);
        }
        for (Off off : allOff) {
            off.removeOff();
        }
        this.removeFromDatabase();
    }

    public ArrayList<LogInfo> getAllSellLogsInfo(String sortField, String sortDirection) {
        ArrayList<Log> sellLogs = Sort.sortLogs(sortField, sortDirection, this.sellLogs);
        ArrayList<LogInfo> sellInfo = new ArrayList<>();
        assert sellLogs != null;
        for (Log log : sellLogs) {
            sellInfo.add(log.getLogInfoForSending());
        }
        return sellInfo;
    }

    public Off getOffById(String id) throws OffDoesntExistException {
        for (Off off : allOff) {
            if (id.equals(off.getOffId()))
                return off;
        }
        throw new OffDoesntExistException();
    }

    public Product getProductById(String id) {
        for (Product product : this.allProducts) {
            if (id.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }

    public void increaseMoney(double money) {
        this.money += money;
    }

    public boolean hasProduct(String id) {
        for (Product product : this.allProducts) {
            if (id.equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasProduct(Product product) {
        return allProducts.contains(product);
    }

    public ArrayList<ProductInfo> getAllProductInfo(String sortField, String sortDirection) {
        ArrayList<Product> products = Sort.sortProduct(sortField, sortDirection, this.allProducts);
        ArrayList<ProductInfo> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            productsInfo.add(product.getProductInfo());
        }
        return productsInfo;
    }

    public ArrayList<OffInfo> getAllOffsInfo(String sortField, String sortDirection) {
        ArrayList<Off> offs = Sort.sortOffs(sortField, sortDirection, this.allOff);
        ArrayList<OffInfo> offsInfo = new ArrayList<>();
        assert offs != null;
        for (Off off : offs) {
            offsInfo.add(off.offInfo());
        }
        return offsInfo;
    }

    public boolean sellerHasThisOff(String offId) {
        for (Off off : this.allOff) {
            if (off.getOffId().equals(offId)) {
                return true;
            }
        }
        return false;
    }

    public OffInfo getOffInfo(String offId) {
        for (Off off : this.allOff) {
            if (off.getOffId().equals(offId)) {
                return off.offInfo();
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        this.allProducts.add(product);
    }

    public void addOff(Off off) {
        this.allOff.add(off);
    }

    public void addOff(HashMap<String, String> offInfo, ArrayList<String> productsId) throws DateException {
        //this function will create a request to adding a new off
        Request request = new Request();
        AddOffRequest addOffRequest = new AddOffRequest();
        request.setRequestSender(this);
        request.setType("add-off");
        request.setMainRequest(addOffRequest);
        addOffRequest.setFinishTime(Controller.getDateWithString(offInfo.get("finish-time")));
        addOffRequest.setStartTime(Controller.getDateWithString(offInfo.get("start-time")));
        addOffRequest.setPercent(Integer.parseInt(offInfo.get("percent")));
        addOffRequest.setProductsId(productsId);
        addOffRequest.setSellerUsername(this.getUsername());

        request.addToDatabase();
    }

    public ArrayList<UserInfo> getBuyers(Product product) {
        ArrayList<UserInfo> buyers = new ArrayList<>();
        for (Log sellLog : this.sellLogs) {
            if (sellLog.isThereProductInLog(product.getId())) {
                buyers.add(sellLog.getCustomer());
            }
        }
        return buyers;
    }

    public void addSellLog(Log sellLog) {
        this.sellLogs.add(sellLog);
    }

    @Override
    public UserInfo userInfoForSending() {
        UserInfo user = new UserInfo();
        userInfoSetter(user);
        user.setType("seller");
        user.setCompanyInfo(this.companyInfo);
        user.setCompanyName(this.companyName);
        user.setMoney(this.money);
        return user;
    }

    @Override
    public UserData updateDatabase() {
        UserData user = new UserData("seller");
        super.updateDatabase(user);
        user.setCompanyName(this.companyName);
        user.setCompanyInfo(this.companyInfo);
        user.setMoney(this.money);
        addOffsToDatabase(user);
        addLogToDatabase(user);
        addProductsToDatabase(user);
        return user;
    }

    private void addProductsToDatabase(UserData user) {
        for (Product product : allProducts) {
            user.addProduct(product.getId());
        }
    }

    private void addLogToDatabase(UserData user) {
        for (Log sellLog : sellLogs) {
            user.addLog(sellLog.getLogId());
        }
    }

    private void addOffsToDatabase(UserData user) {
        for (Off off : allOff) {
            user.addOff(off.getOffId());
        }
    }

    public void removeProduct(Product product) {
        this.allProducts.remove(product);
        this.updateDatabase().update();
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

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
