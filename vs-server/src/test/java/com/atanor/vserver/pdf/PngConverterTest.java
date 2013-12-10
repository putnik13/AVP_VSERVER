package com.atanor.vserver.pdf;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.PngImage;

public class PngConverterTest {

	private static final String PDF_FULL_NAME = "d:/temp/pdf/ConvertImagetoPDF.pdf";
	private static final String IMAGE1_FULL_NAME = "d:/temp/pdf/test0.png";
	private static final String IMAGE2_FULL_NAME = "d:/temp/pdf/test1.png";
	private static final String IMAGE3_FULL_NAME = "d:/temp/pdf/test2.png";
	
	public static void main(String[] args) {
		try {
			Document convertPngToPdf = new Document(PageSize.A4.rotate());
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
		// image.setAbsolutePosition((PageSize.A4.rotate().getWidth() -
		// image.getScaledWidth()) / 2, (PageSize.A4.rotate()
		// .getHeight() - image.getScaledHeight()) / 2);
		image.setAbsolutePosition(0,0);
	}

}
