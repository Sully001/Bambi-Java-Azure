package com.example.bambi.controller;

import com.example.bambi.Projection.DailyOrderRevenue;
import com.example.bambi.Projection.ProductFrequency;
import com.example.bambi.entity.Order;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.repository.ProductRepository;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChartController {
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public ChartController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/data")
    public String showProductsData(Model model) {
        //Get Top 3 Most Sold Items & Their Quantities Sold
        List<ProductFrequency> products = productRepository.findProductFrequency();
        model.addAttribute("products", products);
        model.addAttribute("name", "John");
        Long[] id = new Long[3];
        Long[] frequency = new Long[3];
        String[] shoeNames = new String[3];
        for (int i = 0; i < products.size(); i++) {
            id[i] = products.get(i).getProduct_id();
            String productName = productService.getProductById(id[i]).getProductName();
            shoeNames[i] = productName;
            frequency[i] = products.get(i).getFrequency();
        }


        //Get Revenue for past seven days
        LocalDate now = LocalDate.now();
        LocalDate[] dates = new LocalDate[7];
        int[] dailyRevenue = new int[7];

        LocalDateTime startOfDay = now.atTime(0,0,0);
        LocalDateTime endOfDay = now.atTime(23, 59, 59);

        Timestamp startTime = Timestamp.valueOf(startOfDay);
        Timestamp endTime = Timestamp.valueOf(endOfDay);

        System.out.println("Hello");

        int addition = 0;
        LocalDate nowOrder = LocalDate.now();

        for(int i = 0; i < 7; i++) {
            dates[i] = now;
            now = now.minusDays(1);

            LocalDateTime starting = nowOrder.atTime(0,0,0);
            LocalDateTime ending = nowOrder.atTime(23, 59, 59);

            Timestamp starts = Timestamp.valueOf(starting);
            Timestamp ends = Timestamp.valueOf(ending);

            List<Order> daysOrders = orderService.getPreviousOrdersByTimestamp(starts, ends);
            for (int j = 0; j < daysOrders.size(); j++) {
                addition += daysOrders.get(j).getOrderTotal().intValueExact();
            }
            dailyRevenue[i] = addition;

            //Reset addition and Minus Day
            nowOrder = nowOrder.minusDays(1);
            addition = 0;
        }



        model.addAttribute("id", id);
        model.addAttribute("frequency", frequency);
        model.addAttribute("shoes", shoeNames);
        model.addAttribute("dates", dates);
        model.addAttribute("dailyRevenue", dailyRevenue);
        return "products_data";
    }
}
