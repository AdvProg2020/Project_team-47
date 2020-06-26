package model.discount;

import controller.Controller;
import database.Database;
import database.DiscountCodeData;
import model.ecxeption.CommonException;
import model.others.Sort;
import model.send.receive.DiscountCodeInfo;
import model.user.Customer;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscountCode extends Discount {
    private static final ArrayList<DiscountCode> allDiscountCodes;

    static {
        allDiscountCodes = new ArrayList<>();
    }

    private final String discountCode;
    private final HashMap<User, Integer> userUsedTimeHashMap;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private ArrayList<Customer> usersAbleToUse;

    public DiscountCode(String code, int percent) {
        userUsedTimeHashMap = new HashMap<>();
        usersAbleToUse = new ArrayList<>();
        allDiscountCodes.add(this);
        this.discountCode = code;
        this.percent = percent;
    }

    public DiscountCode(int maxDiscountAmount, int maxUsableTime) {
        userUsedTimeHashMap = new HashMap<>();
        usersAbleToUse = new ArrayList<>();
        allDiscountCodes.add(this);
        this.maxDiscountAmount = maxDiscountAmount;
        this.maxUsableTime = maxUsableTime;
        this.discountCode = codeCreator();
    }

    public static DiscountCode getDiscountById(String id) throws CommonException {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.discountCode.equalsIgnoreCase(id))
                return discountCode;
        }
        throw new CommonException("There isn't any code with this id!!");
    }

    public static boolean isThereDiscountWithCode(String code) {
        return allDiscountCodes.stream().anyMatch
                (discount -> code.equalsIgnoreCase(discount.discountCode));
    }

    public static ArrayList<DiscountCodeInfo> getAllDiscountCodeInfo(String field, String direction) {
        ArrayList<DiscountCode> discountCodes = Sort.sortDiscountCode(field, direction, allDiscountCodes);
        ArrayList<DiscountCodeInfo> discountsInfo = new ArrayList<>();
        assert discountCodes != null;
        for (DiscountCode discountCode : discountCodes) {
            discountsInfo.add(discountCode.discountCodeInfo());
        }
        return discountsInfo;
    }


    public void updateDatabase() {
        DiscountCodeData codeData = new DiscountCodeData();
        codeData.setCode(this.discountCode);
        codeData.setStartTime(this.startTime);
        codeData.setFinishTime(this.finishTime);
        codeData.setMaxDiscountAmount(this.maxDiscountAmount);
        codeData.setMaxUsableTime(this.maxUsableTime);
        codeData.setPercent(this.percent);
        this.addUsersToData(codeData);
        codeData.addToDatabase();
    }

    private void addUsersToData(DiscountCodeData codeData) {
        for (Customer customer : this.usersAbleToUse) {
            codeData.addUserAbleToUse(customer.getUsername());
        }
        for (Map.Entry<User, Integer> entry : userUsedTimeHashMap.entrySet()) {
            codeData.addUserToHashMap(entry.getKey().getUsername(), entry.getValue());
        }
    }

    private String codeCreator() {
        String code = Controller.idCreator();
        if (isThereDiscountWithCode(code))
            return codeCreator();
        else
            return code;
    }

    public void removeCustomer(Customer customer) {
        usersAbleToUse.remove(customer);
        userUsedTimeHashMap.remove(customer);
        this.updateDatabase();
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public void remove() {
        allDiscountCodes.remove(this);
        for (Customer customer : this.usersAbleToUse) {
            customer.removeDiscountCode(this);
        }
        this.removeFromDatabase();
    }

    public double getPriceAfterApply(double price) {
        return (price - appliedDiscount(price));
    }

    public void codeUsed(Customer customer) {
        int usedTime = userUsedTimeHashMap.get(customer);
        userUsedTimeHashMap.replace(customer, usedTime + 1);
        this.updateDatabase();
    }

    public double appliedDiscount(double price) {
        double applyWithoutMaxAmount = price * percent / 100;
        if (applyWithoutMaxAmount > maxDiscountAmount)
            return maxDiscountAmount;
        else
            return applyWithoutMaxAmount;
    }

    public boolean canThisPersonUseCode(User user) {
        int usedTime = userUsedTimeHashMap.get(user);
        return usedTime < maxUsableTime;
    }

    public DiscountCodeInfo discountCodeInfo() {
        DiscountCodeInfo codeInfo = new DiscountCodeInfo(startTime, finishTime, percent);
        codeInfo.setCode(this.discountCode);
        codeInfo.setMaxDiscountAmount(maxDiscountAmount);
        codeInfo.setMaxUsableTime(maxUsableTime);
        for (Customer customer : usersAbleToUse) {
            codeInfo.addUser(customer);
        }
        return codeInfo;
    }

    public void setMaxUsableTime(int maxUsableTime) {
        this.maxUsableTime = maxUsableTime;
    }

    public void setUsersAbleToUse(ArrayList<Customer> usersAbleToUse) {
        this.usersAbleToUse = usersAbleToUse;
        for (Customer customer : usersAbleToUse) {
            this.userUsedTimeHashMap.put(customer, 0);
        }
    }

    public void addUsers(Customer customer, int usedTime) {
        this.usersAbleToUse.add(customer);
        this.userUsedTimeHashMap.put(customer, usedTime);
    }

    @Override
    public void removeFromDatabase() {
        Database.removeCode(this.discountCode);
    }
}
