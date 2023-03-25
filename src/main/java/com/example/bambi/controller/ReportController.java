package com.example.bambi.controller;

import com.example.bambi.Projection.ProductFrequency;
import com.example.bambi.entity.Order;
import com.example.bambi.entity.Size;
import com.example.bambi.exporter.OrderPDFExporter;
import com.example.bambi.exporter.StockPDFExporter;
import com.example.bambi.repository.ProductRepository;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Controller
public class ReportController {

    private OrderService orderService;
    private ProductService productService;
    private SizeService sizeService;
    @Autowired
    private ProductRepository productRepository;

    public ReportController(OrderService orderService, ProductService productService, SizeService sizeService) {
        this.orderService = orderService;
        this.productService = productService;
        this.sizeService = sizeService;
    }
    @GetMapping("/reports")
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
        String[] avgDailySpend = new String[7];
        DecimalFormat df = new DecimalFormat("#.##");

        //Todays start and end
        LocalDateTime startOfDay = now.atTime(0,0,0);
        LocalDateTime endOfDay = now.atTime(23, 59, 59);

        Timestamp startTime = Timestamp.valueOf(startOfDay);
        Timestamp endTime = Timestamp.valueOf(endOfDay);
        //////////////////////

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
            double spend = (double) addition / daysOrders.size();
            if (Double.isNaN(spend)) {
                avgDailySpend[i] = String.valueOf(0);
            } else {
                avgDailySpend[i] = df.format(spend);
            }

            System.out.println(avgDailySpend[i]);

            //Reset addition and Minus Day
            nowOrder = nowOrder.minusDays(1);
            addition = 0;
        }

        //Get Revenue for past 30 Days
        LocalDate today = LocalDate.now();
        LocalDate[] month = new LocalDate[30];
        int[] monthlyRevenue = new int[30];
        int sum = 0;
        for(int i = 0; i < 30; i++) {
            month[i] = today;

            LocalDateTime starting = today.atTime(0,0,0);
            LocalDateTime ending = today.atTime(23, 59, 59);

            Timestamp starts = Timestamp.valueOf(starting);
            Timestamp ends = Timestamp.valueOf(ending);

            List<Order> daysOrders = orderService.getPreviousOrdersByTimestamp(starts, ends);
            for (int j = 0; j < daysOrders.size(); j++) {
                sum += daysOrders.get(j).getOrderTotal().intValueExact();
            }
            monthlyRevenue[i] = sum;

            //Reset sum and Minus Day
            today = today.minusDays(1);
            sum = 0;
        }

        model.addAttribute("frequency", frequency);
        model.addAttribute("shoes", shoeNames);
        model.addAttribute("dates", dates);
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("avgDailySpend", avgDailySpend);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("month", month);
        return "reports";
    }

    @GetMapping("/orders/export/prev-week")
    public void weeklyExportToPDF(HttpServletResponse response) throws IOException {
        ///////////////////////////////////////////////
        //Get this week's Start and End of week
        LocalDate now = LocalDate.now();
        LocalDate thisMonday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisSunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        //Add times for a local date and time for PREVIOUS WEEK
        LocalDateTime prevWeekStart = thisMonday.minusWeeks(1).atTime(0,0,0);
        LocalDateTime prevWeekEnd = thisSunday.minusWeeks(1).atTime(23,59,59);

        //Convert to SQL timestamp for comparison
        Timestamp prevMonday = Timestamp.valueOf(prevWeekStart);
        Timestamp prevSunday = Timestamp.valueOf(prevWeekEnd);
        ///////////////////////////////////////////////

        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_prev_week" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Order> listOrders = orderService.getPreviousOrdersByTimestamp(prevMonday, prevSunday);

        OrderPDFExporter exporter = new OrderPDFExporter(listOrders, prevWeekStart, "Week");
        exporter.export(response);
    }

    @GetMapping("/orders/export/prev-month")
    public void monthlyExportToPDF(HttpServletResponse response) throws IOException {
        LocalDate now = LocalDate.now();
        // Set the day of the month to the first day of the previous month
        LocalDate firstDayOfPreviousMonth = now.minusMonths(1).withDayOfMonth(1);
        // Set the day of the month to the last day of the previous month
        LocalDate lastDayOfPreviousMonth = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());

        LocalDateTime prevMonthStart = firstDayOfPreviousMonth.atTime(0,0,0);
        LocalDateTime prevMonthEnd = lastDayOfPreviousMonth.atTime(23,59,59);

        Timestamp monthStart = Timestamp.valueOf(prevMonthStart);
        Timestamp monthEnd = Timestamp.valueOf(prevMonthEnd);

        //Get list of orders
        List<Order> listOrders = orderService.getPreviousOrdersByTimestamp(monthStart, monthEnd);

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_prev_month" + currentDateTime + ".pdf";
        OrderPDFExporter exporter = new OrderPDFExporter(listOrders, prevMonthStart, "Month");
        exporter.export(response);
    }

    @GetMapping("/orders/export/total-year-orders")
    public void yearlyExportToPDF(HttpServletResponse response) throws IOException {
        LocalDate now = LocalDate.now();

        //Get list of orders
        List<Order> listOrders = orderService.getAllOrders();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_prev_month" + currentDateTime + ".pdf";
        OrderPDFExporter exporter = new OrderPDFExporter(listOrders, dateTime, "Year");
        exporter.export(response);
    }

    @GetMapping("/orders/export/stock-report")
    public void stockExportToPDF(HttpServletResponse response) throws IOException {
        LocalDate now = LocalDate.now();

        //Get list of orders
        List<Size> lowInStock = sizeService.getSizesLowInStock();
        List<Size> outOfStock = sizeService.getSizesOutOfStock();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_prev_month" + currentDateTime + ".pdf";
        StockPDFExporter exporter = new StockPDFExporter(lowInStock, outOfStock);
        exporter.export(response);
    }
}
