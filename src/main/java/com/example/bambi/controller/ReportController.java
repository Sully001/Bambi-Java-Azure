package com.example.bambi.controller;

import com.azure.core.annotation.Get;
import com.example.bambi.entity.Order;
import com.example.bambi.exporter.OrderPDFExporter;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ReportController {

    private OrderService orderService;

    public ReportController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/reports")
    public String reports() {
        return "reports";
    }

    @GetMapping("/orders/export")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Order> listOrders = orderService.getAllOrders();

        OrderPDFExporter exporter = new OrderPDFExporter(listOrders);
        exporter.export(response);


    }
}
