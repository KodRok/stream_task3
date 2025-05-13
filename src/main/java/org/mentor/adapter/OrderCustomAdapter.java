package org.mentor.adapter;

import org.mentor.model.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderCustomAdapter implements OrderAdapter {
    public static final int DATETIME_INDEX = 0;
    public static final int COMPANY_NAME_INDEX = 1;
    public static final int WEIGHT_INDEX = 2;

    @Override
    public List<Order> parseToOrders(List<String> lines) {
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(parts[DATETIME_INDEX], formatter);
            String companyName = parts[COMPANY_NAME_INDEX];
            int cementWeight = Integer.parseInt(parts[WEIGHT_INDEX]);
            orders.add(new Order(dateTime, companyName, cementWeight));
        }
        return orders;
    }
}
