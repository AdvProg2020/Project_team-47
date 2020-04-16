package model.discount;

import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode extends Discount {
    private static ArrayList<DiscountCode> allDiscountCodes;
    private int discountCode;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private HashMap<User,Integer> userUsedTimeHashMap;
    private ArrayList<User> usersAbleToUse;


    public DiscountCode() {
    }

    public static Discount getDiscountById(int id){return null;}

    public static void removeDiscountCode(String code){}







    public static boolean isThereDiscountWithCode(String code){return true;}

    public static String getAllDiscountCodeInfo(String field,String direction){return null;}

    public boolean canThisPersonUseCode(User user){return true;}

    @Override
    public String toString() {
        return "DiscountCode{}";
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public void setUserUsedTimeHashMap(HashMap<User, Integer> userUsedTimeHashMap) {
        this.userUsedTimeHashMap = userUsedTimeHashMap;
    }

    public void useDiscountCode(User user){


    }












    @Override
    public String discountInfoForSending() {
        return null;
    }


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
