package model.discount;

import com.google.gson.Gson;
import model.log.BuyLog;
import model.others.Date;
import model.others.Sort;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode extends Discount {
    private static ArrayList<DiscountCode> allDiscountCodes;
    private String discountCode;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private HashMap<User,Integer> userUsedTimeHashMap;
    private ArrayList<User> usersAbleToUse;
    private BuyLog buyLog;


    public DiscountCode(Date discountStartTime, Date discountFinishTime, int discountPercent,
                        String discountCode, int maxUsableTime, int maxDiscountAmount,
                        HashMap<User, Integer> userUsedTimeHashMap, ArrayList<User> usersAbleToUse, BuyLog buyLog) {
        super(discountStartTime, discountFinishTime, discountPercent);
        this.discountCode = discountCode;
        this.maxUsableTime = maxUsableTime;
        this.maxDiscountAmount = maxDiscountAmount;
        this.userUsedTimeHashMap = userUsedTimeHashMap;
        this.usersAbleToUse = usersAbleToUse;
        this.buyLog = buyLog;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public HashMap<User, Integer> getUserUsedTimeHashMap() {
        return userUsedTimeHashMap;
    }

    public static Discount getDiscountById(String id){
        return allDiscountCodes.stream().
                filter(discount -> id.equals(discount.discountCode))
                .findAny().orElse(null);
    }

    public static void removeDiscountCode(String code){
        allDiscountCodes.remove(allDiscountCodes.stream().
                filter(discount -> code.equals(discount.discountCode))
                .findAny().orElse(null));
    }

    public static boolean isThereDiscountWithCode(String code){
        return allDiscountCodes.stream().anyMatch
                (discount -> code.equals(discount.discountCode));
    }

    public static String getAllDiscountCodeInfo(String field,String direction){
        ArrayList<DiscountCode> discountCodes = Sort.sortDiscountCode(field, direction, allDiscountCodes);
        ArrayList<String> discountsInfo = new ArrayList<>();
        assert discountCodes != null;
        for (DiscountCode discountCode : discountCodes) {
            discountsInfo.add(discountCode.discountInfoForSending());
        }
        return (new Gson()).toJson(discountsInfo);
    }

    public boolean canThisPersonUseCode(User user){
        return usersAbleToUse.stream().anyMatch(user::equals);
    }

    @Override
    public String toString() {
        return "DiscountCode{}";
    }//

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public void setUserUsedTimeHashMap(HashMap<User, Integer> userUsedTimeHashMap) {
        this.userUsedTimeHashMap = userUsedTimeHashMap;
    }

    public void useDiscountCode(User user){
       userUsedTimeHashMap.replace(user, userUsedTimeHashMap.get(user) - 1);
    }

    @Override
    public String discountInfoForSending() {
        return null;
    }//


    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }


    public int getMaxUsableTime() {
        return maxUsableTime;
    }


    public ArrayList<User> getUsersAbleToUse() {
        return usersAbleToUse;
    }

    public static void setAllDiscountCodes(ArrayList<DiscountCode> allDiscountCodes) {
        DiscountCode.allDiscountCodes = allDiscountCodes;
    }


    public void setMaxUsableTime(int maxUsableTime) {
        this.maxUsableTime = maxUsableTime;
    }


    public void setUsersAbleToUse(ArrayList<User> usersAbleToUse) {
        this.usersAbleToUse = usersAbleToUse;
    }
}
