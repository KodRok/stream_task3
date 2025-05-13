package org.mentor.adapter;

import org.mentor.model.Order;
import java.util.List;

public interface OrderAdapter {
    List<Order> parseToOrders(List<String> lines);
}
