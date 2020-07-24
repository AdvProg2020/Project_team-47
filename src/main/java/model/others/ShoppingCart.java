package model.others;

import controller.Controller;
import controller.Server;
import model.log.BuyLog;
import model.send.receive.CartInfo;
import model.send.receive.ServerMessage;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;

public class ShoppingCart {
    private static ShoppingCart localShoppingCart;

    private final ArrayList<ProductInCart> productsInCart;

    public ShoppingCart() {
        productsInCart = new ArrayList<>();
    }

    public static ShoppingCart getLocalShoppingCart() {
        return localShoppingCart;
    }

    public static void setLocalShoppingCart(ShoppingCart localShoppingCart) {
        ShoppingCart.localShoppingCart = localShoppingCart;
    }

    public CartInfo cartInfo() {
        CartInfo cartInfo = new CartInfo();
        for (ProductInCart productInCart : productsInCart) {
            cartInfo.addProductInfo(productInCart.getProduct(), productInCart.getSeller(), productInCart.getNumberInCart());
        }
        cartInfo.setPrice(this.getTotalPrice());
        return cartInfo;
    }

    public boolean isProductInCart(String productId, String sellerUsername) {
        for (ProductInCart product : productsInCart) {
            if (product.getProduct().getId().equalsIgnoreCase(productId) &&
                    product.getSeller().getUsername().equalsIgnoreCase(sellerUsername)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductInCart(Product product, Seller seller) {
        for (ProductInCart productInCart : productsInCart) {
            if (productInCart.getProduct() == product && productInCart.getSeller() == seller) {
                return true;
            }
        }
        return false;
    }

    public void decreaseProductInCart(Product product, Seller seller) {
        ProductInCart productInCart = new ProductInCart(product, seller);
        for (ProductInCart temp : productsInCart) {
            if (temp.equals(productInCart)) {
                decreaseProductInCart(temp);
                return;
            }
        }

    }

    public void increaseProductInCart(Product product, Seller seller) {
        ProductInCart productInCart = new ProductInCart(product, seller);
        for (ProductInCart temp : productsInCart) {
            if (temp.equals(productInCart)) {
                increaseProductInCart(temp);
            }
        }
    }

    private void decreaseProductInCart(ProductInCart productInCart) {
        if (productInCart.getNumberInCart() == 1) {
            productsInCart.remove(productInCart);
        } else {
            productInCart.decrease();
        }
    }

    private void increaseProductInCart(ProductInCart productInCart) {
        productInCart.increase();
    }


    public boolean canIncrease(Product product, Seller seller) {
        ProductInCart productInCart = this.getProductInCart(product, seller);
        if (productInCart == null) {
            return false;
        }
        return product.getProductSeller(seller).getNumberInStock() > productInCart.getNumberInCart();
    }

    public ProductInCart getProductInCart(Product product, Seller seller) {
        for (ProductInCart productInCart : this.productsInCart) {
            if (productInCart.getProduct() == product && productInCart.getSeller() == seller) {
                return productInCart;
            }
        }
        return null;
    }

    public void mergingWithLocalCart(ShoppingCart localShoppingCart) {
        for (ProductInCart productInCart : localShoppingCart.productsInCart) {
            if (!isProductInCart(productInCart.getProduct(), productInCart.getSeller())) {
                this.productsInCart.add(productInCart);
            }
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (ProductInCart productInCart : this.productsInCart) {
            Seller seller = productInCart.getSeller();
            Product product = productInCart.getProduct();
            int productNumber = productInCart.getNumberInCart();
            totalPrice += product.getFinalPrice(seller) * (double) productNumber;
        }
        return totalPrice;
    }

    public void addToCart(Product product, Seller seller) {
        if (isProductInCart(product, seller)) {
            return;
        }
        ProductInCart productInCart = new ProductInCart(product, seller);
        this.productsInCart.add(productInCart);
    }

    public void buy() {
        for (ProductInCart productInCart : productsInCart) {
            Seller seller = productInCart.getSeller();
            Product product = productInCart.getProduct();
            if (product.getFilePath() != null)
                new Thread(() -> startDownload(Controller.getLoggedUser(), seller, product.getFilePath())).start();
            int productNumber = productInCart.getNumberInCart();
            double price = product.getFinalPrice(seller) * (double) productNumber;
            seller.increaseMoney(price);
            product.decreaseProduct(seller, productNumber);
            product.updateDatabase();
            seller.updateDatabase().update();
        }
    }

    private void startDownload(User customer, User seller, String path) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("give me port");
        serverMessage.setFirstString(customer.getUsername());
        serverMessage.setSecondString(path);
        Server.getServer().addMessage(seller, serverMessage);
    }

    public void update() {
        for (ProductInCart productInCart : productsInCart) {
            Seller seller = productInCart.getSeller();
            Product product = productInCart.getProduct();
            int numberInStock = productInCart.getNumberInCart();
            if (!seller.hasProduct(product)) {
                productsInCart.remove(productInCart);
            } else if (!User.isThereSeller(seller)) {
                productsInCart.remove(productInCart);
            } else if (!Product.isThereProduct(product)) {
                productsInCart.remove(productInCart);
            } else if (numberInStock > product.getNumberInStock(seller)) {
                productInCart.setNumberInCart(product.getNumberInStock(seller));
            }
        }
    }

    public void addToBuyLog(BuyLog purchaseLog) {
        for (ProductInCart productInCart : productsInCart) {
            purchaseLog.addProduct(productInCart.getSeller(), productInCart.getProduct(), productInCart.getNumberInCart());
        }
        purchaseLog.updateDatabase();
    }

    public static class ProductInCart {
        private Product product;
        private Seller seller;
        private int numberInCart;

        public ProductInCart(Product product, Seller seller) {
            this.numberInCart = 1;
            this.product = product;
            this.seller = seller;
        }

        public void decrease() {
            numberInCart--;
        }

        public void increase() {
            numberInCart++;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ProductInCart)
                return product == ((ProductInCart) obj).product && seller == ((ProductInCart) obj).seller;
            return false;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Seller getSeller() {
            return seller;
        }

        public void setSeller(Seller seller) {
            this.seller = seller;
        }

        public int getNumberInCart() {
            return numberInCart;
        }

        public void setNumberInCart(int numberInCart) {
            this.numberInCart = numberInCart;
        }
    }//end ProductInCart class

}//end ShoppingCart class
