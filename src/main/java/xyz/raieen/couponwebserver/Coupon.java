package xyz.raieen.couponwebserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a coupon
 */
@Document(collection = "coupons")
public class Coupon {

    @Id
    String id; // Spring
    private boolean redeemable;
    private String action, recipient;
    private int quantity;
    private long redeemed;

    /**
     * Needed for Spring
     */
    public Coupon() {
    }

    /**
     * Default Constructor
     *
     * @param redeemable weather the action is redeemable
     * @param action     action on the coupon ie. high five
     * @param recipient  email of recipient
     * @param quantity   quantity of action. ie. 5 high fives.
     * @param redeemed   timestamp of when the coupon was redeemed, 0 if it has not been redeemed
     */
    public Coupon(boolean redeemable, String action, String recipient, int quantity, long redeemed) {
        this.redeemable = redeemable;
        this.action = action;
        this.recipient = recipient;
        this.quantity = quantity;
        this.redeemed = redeemed;
    }

    /*
     * Getters/Setters
     * I wish java had proper data classes...
     */

    public String getId() {
        return id;
    }

    public boolean isRedeemable() {
        return redeemable;
    }

    public void setRedeemable(boolean redeemable) {
        this.redeemable = redeemable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(long redeemed) {
        this.redeemed = redeemed;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "redeemable=" + redeemable +
                ", action='" + action + '\'' +
                ", receipient='" + recipient + '\'' +
                ", quantity=" + quantity +
                ", redeemed=" + redeemed +
                '}';
    }
}
