package org.mentor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mentor.adapter.OrderAdapter;
import org.mentor.adapter.OrderAdapterService;
import org.mentor.model.Order;
import org.mentor.model.OrderReport;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    @Mock
    private OrderAdapterService orderAdapterService;

    @Mock
    private FileOrderService fileOrderService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderManager orderManager;

    @Test
    void manageDiscountDayShouldCorrectReadAndWrite() {
        String inputFileName = "input.txt";
        String outputFileName = "output.txt";
        double startDiscount = 10.0;
        double discountStep = 5.0;
        double pricePerKg = 100.0;

        List<String> lines = List.of("line1", "line2");
        OrderAdapter adapter = mock(OrderAdapter.class);
        List<Order> orders = List.of(new Order((LocalDateTime.parse("2025-02-24T12:33:33")), "CompanyA", 10));
        List<OrderReport> report = List.of(new OrderReport("CompanyA", 111));

        when(fileOrderService.read(inputFileName)).thenReturn(lines);
        when(orderAdapterService.getAdapter(inputFileName)).thenReturn(adapter);
        when(adapter.parseToOrders(lines)).thenReturn(orders);
        when(orderService.calculateDiscount(orders, startDiscount, discountStep, pricePerKg)).thenReturn(report);

        orderManager.manageDiscountDay(inputFileName, outputFileName, startDiscount, discountStep, pricePerKg);

        verify(fileOrderService).read(inputFileName);
        verify(orderAdapterService).getAdapter(inputFileName);
        verify(adapter).parseToOrders(lines);
        verify(orderService).calculateDiscount(orders, startDiscount, discountStep, pricePerKg);
        verify(fileOrderService).write(outputFileName, report);
    }

    @Test
    void manageDiscountDayShouldThrowsExceptionWhenNullInputFileName() {
        assertThrows(IllegalArgumentException.class,
                () -> orderManager.manageDiscountDay(null, "output.txt", 10.0, 5.0, 100.0));
    }

    @Test
    void manageDiscountDayShouldThrowsExceptionWhenNullOutputFileName() {
        assertThrows(IllegalArgumentException.class,
                () -> orderManager.manageDiscountDay("input.txt", null, 10.0, 5.0, 100.0));
    }
}