package model.others.request;

import model.ecxeption.product.ProductDoesntExistException;
import model.others.Comment;
import model.others.Product;
import model.send.receive.RequestInfo;

public class AddCommentRequest extends MainRequest {
    private Comment comment;

    public AddCommentRequest(Comment comment) {
        this.comment = comment;
    }

    public AddCommentRequest() {
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setAddComment(comment);
    }

    @Override
    void accept() {
        try {
            Product product = Product.getProductWithId(comment.getProductId());
            product.addComment(comment);
            comment.setStatus("CONFIRMED");
        } catch (ProductDoesntExistException ignored) {
        }
    }

    @Override
    boolean update() {
        return Product.isThereProduct(comment.getProductId());
    }

    @Override
    public void decline() {
        comment.setStatus("UNCONFIRMED");
    }
}
