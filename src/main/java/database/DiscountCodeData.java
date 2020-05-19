package database;

import model.discount.DiscountCode;
import model.user.Customer;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiscountCodeData {
    private Date startTime;
    private Date finishTime;
    private int percent;
    private String code;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private HashMap<String, Integer> userUsedHashMap;
    private ArrayList<String> userAbleToUse;

    public DiscountCodeData() {
        userUsedHashMap = new HashMap<>();
        userAbleToUse = new ArrayList<>();
    }

    static void addCodes(ArrayList<DiscountCodeData> codes) {
        if (codes == null)
            return;
        for (DiscountCodeData code : codes) {
            code.createCode();
        }
    }

    static void connectRelations(ArrayList<DiscountCodeData> codes) {
        for (DiscountCodeData code : codes) {
            code.connectRelations();
        }
    }

    private void connectRelations() {
        DiscountCode code = DiscountCode.getDiscountById(this.code);
        HashMap<Customer, Integer> userUsedTime = getUserUsedTimeHashMap();
        for (Map.Entry<Customer, Integer> entry : userUsedTime.entrySet()) {
            code.addUsers(entry.getKey(), entry.getValue());
        }
    }

    private HashMap<Customer, Integer> getUserUsedTimeHashMap() {
        HashMap<Customer, Integer> userUsedTime = new HashMap<>();
        for (Map.Entry<String, Integer> entry : this.userUsedHashMap.entrySet()) {
            User user = User.getUserByUsername(entry.getKey());
            if (user instanceof Customer)
                userUsedTime.put((Customer) user, entry.getValue());
        }
        return userUsedTime;
    }

    private void createCode() {
        DiscountCode code = new DiscountCode(this.code, this.percent);
        code.setMaxDiscountAmount(this.maxDiscountAmount);
        code.setMaxUsableTime(this.maxUsableTime);
        code.setStartTime(this.startTime);
        code.setFinishTime(this.finishTime);
    }

    public void addToDatabase() {
        Database.addDiscountCode(this, this.code);
    }

    public void addUserAbleToUse(String username) {
        this.userAbleToUse.add(username);
    }

    public void addUserToHashMap(String username, Integer usedTime) {
        userUsedHashMap.put(username, usedTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMaxUsableTime(int maxUsableTime) {
        this.maxUsableTime = maxUsableTime;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}
