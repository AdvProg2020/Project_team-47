package model.user;

import com.google.gson.Gson;
import model.send.receive.UserInfo;

import java.util.HashMap;

public class Manager extends User {
    public Manager() {
        super();
    }

    public Manager(HashMap<String, String> userInfo) {
        super(userInfo);
        User.managerAdded();
    }

    @Override
    public String userInfoForSending() {
        UserInfo user = new UserInfo();
        user.setEmail(this.getEmail());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setPhoneNumber(this.getPhoneNumber());
        user.setUsername(this.getUsername());
        return (new Gson()).toJson(user);
    }
}
