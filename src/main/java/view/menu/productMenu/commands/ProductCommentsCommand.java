package view.menu.productMenu.commands;

import model.others.Comment;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ProductCommentsCommand extends Command {
    public ProductCommentsCommand(Menu menu) {
        super(menu);
        setSignature("Comments");
        setRegex("^Comments$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("comments");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showComments(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showComments(ServerMessage serverMessage) {
        ArrayList<Comment> comments = serverMessage.getCommentArrayList();

        int index;
        for (Comment comment : comments) {
            index = comments.indexOf(comment) + 1;
            System.out.println(index + ". ");

            System.out.println("comment title : " + comment.getCommentTitle());
            System.out.println("comment text : " + comment.getCommentText());
            System.out.println("status : " + comment.getStatus());
            System.out.println("product seller : " + comment.getSellerCommentBelongTo());
            System.out.println("comment from : " + comment.getWhoComment());
            if (comment.doesCustomerBought()) {
                System.out.println(comment.getWhoComment() + " had bought the product");
            }
        }
    }
}
