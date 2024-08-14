package com.ims.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ims.common.InventoryConstants;
import com.ims.model.Product;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.opencsv.CSVWriter;

@Component
public class ExportUtil {

	public void writeProductsToCsv(Writer writer, List<Product> products) throws IOException {

		try (CSVWriter csvWriter = new CSVWriter(writer)) {
			String[] header = { InventoryConstants.ID, InventoryConstants.NAME, InventoryConstants.PRICE,
					InventoryConstants.DESCRIPTION, InventoryConstants.CREATED_DATE, InventoryConstants.UPDATED_DATE };
			csvWriter.writeNext(header);

			for (Product product : products) {
				String[] data = { String.valueOf(product.getId()), product.getName(), product.getPrice().toString(),
						product.getDescription(),
						DateTimeUtils.formatISODateTime(product.getCreatedDate().toString(),
								InventoryConstants.DATE_TIME_12_HOUR_PATTERN),
						DateTimeUtils.formatISODateTime(product.getUpdatedDate().toString(),
								InventoryConstants.DATE_TIME_12_HOUR_PATTERN) };
				csvWriter.writeNext(data);
			}
		}
	}

	public void writeProductsToPdf(OutputStream outputStream, List<Product> products) {

		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);

		// Title
		document.add(new Paragraph(InventoryConstants.PDF_HEADING).setFontSize(14)
				.setFontColor(new DeviceRgb(255, 69, 0)).setTextAlignment(TextAlignment.CENTER).setBold());

		// Table Creation
		Table table = new Table(7);
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.SERIAL_NUMBER).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.ID).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.NAME).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.PRICE).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.DESCRIPTION).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.CREATED_DATE).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));
		table.addHeaderCell(new Cell().add(new Paragraph(InventoryConstants.UPDATED_DATE).setFontSize(10)
				.setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER).setBold()));

		// Populating the table with data
		int serialNumber = 0;
		for (Product product : products) {

			boolean isRowEven = serialNumber % 2 == 0;
			Color bgColor = isRowEven ? ColorConstants.LIGHT_GRAY : ColorConstants.WHITE;

			table.addCell(new Cell().add(new Paragraph(String.valueOf(++serialNumber))).setFontSize(9)
					.setBackgroundColor(bgColor));
			table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getId()))).setFontSize(9)
					.setBackgroundColor(bgColor));
			table.addCell(new Cell().add(new Paragraph(product.getName())).setFontSize(9).setBackgroundColor(bgColor));
			table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getPrice()))).setFontSize(9)
					.setBackgroundColor(bgColor));
			table.addCell(new Cell()
					.add(new Paragraph(null != product.getDescription() ? product.getDescription() : StringUtils.EMPTY))
					.setFontSize(9).setBackgroundColor(bgColor));
			table.addCell(
					new Cell()
							.add(new Paragraph(DateTimeUtils.formatISODateTime(product.getCreatedDate().toString(),
									InventoryConstants.DATE_TIME_12_HOUR_PATTERN)))
							.setFontSize(9).setBackgroundColor(bgColor));
			table.addCell(
					new Cell()
							.add(new Paragraph(DateTimeUtils.formatISODateTime(product.getUpdatedDate().toString(),
									InventoryConstants.DATE_TIME_12_HOUR_PATTERN)))
							.setFontSize(9).setBackgroundColor(bgColor));
		}

		document.add(table);
		document.close();
	}

	public String getFileName(String format) {

		return InventoryConstants.PRODUCTS_FILE_NAME + DateTimeUtils
				.localTimezoneData(InventoryConstants.INDIA_STANDARD_TIME_ZONE, InventoryConstants.DATE_TIME_PATTERN)
				.replace(" ", "_") + format;
	}
}
