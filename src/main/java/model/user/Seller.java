package model.user;

import com.google.gson.Gson;
import model.category.Category;
import model.discount.Off;
import model.log.Log;
import model.others.Date;
import model.others.Product;
import model.others.Sort;
import model.others.request.*;
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
        sellLogs = new ArrayList<>();
        allProducts=new ArrayList<>();
        allOff=new ArrayList<>();
    }

    public Seller(HashMap<String, String> userInfo) {
        super(userInfo);
        this.companyInfo = userInfo.get("company-info");
        this.companyName = userInfo.get("company-name");
        sellLogs = new ArrayList<>();
        allProducts=new ArrayList<>();
        allOff=new ArrayList<>();
    }

    @Override
    public void deleteUser() {
        allUsers.remove(this);
        for (Product product : this.allProducts) {
            product.removeSeller(this);
        }
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
        request.setType("edit-product " + type);
        EditProductRequest editProductRequest = new EditProductRequest();
        if (newValue instanceof HashMap) {
            HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
            editProductRequest.setNewValueHashMap(specialProperties);
        } else
            editProductRequest.setNewValue(newValue);
        editProductRequest.setField(field);
        editProductRequest.setProduct(product);
        editProductRequest.setSeller(this);
        request.setMainRequest(editProductRequest);
    }

    public void addProduct(HashMap<String, String> productInfo, HashMap<String, String> specialProperties) {
        Request request = new Request();
        request.setApplyDate(Date.getCurrentDate());
        request.setRequestSender(this);
        request.setType("add-product");
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setSeller(this);
        addProductRequest.setCategory(Category.getMainCategoryByName(productInfo.get("category")));
        addProductRequest.setSubCategory(Category.getSubCategoryByName(productInfo.get("sub-category")));
        addProductRequest.setCompany(productInfo.get("company"));
        addProductRequest.setDescription(productInfo.get("description"));
        addProductRequest.setName(productInfo.get("name"));
        addProductRequest.setNumberInStock(Integer.parseInt(productInfo.get("number-in-stock")));
        addProductRequest.setPrice(Double.parseDouble(productInfo.get("price")));
        addProductRequest.setSpecialProperties(specialProperties);
        request.setMainRequest(addProductRequest);
    }

    public void editOff(Off off, String field, Object newValue, String type) {
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
        request.setType("edit-product " + type);
        editOffRequest.setOff(off);
        request.setMainRequest(editOffRequest);
    }

    public void addOff(HashMap<String, String> offInfo, ArrayList<String> productsId) {
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
        product.getProductSellers().remove(product.getProductSeller(this));
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
