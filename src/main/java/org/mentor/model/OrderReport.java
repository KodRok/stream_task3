package org.mentor.model;

public class OrderReport {
    private String companyName;
    private double orderAmount;

    public OrderReport(String companyName, double orderAmount) {
        this.companyName = companyName;
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return companyName + " - " + orderAmount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

}
