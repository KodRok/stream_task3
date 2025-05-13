package org.mentor.model;

import java.time.LocalDateTime;

public class Order {
    private LocalDateTime dateTime;
    private int cementWeigth;
    private String companyName;


    public Order(LocalDateTime dateTime, String companyName, int weight) {
        this.dateTime = dateTime;
        this.cementWeigth = weight;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCementWeight() {
        return cementWeigth;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
