package org.mentor.adapter;

import org.mentor.model.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCustomAdapter implements OrderAdapter {
    public static final int DATETIME_INDEX = 0;
    public static final int COMPANY_NAME_INDEX = 1;
    public static final int WEIGHT_INDEX = 2;

    @Override
    public List<Order> parseToOrders(List<String> lines) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return lines.stream()
                .map(line -> line.split("\\|"))
                .map(parts -> new Order(
                        LocalDateTime.parse(parts[DATETIME_INDEX], formatter),
                        parts[COMPANY_NAME_INDEX],
                        Integer.parseInt(parts[WEIGHT_INDEX])
                ))
                .collect(Collectors.toList());
    }
}
