package com.example.bambi.exporter;

import com.example.bambi.entity.Order;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class OrderPDFExporter {
    private List<Order> listOrders;

    public OrderPDFExporter(List<Order> listOrders) {
        this.listOrders = listOrders;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(new Color(255, 246, 166));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);


        cell.setPhrase(new Phrase("Order Number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total(£)", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Date", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingSize(3);
        for (Order order: listOrders) {
            table.addCell(String.valueOf((order.getOrderId())));
            table.addCell(order.getFirstName());
            table.addCell(order.getLastName());
            table.addCell(order.getOrderCompletion());
            table.addCell(decimalFormat.format(order.getOrderTotal()));
            table.addCell(String.valueOf(order.getCreatedAt()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        int total = 0;

        for (Order order: listOrders) {
            total += order.getOrderTotal().intValueExact();
        }

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingSize(3);

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance("src/main/resources/static/images/Bambi-Shoes-Logo-Text-only-1.png");

        document.add(image);
        document.add(new Paragraph("Orders Report"));


        document.add(new Paragraph("Date: " + new Date()));
        document.add(new Paragraph("Total Revenue To Date: " + "£"
                + decimalFormat.format(total)));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

}
