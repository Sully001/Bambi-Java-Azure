package com.example.bambi.controller;

import com.example.bambi.entity.Order;
import com.example.bambi.exporter.OrderPDFExporter;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Controller
public class ReportController {

    private OrderService orderService;
    private ProductService productService;

    public ReportController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        LocalDate now = LocalDate.now();
        // Set the day of the month to the first day of the previous month
        LocalDate firstDayOfPreviousMonth = now.minusMonths(1).withDayOfMonth(1);
        // Set the day of the month to the last day of the previous month
        LocalDate lastDayOfPreviousMonth = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());




//        while(!weekStartDate.isEqual(weekEndDate)) {
//            weekStartDate = weekStartDate.plusDays(1);
//            System.out.println("Day: " + weekStartDate);
//            System.out.println("The end of the week!");
//        }

//        model.addAttribute("weekStartDate", monday);
//        model.addAttribute("weekEndDate", sunday);
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
}
