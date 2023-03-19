package com.example.bambi.exporter;

import com.example.bambi.entity.Order;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderPDFExporter {
    private List<Order> listOrders;
    private LocalDateTime startDateTime;
    private Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    private String timeLength;

    public OrderPDFExporter(List<Order> listOrders, LocalDateTime startDateTime, String length) {
        this.startDateTime = startDateTime;
        this.listOrders = listOrders;
        this.timeLength = length;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(new Color(255, 246, 166));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);


        cell.setPhrase(new Phrase("Order Number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total(£)", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingSize(3);

        for (Order order: listOrders) {
            if (!order.getOrderCompletion().equals("COMPLETE") && !order.getOrderCompletion().equals("CANCELLED")) {
                table.addCell(String.valueOf((order.getOrderId())));
                table.addCell(order.getFirstName());
                table.addCell(order.getLastName());
                if (order.getOrderCompletion().equals("IN PROGRESS")) {
                    table.addCell("Outgoing");
                } else if (order.getOrderCompletion().equals("PENDING")) {
                    table.addCell("Incoming");
                }
                table.addCell(String.valueOf(order.getCreatedAt()));
                table.addCell(decimalFormat.format(order.getOrderTotal()));
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingSize(3);

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance("src/main/resources/static/images/Bambi-Shoes-Logo-Text-only-1.png");

        document.add(image);
        if (this.timeLength.equals("Year")) {
            document.add(new Paragraph("Current " + timeLength + "'s Orders Report"));
        } else {
            document.add(new Paragraph("Previous " + timeLength + "'s Orders Report"));
        }


        document.add(new Paragraph("Date of Report Creation: " + new Date()));
        document.add(new Paragraph( timeLength + " Beginning: " + startDateTime.toLocalDate()));
        document.add(new Paragraph("Total Revenue: £"
                + decimalFormat.format(weeklyTotalRevenue()), font));

        //Orders Table Creation
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);

        document.close();
    }

    public int weeklyTotalRevenue() {
        int total = 0;
        for (Order order: listOrders) {
            total += order.getOrderTotal().intValueExact();
        }
        return total;
    }
}
