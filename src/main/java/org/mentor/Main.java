package org.mentor;

import org.mentor.service.FileOrderService;
import org.mentor.service.OrderManager;
import org.mentor.service.OrderService;
import org.mentor.adapter.OrderAdapterService;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "orders/discount_day.txt";
        String outputFileName = "reports/report.txt";
        double startDiscount = 0.5;
        double discountStep = 0.05;
        double pricePerKg = 10;

        FileOrderService fileOrderService = new FileOrderService();
        OrderAdapterService orderAdapterService = new OrderAdapterService();
        OrderService orderService = new OrderService();

        OrderManager orderManager = new OrderManager(orderAdapterService, fileOrderService,
                orderService);
        orderManager.manageDiscountDay(inputFileName, outputFileName,
                startDiscount, discountStep, pricePerKg);
    }
}
