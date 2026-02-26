package poly.edu.assignment.dto;

import java.util.Date;

public class VipCustomerDTO {

    private String customerName;
    private Double totalSpent;
    private Date firstPurchase;
    private Date lastPurchase;

    public VipCustomerDTO(String customerName,
            Double totalSpent,
            Date firstPurchase,
            Date lastPurchase) {
        this.customerName = customerName;
        this.totalSpent = totalSpent;
        this.firstPurchase = firstPurchase;
        this.lastPurchase = lastPurchase;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public Date getFirstPurchase() {
        return firstPurchase;
    }

    public Date getLastPurchase() {
        return lastPurchase;
    }
}