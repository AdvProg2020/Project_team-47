package model.log;

import model.others.Date;
import model.others.Product;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SellLog extends Log {
    private HashMap<Product, Integer> productsHashMap;
    @Override
    public String toString() {
        return "SellLog{}";
    }//



    @Override
    public String getLogInfoForSending() {
        return null;
    }//
}
