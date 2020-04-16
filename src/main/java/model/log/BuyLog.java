package model.log;


public class BuyLog extends Log {
    public BuyLog() {
    }

    @Override
    public String toString() {
        return "BuyLog{}";
    }

    @Override
    public String getLogInfoForSending() {
        return null;
    }
}
