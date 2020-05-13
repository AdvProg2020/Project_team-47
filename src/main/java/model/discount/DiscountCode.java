package model.discount;

import com.google.gson.Gson;
import controller.Controller;
import model.log.BuyLog;
import model.others.Sort;
import model.send.receive.DiscountCodeInfo;
import model.user.Customer;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode extends Discount {
    private static ArrayList<DiscountCode> allDiscountCodes;
    private String discountCode;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private HashMap<User, Integer> userUsedTimeHashMap;
    private ArrayList<Customer> usersAbleToUse;
    private BuyLog buyLog;

    static{
        allDiscountCodes = new ArrayList<>();
    }

    public DiscountCode() {
        userUsedTimeHashMap = new HashMap<>();
        usersAbleToUse = new ArrayList<>();
        allDiscountCodes.add(this);
        this.discountCode = codeCreator();
    }

    private String codeCreator() {
        String id = Controller.idCreator();
        for (DiscountCode discountCode : allDiscountCodes) {
            if (id.equals(discountCode.getDiscountCode())){
                codeCreator();
            }
        }
        return id;
    }

    public void removeCustomer(Customer customer){

    }

    public static DiscountCode getDiscountById(String id) {
        return allDiscountCodes.stream().
                filter(discount -> id.equals(discount.discountCode))
                .findAny().orElse(null);
    }

    public static void removeDiscountCode(String code) {
        allDiscountCodes.remove(allDiscountCodes.stream().
                filter(discount -> code.equals(discount.discountCode))
                .findAny().orElse(null));
    }

    public static boolean isThereDiscountWithCode(String code) {
        return allDiscountCodes.stream().anyMatch
                (discount -> code.equals(discount.discountCode));
    }

    public static ArrayList<DiscountCodeInfo> getAllDiscountCodeInfo(String field, String direction) {
        ArrayList<DiscountCode> discountCodes = Sort.sortDiscountCode(field, direction, allDiscountCodes);
        ArrayList<DiscountCodeInfo> discountsInfo = new ArrayList<>();
        assert discountCodes != null;
        for (DiscountCode discountCode : discountCodes) {
            discountsInfo.add(discountCode.discountInfoForSending());
        }
        return discountsInfo;
    }

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public static void setAllDiscountCodes(ArrayList<DiscountCode> allDiscountCodes) {
        DiscountCode.allDiscountCodes = allDiscountCodes;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public HashMap<User, Integer> getUserUsedTimeHashMap() {
        return userUsedTimeHashMap;
    }

    public void setUserUsedTimeHashMap(HashMap<User, Integer> userUsedTimeHashMap) {
        this.userUsedTimeHashMap = userUsedTimeHashMap;
    }

    public void remove() {
        allDiscountCodes.remove(this);
        for (Customer customer : this.usersAbleToUse) {
            customer.removeDiscountCode(this);
        }
    }

    public double getPriceAfterApply(double price) {
        return (100 - discountPercent) * price / 100;
    }

    public void codeUsed(Customer customer) {
        userUsedTimeHashMap.replace(customer, userUsedTimeHashMap.get(customer) + 1);
    }
    public double appliedDiscount(double price) {
        return discountPercent * price / 100;
    }

    public boolean canThisPersonUseCode(User user) {
        return usersAbleToUse.stream().anyMatch(user::equals);
    }

    @Override
    public String toString() {
        return "DiscountCode{}";
    }//

    public void useDiscountCode(User user) {
        userUsedTimeHashMap.replace(user, userUsedTimeHashMap.get(user) - 1);
    }

    public DiscountCodeInfo discountInfoForSending() {
        return null;
    }//

    public int getMaxUsableTime() {
        return maxUsableTime;
    }

    public void setMaxUsableTime(int maxUsableTime) {
        this.maxUsableTime = maxUsableTime;
    }

    public ArrayList<Customer> getUsersAbleToUse() {
        return usersAbleToUse;
    }

    public void setUsersAbleToUse(ArrayList<Customer> usersAbleToUse) {
        this.usersAbleToUse = usersAbleToUse;
    }
}
