package model.log;


import model.others.Date;
import model.others.Product;
import model.user.User;

import java.util.ArrayList;

public class BuyLog extends Log {
    public BuyLog(double money, String logId, Date logDate, double appliedDiscount,
                  User customer, String address, String postalCode, String phoneNumber,
                  String customerRequests, String purchaseStatus, ArrayList<Product> purchasedWares) {
        super(money, logId, logDate, appliedDiscount, customer, address, postalCode,
                phoneNumber, customerRequests, purchaseStatus, purchasedWares);
    }

    @Override
    public String toString() {
        return "BuyLog{}";
    }//

    @Override
    public String getLogInfoForSending() {
        return null;
    }//
}
