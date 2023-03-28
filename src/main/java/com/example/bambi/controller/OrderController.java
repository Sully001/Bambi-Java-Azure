package com.example.bambi.controller;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.entity.Product;
import com.example.bambi.service.OrderDetailsService;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final ProductService productService;

    public OrderController(OrderService orderService, OrderDetailsService orderDetailsService, ProductService productService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
        this.productService = productService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Order> orders = orderService.getAllOrders();
        if (keyword != null && !keyword.isEmpty()) {
            orders = orderService.searchOrders(keyword);
        }
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
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
        // Get a list of low stock products
        List<Product> lowStockProducts = productService.getLowStockProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetailsList", orderDetailsList);
        return "order_details";
    }




}
