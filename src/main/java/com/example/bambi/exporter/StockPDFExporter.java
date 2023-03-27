package com.example.bambi.exporter;

import com.example.bambi.entity.Size;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class StockPDFExporter {
    private final List<Size> lowInStock;
    private final List<Size> outOfStock;
    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    Font heading1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new Color(238, 145, 11));
    Font heading2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new Color(0xFF, 0x00, 0x00));

    public StockPDFExporter(List<Size> lowInStock, List<Size> outOfStock) {
        this.lowInStock = lowInStock;
        this.outOfStock = outOfStock;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(new Color(255, 246, 166));
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Product ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Product Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Product Size", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stock Level", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<Size> list) {
        for (Size size: list) {
            table.addCell(String.valueOf(size.getProduct().getId()));
            table.addCell(size.getProduct().getProductName());
            table.addCell(size.getProductSize());
            table.addCell(String.valueOf(size.getProductStock()));
        }
    }


    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Image image = Image.getInstance("src/main/resources/static/images/Bambi-Shoes-Logo-Text-only-1.png");
        document.add(image);
        document.add(new Paragraph("Date of Report: " + new Date(), font));
        document.add(new Paragraph("Low In Stock: Any product with a stock level lower than 13"));
        //Create low stock table
        document.add(new Paragraph("All Low In Stock Products", heading1));
        PdfPTable lowInStockTable = new PdfPTable(4);
        lowInStockTable.setWidthPercentage(100);
        lowInStockTable.setSpacingBefore(15);

        writeTableHeader(lowInStockTable);
        writeTableData(lowInStockTable, lowInStock);
        document.add(lowInStockTable);


        //Add second table
        document.add(new Paragraph("All Out Of Stock Products", heading2));
        PdfPTable outOfStockTable = new PdfPTable(4);
        outOfStockTable.setWidthPercentage(100);
        outOfStockTable.setSpacingBefore(15);

        writeTableHeader(outOfStockTable);
        writeTableData(outOfStockTable, outOfStock);
        document.add(outOfStockTable);

        document.close();
    }


}
