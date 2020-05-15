package model.send.receive;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMessage extends Message {
    private String messageContext;
    private ArrayList<String> messageArrayListInputs;
    private HashMap<String, String> messageHashMapInputs;
    private HashMap<String, String> productInfo;
    private HashMap<String, String> productSpecialProperties;
    private String mainCategoryName;
    private String subCategoryName;

    public ClientMessage(String messageContext, ArrayList<String> messageArrayListInputs,
                         HashMap<String, String> messageHashMapInputs) {
        this.messageContext = messageContext;
        this.messageArrayListInputs = new ArrayList<>(messageArrayListInputs);
        this.messageHashMapInputs = new HashMap<>(messageHashMapInputs);
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public ArrayList<String> getMessageArrayListInputs() {
        return messageArrayListInputs;
    }

    public void setMessageArrayListInputs(ArrayList<String> messageArrayListInputs) {
        this.messageArrayListInputs = messageArrayListInputs;
    }

    public HashMap<String, String> getProductInfo() {
        return productInfo;
    }

    public HashMap<String, String> getProductSpecialProperties() {
        return productSpecialProperties;
    }

    public void setProductInfo(HashMap<String, String> productInfo) {
        this.productInfo = productInfo;
    }

    public void setProductSpecialProperties(HashMap<String, String> productSpecialProperties) {
        this.productSpecialProperties = productSpecialProperties;
    }

    public HashMap<String, String> getMessageHashMapInputs() {
        return messageHashMapInputs;
    }

    public void setMessageHashMapInputs(HashMap<String, String> messageHashMapInputs) {
        this.messageHashMapInputs = messageHashMapInputs;
    }
}
