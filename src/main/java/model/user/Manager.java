package model.user;

import database.UserData;
import model.send.receive.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Manager extends User {
    public Manager() {
        super();
    }

    public Manager(HashMap<String, String> userInfo,byte[] avatar) {
        super(userInfo,avatar);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Customer> getCustomersForGift(int numberOfUser) {
        ArrayList<Customer> randomCustomers = new ArrayList<>();
        ArrayList<User> allUsersClone = (ArrayList<User>) allUsers.clone();
        Collections.shuffle(allUsersClone);
        for (User user : allUsers) {
            if (numberOfUser == 0)
                break;
            else if (user instanceof Customer) {
                randomCustomers.add((Customer) user);
                numberOfUser--;
            }
        }
        return randomCustomers;
    }

    @Override
    public void deleteUser() {
        allUsers.remove(this);
        User.managerRemoved();
        this.removeFromDatabase();
    }

    @Override
    public UserInfo userInfoForSending() {
        UserInfo user = new UserInfo();
        userInfoSetter(user);
        user.setType("manager");
        return user;
    }

    @Override
    public UserData updateDatabase() {
        UserData user = new UserData("manager");
        super.updateDatabase(user);
        return user;
    }
}
