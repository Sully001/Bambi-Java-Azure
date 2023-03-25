package com.example.bambi.controller;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.service.OrderDetailsService;
import com.example.bambi.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;

    public OrderController(OrderService orderService,OrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Order> orders = orderService.getAllOrders();
        if (keyword != null && !keyword.isEmpty()) {
            orders = orderService.searchOrders(keyword);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        return "orders";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("orderStatus") String orderStatus) {
        orderService.updateOrderStatus(orderId, orderStatus);
        return "redirect:/orders";
    }
    @GetMapping("/order_details/{orderId}")
    public String getOrderDetails(@PathVariable Order orderId, Model model) {
        List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetailsList", orderDetailsList);
        return "order_details";
    }




}
