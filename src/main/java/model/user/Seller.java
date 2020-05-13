package model.user;

import model.discount.Off;
import model.log.Log;
import model.others.Date;
import model.others.Product;
import model.others.Sort;
import model.others.request.*;
import model.send.receive.LogInfo;
import model.send.receive.OffInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends User {
    private String companyName;
    private String companyInfo;
    private ArrayList<Log> sellLogs;
    private ArrayList<Product> allProducts;
    private ArrayList<Off> allOff;
    private double money;


    public Seller() {
        super();
        sellLogs = new ArrayList<>();
        allProducts=new ArrayList<>();
        allOff=new ArrayList<>();
        //changing database
    }


    public Seller(HashMap<String, String> userInfo) {
        super(userInfo);
        this.companyInfo = userInfo.get("company-info");
        this.companyName = userInfo.get("company-name");
        sellLogs = new ArrayList<>();
        allProducts=new ArrayList<>();
        allOff=new ArrayList<>();
        //changing database
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
        //changing database
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


    public Off getOffById(String id) {
        for (Off off : allOff) {
            if (id.equals(off.getOffId()))
                return off;
        }
        return null;
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
            offsInfo.add(off.discountInfoForSending());
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
                return off.discountInfoForSending();
            }
        }
        return null;
    }


    public void editProduct(String type, String field, Object newValue, Product product) {
        //this function will create a new request for this seller to editing product with data given to this function
        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType("edit-product " + type);
        request.setRequestSender(this);


        EditProductRequest editProductRequest = new EditProductRequest();
        if (newValue instanceof HashMap) {
            HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
            editProductRequest.setNewValueHashMap(specialProperties);
        } else if(newValue instanceof String)
            editProductRequest.setNewValue((String) newValue);

        editProductRequest.setField(field);
        editProductRequest.setProductId(product.getId());
        editProductRequest.setSeller(this.getUsername());

        request.setMainRequest(editProductRequest);
    }

    public void addProduct(Product product) {
        this.allProducts.add(product);
    }

    public void addProduct(HashMap<String, String> productInfo, HashMap<String, String> specialProperties) {
        //this function will create a request to adding product to intended seller
        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType("add-product");

        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setSellerUsername(this.getUsername());
        addProductRequest.setCategoryName(productInfo.get("category"));
        addProductRequest.setSubCategoryName(productInfo.get("sub-category"));
        addProductRequest.setCompany(productInfo.get("company"));
        addProductRequest.setDescription(productInfo.get("description"));
        addProductRequest.setName(productInfo.get("name"));
        addProductRequest.setNumberInStock(Integer.parseInt(productInfo.get("number-in-stock")));
        addProductRequest.setPrice(Double.parseDouble(productInfo.get("price")));
        addProductRequest.setSpecialProperties(specialProperties);

        request.setMainRequest(addProductRequest);
    }


    public void editOff(Off off, String field, Object newValue, String type) {
        //this function will create a request to edit intended off
        EditOffRequest editOffRequest;
        if (newValue instanceof ArrayList) {
            ArrayList<String> productsId = (ArrayList<String>) newValue;
            editOffRequest = new EditOffRequest(field, productsId);
        } else if (newValue instanceof String) {
            editOffRequest = new EditOffRequest(field, (String) newValue);
        } else
            return;

        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType("edit-off " + type);
        editOffRequest.setOffId(off.getOffId());

        request.setMainRequest(editOffRequest);
    }

    public void addOff(Off off) {
        this.allOff.add(off);
    }

    public void addOff(HashMap<String, String> offInfo, ArrayList<String> productsId) {
        //this function will create a request to adding a new off
        Request request = new Request();
        AddOffRequest addOffRequest = new AddOffRequest();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType("add-off");
        request.setMainRequest(addOffRequest);
        addOffRequest.setFinishTime(Date.getDateWithString(offInfo.get("finish-time")));
        addOffRequest.setStartTime(Date.getDateWithString(offInfo.get("start-time")));
        addOffRequest.setPercent(Integer.parseInt(offInfo.get("percent")));
        addOffRequest.setProductsId(productsId);
        addOffRequest.setSellerUsername(this.getUsername());
    }


    public ArrayList<UserInfo> getBuyers(Product product) {
        ArrayList<UserInfo> buyers = new ArrayList<>();
        for (Log sellLog : this.sellLogs) {
            if (sellLog.isThereProductInLog(product)) {
                buyers.add(sellLog.getCustomer());
            }
        }
        return buyers;
    }


    private void addSellLog(Log sellLog) {
        this.sellLogs.add(sellLog);
    }


    @Override
    public UserInfo userInfoForSending() {
        UserInfo user = new UserInfo();
        user.setCompanyInfo(this.companyInfo);
        user.setCompanyName(this.companyName);
        user.setEmail(this.getEmail());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setMoney(this.money);
        user.setPhoneNumber(this.getPhoneNumber());
        user.setUsername(this.getUsername());
        return user;
    }


    public void removeProduct(Product product) {
        this.allProducts.remove(product);
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
}
