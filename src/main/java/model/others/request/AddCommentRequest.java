package model.others.request;

import model.others.Comment;
import model.others.Product;
import model.send.receive.RequestInfo;

public class AddCommentRequest extends MainRequest {
    private Comment comment;

    public AddCommentRequest(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setAddComment(comment);
    }

    @Override
    void accept(String type) {
        Product product = Product.getProductWithId(comment.getProductId());
        product.addComment(comment);
        comment.setStatus("CONFIRMED");
    }

    @Override
    boolean update(String type) {
        return Product.isThereProduct(comment.getProductId());
    }

    @Override
    public void decline() {
        comment.setStatus("UNCONFIRMED");
    }
}
