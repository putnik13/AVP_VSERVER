package com.atanor.vserver.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.PngImage;

public class PngConverterTest {

	private static final Rectangle PAGE_FORMAT = PageSize.A4.rotate();

	private static final String PDF_FULL_NAME = "d:/temp/pdf/ConvertImagetoPDF.pdf";
	private static final String PDF_WITH_LOGO_FULL_NAME = "d:/temp/pdf/ConvertImagetoPDFLogo.pdf";
	private static final String IMAGE1_FULL_NAME = "d:/temp/pdf/test0.png";
	private static final String IMAGE2_FULL_NAME = "d:/temp/pdf/test1.png";
	private static final String IMAGE3_FULL_NAME = "d:/temp/pdf/test2.png";
	private static final String LOGO_FULL_NAME = "d:/tmp/pdf/testlogo.png";
	
	public static void main(String[] args) {
		try {
			Document convertPngToPdf = new Document(PAGE_FORMAT);
			PdfWriter.getInstance(convertPngToPdf, new FileOutputStream(PDF_FULL_NAME));
			convertPngToPdf.open();

			Image convertBmp0 = PngImage.getImage(IMAGE1_FULL_NAME);
			setImagePosition(convertBmp0);
			convertPngToPdf.add(convertBmp0);

			convertPngToPdf.newPage();
			Image convertBmp1 = PngImage.getImage(IMAGE2_FULL_NAME);
			setImagePosition(convertBmp1);
			convertPngToPdf.add(convertBmp1);

			convertPngToPdf.newPage();
			Image convertBmp2 = PngImage.getImage(IMAGE3_FULL_NAME);
			setImagePosition(convertBmp2);
			convertPngToPdf.add(convertBmp2);

			convertPngToPdf.close();

		} catch (Exception i1) {
			i1.printStackTrace();
		}
	}

	private static void setImagePosition(final Image image) {
		image.setAbsolutePosition(0, 0);
		image.scaleAbsolute(PAGE_FORMAT.getWidth(), PAGE_FORMAT.getHeight());
	}

	public static void addLogo() throws IOException, DocumentException {
		PdfReader reader = new PdfReader(PDF_FULL_NAME);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(PDF_WITH_LOGO_FULL_NAME));

		Image logoImage = PngImage.getImage(LOGO_FULL_NAME);
		logoImage.setAbsolutePosition(20, PAGE_FORMAT.getHeight() - 20 - 50);
		logoImage.scaleAbsolute(50, 50);
		
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			final PdfContentByte over = stamper.getOverContent(i);
			over.addImage(logoImage);
		}
		
		stamper.close();
		reader.close();
	}

}
