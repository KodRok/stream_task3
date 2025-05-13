package org.mentor.service;

import org.junit.jupiter.api.Test;
import org.mentor.model.Order;
import org.mentor.model.OrderReport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    @Test
    void calculateDiscountShouldSortOrdersByDateAndTime() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:33"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        double startDiscount = 0.5;
        double discountStep = 0.05;
        double pricePerKg = 10;

        List<OrderReport> orderReports = orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg);
        assertEquals("Company1", orderReports.get(0).getCompanyName());
        assertEquals("Company2", orderReports.get(1).getCompanyName());
        assertEquals("Company3", orderReports.get(2).getCompanyName());
    }

    @Test
    void calculateDiscountShouldReturnCorrectOrderAmount() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = 0.5;
        double discountStep = 0.1;
        double pricePerKg = 10;

        List<OrderReport> orderReports = orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg);
        assertEquals(50, orderReports.get(0).getOrderAmount(), 0.01);
        assertEquals(120, orderReports.get(1).getOrderAmount(), 0.01);
        assertEquals(210, orderReports.get(2).getOrderAmount(), 0.01);
        assertEquals(320, orderReports.get(3).getOrderAmount(), 0.01);
        assertEquals(450, orderReports.get(4).getOrderAmount(), 0.01);
    }

    @Test
    void calculateDiscountShouldReturnPositiveDiscount() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = 0.5;
        double discountStep = 0.2;
        double pricePerKg = 10;

        List<OrderReport> orderReports = orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg);
        assertEquals(50, orderReports.get(0).getOrderAmount(), 0.01);
        assertEquals(140, orderReports.get(1).getOrderAmount(), 0.01);
        assertEquals(270, orderReports.get(2).getOrderAmount(), 0.01);
        assertEquals(400, orderReports.get(3).getOrderAmount(), 0.01);
        assertEquals(500, orderReports.get(4).getOrderAmount(), 0.01);
    }
    @Test
    void calculateDiscountShouldThrowExceptionWhenNegativeDiscountStep() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = 10;
        double discountStep = - 0.2;
        double pricePerKg = - 10;

        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }

    @Test
    void calculateDiscountShouldThrowExceptionWhenNegativeStartDiscount() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = -10;
        double discountStep = 0.2;
        double pricePerKg = 10;

        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }

    @Test
    void calculateDiscountShouldThrowExceptionWhenNegativePricePerKg() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = 10;
        double discountStep = 0.2;
        double pricePerKg = - 10;

        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }

    @Test
    void calculateDiscountShouldThrowExceptionWhenZeroPricePerKg() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.parse("2025-02-24T12:33:33"), "Company1", 10));
        orders.add(new Order(LocalDateTime.parse("2025-02-25T12:33:32"), "Company2", 20));
        orders.add(new Order(LocalDateTime.parse("2025-02-27T12:33:32"), "Company3", 30));
        orders.add(new Order(LocalDateTime.parse("2025-03-28T12:33:32"), "Company4", 40));
        orders.add(new Order(LocalDateTime.parse("2025-04-28T12:33:32"), "Company4", 50));
        double startDiscount = 10;
        double discountStep = 0.2;
        double pricePerKg = 0;

        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }

    @Test
    void calculateDiscountShouldThrowExceptionWhenNullOrders() {
        OrderService orderService = new OrderService();
        List<Order> orders = null;
        double startDiscount = 0.5;
        double discountStep = 0.1;
        double pricePerKg = 10;
        assertThrows(NullPointerException.class, () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }

    @Test
    void calculateDiscountShouldThrowExceptionWhenEmptyOrders() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>();
        double startDiscount = 0.5;
        double discountStep = 0.1;
        double pricePerKg = 10;
        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg));
    }
}