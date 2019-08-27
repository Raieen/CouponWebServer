package xyz.raieen.couponwebserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupons")
public class Coupon {

    @Id
    String id;
    private boolean redeemable;
    private String action, receipient;
    private int quantity;
    private long redeemed;

    public Coupon() {
    }

    public Coupon(boolean redeemable, String action, String receipient, int quantity, long redeemed) {
        this.redeemable = redeemable;
        this.action = action;
        this.receipient = receipient;
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

    public String getReceipient() {
        return receipient;
    }

    public void setReceipient(String receipient) {
        this.receipient = receipient;
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
                ", receipient='" + receipient + '\'' +
                ", quantity=" + quantity +
                ", redeemed=" + redeemed +
                '}';
    }
}
