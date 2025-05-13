package org.mentor.service;

import org.mentor.model.Order;
import org.mentor.model.OrderReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderService {

    public List<OrderReport> calculateDiscount(List<Order> orders, double startDiscount,
                                               double discountStep, double pricePerKg) {
        validate(startDiscount, discountStep, pricePerKg);
        Collections.sort(orders, Comparator.comparing(Order::getDateTime));
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Список заказов пуст");
        }

        List<OrderReport> orderReports = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            double discount = Math.max(0, startDiscount - i * discountStep);
            double orderWeight = order.getCementWeight();
            double discountAmount = discount * pricePerKg * orderWeight;
            double orderAmount = orderWeight * pricePerKg - discountAmount;
            OrderReport orderReport = new OrderReport(order.getCompanyName(), orderAmount);
            orderReports.add(orderReport);
        }
        return orderReports;
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