package database;

import model.discount.DiscountCode;
import model.discount.Off;
import model.log.BuyLog;
import model.log.SellLog;
import model.others.Product;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.TreeSet;

public class UserData {
    private String sendCode;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;
    private double money;
    private String companyName;
    private String companyInfo;
    private TreeSet<String> productsId;
    private TreeSet<String> offsId;
    private TreeSet<String> logs;
    private TreeSet<String> discountCodes;

    public UserData(String type) {
        switch (type) {
            case "seller":
                productsId = new TreeSet<>();
                offsId = new TreeSet<>();
                logs = new TreeSet<>();
                this.type = "seller";
                break;
            case "customer":
                logs = new TreeSet<>();
                discountCodes = new TreeSet<>();
                this.type = "customer";
                break;
            case "manager":
                this.type = "manager";
                break;
        }
    }


    static void addUsers(ArrayList<UserData> users) {
        for (UserData user : users) {
            user.createUser().addToUsersFromDatabase();
        }
    }

    static void addNotVerifiedUsers(ArrayList<UserData> notVerifiedUsers) {
        for (UserData notVerifiedUser : notVerifiedUsers) {
            notVerifiedUser.createUser().addToVerificationListFromDatabase();
        }
    }

    static void connectRelations(ArrayList<UserData> users, ArrayList<SellLog> sellLogs, ArrayList<BuyLog> buyLogs) {
        for (UserData user : users) {
            user.connectRelations(sellLogs, buyLogs);
        }
    }

    private void connectRelations(ArrayList<SellLog> sellLogs, ArrayList<BuyLog> buyLogs) {
        User user = User.getUserByUsername(this.username);
        if (user instanceof Seller) {
            this.connectProducts((Seller) user);
            this.connectOffs((Seller) user);
            this.connectSellLogs((Seller) user, sellLogs);
        } else if (user instanceof Customer) {
            this.connectBuyLogs((Customer) user, buyLogs);
            this.connectDiscountCodes((Customer) user);
        }
    }

    private void connectDiscountCodes(Customer customer) {
        for (String code : this.discountCodes) {
            DiscountCode discountCode = DiscountCode.getDiscountById(code);
            if (discountCode != null)
                customer.addDiscountCode(discountCode);
        }
    }

    private void connectBuyLogs(Customer customer, ArrayList<BuyLog> buyLogs) {
        for (String logId : this.logs) {
            BuyLog buyLog = getBuyLogById(buyLogs, logId);
            if (buyLog != null)
                customer.addBuyLog(buyLog);
        }
    }

    private void connectSellLogs(Seller seller, ArrayList<SellLog> sellLogs) {
        for (String logId : this.logs) {
            SellLog sellLog = getSellLogById(sellLogs, logId);
            if (sellLog != null)
                seller.addSellLog(sellLog);
        }
    }

    private SellLog getSellLogById(ArrayList<SellLog> sellLogs, String logId) {
        for (SellLog sellLog : sellLogs) {
            if (sellLog.getLogId().equals(logId))
                return sellLog;
        }
        return null;
    }

    private BuyLog getBuyLogById(ArrayList<BuyLog> buyLogs, String logId) {
        for (BuyLog buyLog : buyLogs) {
            if (buyLog.getLogId().equals(logId))
                return buyLog;
        }
        return null;
    }

    private void connectOffs(Seller seller) {
        for (String offId : this.offsId) {
            Off off = Off.getOffById(offId);
            if (off != null)
                seller.addOff(off);
        }
    }

    private void connectProducts(Seller seller) {
        for (String productId : this.productsId) {
            Product product = Product.getProductWithId(productId);
            if (product != null)
                seller.addProduct(product);
        }
    }

    private User createUser() {
        User user;
        switch (this.type) {
            case "seller":
                user = new Seller();
                ((Seller) user).setCompanyName(this.companyName);
                ((Seller) user).setCompanyInfo(this.companyInfo);
                ((Seller) user).setMoney(this.money);
                break;
            case "customer":
                user = new Customer();
                ((Customer) user).setMoney(this.money);
                break;
            default:
                user = new Manager();
                break;
        }
        user.setType(this.type);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        user.setLastName(this.lastName);
        user.setFirstName(this.firstName);
        user.setSendCode(this.sendCode);
        user.setUsername(this.username);
        user.setEmail(this.email);
        return user;
    }

    public void update() {
        Database.addUser(this, this.username);
    }

    public void addDiscountCode(String code) {
        this.discountCodes.add(code);
    }

    public void addProduct(String productId) {
        this.productsId.add(productId);
    }

    public void addOff(String offId) {
        this.offsId.add(offId);
    }

    public void addLog(String logId) {
        this.logs.add(logId);
    }


    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public void setProductsId(TreeSet<String> productsId) {
        this.productsId = productsId;
    }

    public void setOffsId(TreeSet<String> offsId) {
        this.offsId = offsId;
    }

    public void setLogs(TreeSet<String> logs) {
        this.logs = logs;
    }

    public void setDiscountCodes(TreeSet<String> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addNorVerifiedUser() {
        Database.addNotVerifiedUser(this, this.username);
    }


    public String getType() {
        return type;
    }
}
