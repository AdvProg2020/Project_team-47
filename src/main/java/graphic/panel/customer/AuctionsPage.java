package graphic.panel.customer;

import graphic.PageController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.send.receive.AuctionInfo;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionsPage extends PageController {

    private static PageController controller;
    public Label l0;
    public Label l1;
    public Label l2;
    public Label l3;
    public Label l4;
    public Label l5;
    public Label l6;
    public Label l7;
    public Label l8;
    public Label l9;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/auction/AuctionsPage.fxml");
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

    public void fresh() {
        ServerMessage answer = send(new ClientMessage("give auctions"));
        ArrayList<AuctionInfo> auctionInfos = answer.getAuctionInfoArrayList();
        for (int i = 0; i < 10 && i < auctionInfos.size(); i++) {
            switch (i) {
                case 0: l0.setText(auctionInfos.get(i).getProduct().getName());
                break;
                case 1: l1.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 2: l2.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 3: l3.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 4: l4.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 5: l5.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 6: l6.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 7: l7.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 8: l8.setText(auctionInfos.get(i).getProduct().getName());
                    break;
                case 9: l9.setText(auctionInfos.get(i).getProduct().getName());
                    break;
            }
        }
    }
}
