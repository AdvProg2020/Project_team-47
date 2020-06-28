package controller;

import graphic.PageController;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;


public class LoginAndViewPersonalInfoTest {
    static User manager;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    public void viewPersonalInfo() {

        //first we should register a user with this properties
        // to add a json file for user
        Manager amir = new Manager();
        amir.setUsername("amirmohammad0");
        amir.setPassword("asdfghjk");
        amir.setEmail("amirmohammadisazadeh@gmail.com");
        amir.setFirstName("amir0");
        amir.setLastName("amir0");
        amir.setPhoneNumber("09111111110");
        amir.setType("manager");

        User.getAllUsers().add(amir);
        ClientMessage request;
        ServerMessage answer;
        UserInfo userInfo;
        HashMap<String, String> hashMap;

        request = new ClientMessage("login");
        hashMap = new HashMap<>();
        hashMap.put("username", "amirmohammad0");
        hashMap.put("password", "asdfghjk");
        request.setHashMap(hashMap);
        answer = PageController.send1(request);

        request = new ClientMessage("view personal info");
        hashMap = new HashMap<>();
        hashMap.put("username", "amir");
        request.setHashMap(hashMap);
        answer = PageController.send1(request);
        userInfo = answer.getUserInfo();

        Assert.assertEquals("amirmohammad0", userInfo.getUsername());
        Assert.assertEquals("amirmohammadisazadeh@gmail.com", userInfo.getEmail());
        Assert.assertEquals("amir0", userInfo.getFirstName());
        Assert.assertEquals("amir0", userInfo.getLastName());
        Assert.assertEquals("09111111110", userInfo.getPhoneNumber());
        Assert.assertEquals("manager", userInfo.getType());

    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all test methods");
    }


}
