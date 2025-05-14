package org.mentor.service;

import org.mentor.model.Order;
import org.mentor.model.OrderReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderService {
    public List<OrderReport> calculateDiscount(List<Order> orders, double startDiscount,
                                               double discountStep, double pricePerKg) {
        validate(startDiscount, discountStep, pricePerKg);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Список заказов пуст");
        }

        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getDateTime))
                .toList();

        return IntStream.range(0, sortedOrders.size())
                .mapToObj(i -> {
                    Order order = sortedOrders.get(i);
                    double discount = Math.max(0, startDiscount - i * discountStep);
                    double orderWeight = order.getCementWeight();
                    double discountAmount = discount * pricePerKg * orderWeight;
                    double orderAmount = orderWeight * pricePerKg - discountAmount;
                    return new OrderReport(order.getCompanyName(), orderAmount);
                })
                .collect(Collectors.toList());
    }

    private static void validate(double startDiscount, double discountStep, double pricePerKg) {
        if (startDiscount < 0) {
            throw new IllegalArgumentException("Скидка не может быть отрицательной");
        }
        if (discountStep < 0) {
            throw new IllegalArgumentException("Шаг скидки не может быть отрицательным");
        }
        if (pricePerKg <= 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной или нулевой");
        }
    }
}