package model.others;

import com.google.gson.Gson;
import model.discount.Discount;
import model.log.BuyLog;
import model.send.receive.CartInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;

public class ShoppingCart {
    private static ShoppingCart localShoppingCart;

    private Discount appliedDiscountCode;
    private ArrayList<ProductInCart> productsInCart = new ArrayList<>();


    public ShoppingCart() {
        productsInCart=new ArrayList<>();
    }

    public static ShoppingCart getLocalShoppingCart() {
        return localShoppingCart;
    }

    public static void setLocalShoppingCart(ShoppingCart localShoppingCart) {
        ShoppingCart.localShoppingCart = localShoppingCart;
    }

    public String cartInfo() {
        CartInfo cartInfo = new CartInfo();
        for (ProductInCart product : productsInCart) {
            cartInfo.addProductInfo(product.getProduct(), product.getSeller(), product.getNumberInCart());
        }
        return (new Gson()).toJson(cartInfo);
    }

    public boolean isProductInCart(String productId, String sellerUsername) {
        for (ProductInCart product : productsInCart) {
            if (product.getProduct().getId().equals(productId) &&
                    product.getSeller().getUsername().equals(sellerUsername)) {
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
            if (!this.productsInCart.contains(productInCart)) {
                this.productsInCart.add(productInCart);
            }
        }
    }

    public void applyDiscountCode(String code) {
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (ProductInCart productInCart : this.productsInCart) {
            Seller seller = productInCart.getSeller();
            Product product = productInCart.getProduct();
            int productNumber = productInCart.getNumberInCart();
            totalPrice = product.getFinalPrice(seller) * (double) productNumber;
        }
        return totalPrice;
    }

    public void addToCart(Product product, Seller seller) {
        ProductInCart productInCart = new ProductInCart(product, seller);
        for (ProductInCart temp : this.productsInCart) {
            if (productInCart.equals(temp)) {
                return;
            }
        }
        this.productsInCart.add(productInCart);
    }

    public void buy() {
        for (ProductInCart productInCart : productsInCart) {
            Seller seller = productInCart.getSeller();
            Product product = productInCart.getProduct();
            int productNumber = productInCart.getNumberInCart();
            double price = product.getFinalPrice(seller) * (double) productNumber;
            seller.increaseMoney(price);
            product.decreaseProduct(seller, productNumber);
        }
    }

    public Discount getAppliedDiscountCode() {
        return appliedDiscountCode;
    }

    public void setAppliedDiscountCode(Discount appliedDiscountCode) {
        this.appliedDiscountCode = appliedDiscountCode;
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
    }


    private static class ProductInCart {
        private Product product;
        private Seller seller;
        private int numberInCart;

        public ProductInCart() {
        }

        public ProductInCart(Product product, Seller seller) {
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
    }
}
