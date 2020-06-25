package graphic.panel.customer;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import graphic.panel.AccountPage;
import graphic.panel.customer.CustomerDiscountCodes.CustomerDiscountCodesController;
import graphic.panel.customer.CustomerDiscountCodes.CustomerDiscountCodesPage;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryPage;
import graphic.panel.customer.cart.CustomerCartController;
import graphic.panel.customer.cart.CustomerCartPage;
import javafx.scene.input.MouseEvent;
import model.send.receive.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerPageController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPageController();
        }
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showPersonalInfo() {
        //getting information

        GraphicView.getInstance().changeScene(AccountPage.getScene());
    }

    public boolean bol = false;
    public void goToShoppingCart() {
        //getting purchase history
        ClientMessage request = new ClientMessage("show products in cart");
        //todo

        ServerMessage answer = send(request);

        if(answer.getType().equals("Successful") || true){
            //ArrayList<CartInfo.ProductInCart> productInCartArrayList = answer.getCartInfo().getProducts();
            ArrayList<CartInfo.ProductInCart> productInCartArrayList = new ArrayList<>();
            CartInfo cartInfo = new CartInfo();
            cartInfo.setPrice(200);

            CartInfo.ProductInCart p1 = new CartInfo.ProductInCart();
            ProductInfo pf1 = new ProductInfo();
            p1.setProduct(pf1);
            if (bol) {
                p1.setNumberInCart(3);

            } else {
                p1.setNumberInCart(2);
            }
            pf1.setName("ali");
            productInCartArrayList.add(p1);

            CartInfo.ProductInCart p2 = new CartInfo.ProductInCart();
            ProductInfo pf2 = new ProductInfo();
            p2.setProduct(pf2);
            p2.setNumberInCart(22);
            pf2.setName("ali2");
            productInCartArrayList.add(p2);


            CustomerCartController.cartInfo = cartInfo;
            CustomerCartController.productsInCart = productInCartArrayList;
            //CustomerCartController.cartInfo = answer.getCartInfo();
            //((CustomerPurchaseHistoryPage)CustomerPurchaseHistoryPage.getInstance()).setLogInfoArrayList(logInfoArrayList);
            GraphicView.getInstance().changeScene(CustomerCartPage.getInstance());
        } else {
            //todo amir
            System.out.println("oops");
        }

    }

    public void showPurchaseHistory(MouseEvent mouseEvent) {
        //getting purchase history
        ClientMessage request = new ClientMessage("view orders");
        //todo

        ServerMessage answer = send(request);

        if(answer.getType().equals("Successful") || true){
            //ArrayList<LogInfo> logInfoArrayList = answer.getLogInfoArrayList();
            ArrayList<LogInfo> logInfoArrayList = new ArrayList<>();

            LogInfo l1 = new LogInfo("1", new Date(1 / 2), "customer");
            l1.setPrice(10);
            l1.setSeller("ali");
            logInfoArrayList.add(l1);

            LogInfo l2 = new LogInfo("2", new Date(1), "customer2");
            l2.setPrice(102);
            l2.setSeller("ali2");
            logInfoArrayList.add(l2);

            CustomerPurchaseHistoryController.purchaseHistoryArrayList = logInfoArrayList;

            //((CustomerPurchaseHistoryPage)CustomerPurchaseHistoryPage.getInstance()).setLogInfoArrayList(logInfoArrayList);
            GraphicView.getInstance().changeScene(CustomerPurchaseHistoryPage.getInstance());
        } else {
            //todo amir
            System.out.println("oops");
        }

    }

    public void showDiscountCodes(MouseEvent mouseEvent) {
        //getting purchase history
        ClientMessage request = new ClientMessage("view discount codes customer");
        //todo

        ServerMessage answer = send(request);


        if(answer.getType().equals("Successful") || true){
            //ArrayList<DiscountCodeInfo> discountCodeInfoArrayList = answer.getDiscountCodeInfoArrayList();
            ArrayList<DiscountCodeInfo> discountCodeInfoArrayList = new ArrayList<>();
            DiscountCodeInfo d1 = new DiscountCodeInfo(new Date(1), new Date(2), 40);
            d1.setCode("salam1");
            d1.setMaxUsableTime(5);
            d1.setMaxDiscountAmount(2);
            discountCodeInfoArrayList.add(d1);

            DiscountCodeInfo d2 = new DiscountCodeInfo(new Date(3), new Date(4), 80);
            d2.setCode("salam2");
            d2.setMaxUsableTime(6);
            d2.setMaxDiscountAmount(7);
            discountCodeInfoArrayList.add(d2);



            CustomerDiscountCodesController.discountCodes = discountCodeInfoArrayList;
            //((CustomerPurchaseHistoryPage)CustomerPurchaseHistoryPage.getInstance()).setLogInfoArrayList(logInfoArrayList);

            GraphicView.getInstance().changeScene(CustomerDiscountCodesPage.getInstance());
        } else {
            //todo amir
            System.out.println("oops");
        }


    }

    public void logout(MouseEvent mouseEvent) {
        ClientMessage request = new ClientMessage("logout");
        ServerMessage answer = send(request);
        if (answer.getType().equals("Successful") || true) {
            GraphicView.getInstance().changeScene(MainMenuPage.getInstance());
        }
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
