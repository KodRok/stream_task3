package org.mentor.service;

import org.mentor.adapter.OrderAdapter;
import org.mentor.adapter.OrderAdapterService;
import org.mentor.model.Order;
import org.mentor.model.OrderReport;

import java.util.List;

public class OrderManager {
    private final OrderAdapterService orderAdapterService;
    private final FileOrderService fileOrderService;
    private final OrderService orderService;

    public OrderManager(OrderAdapterService orderAdapterService, FileOrderService fileOrderService,
                        OrderService orderService) {
        this.orderAdapterService = orderAdapterService;
        this.fileOrderService = fileOrderService;
        this.orderService = orderService;
    }

    public void manageDiscountDay(String inputFileName, String outputFileName,
                                  double startDiscount, double discountStep, double pricePerKg) {
        if (inputFileName == null || outputFileName == null) {
            throw new IllegalArgumentException("FileName is null or empty");
        }
        List<String> lines = fileOrderService.read(inputFileName);
        OrderAdapter adapter = orderAdapterService.getAdapter(inputFileName);
        List<Order> orders = adapter.parseToOrders(lines);
        List<OrderReport> report = orderService.calculateDiscount(orders, startDiscount,
                discountStep, pricePerKg);
        fileOrderService.write(outputFileName, report);
    }
}
