package model.log;

import model.others.Date;
import model.others.Product;
import model.send.receive.LogInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SellLog extends Log {
    private UserInfo seller;
    private HashMap<ProductInfo, Integer> productsHashMap;
    @Override
    public String toString() {
        return "SellLog{}";
    }



    @Override
    public LogInfo getLogInfoForSending() {
        return null;
    }//
}
