package com.atanor.vserver.pdf.impl;

import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.pdf.PdfGenerator;
import com.atanor.vserver.services.ConfigDataService;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.PngImage;

public class PdfGeneratorImpl implements PdfGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(PdfGeneratorImpl.class);
	private static final Rectangle PAGE_FORMAT = PageSize.A4.rotate();

	@Inject
	ConfigDataService configService;

	@Override
	public void generatePdf(final String fileName, final List<Snapshot> snapshots) {

		try {
			
			final String pdfName = config().getPresentationsOutput() + "/" + fileName;
			
			final Document convertPngToPdf = new Document(PAGE_FORMAT);
			PdfWriter.getInstance(convertPngToPdf, new FileOutputStream(pdfName));
			convertPngToPdf.open();

			for (int i = 0; i < snapshots.size(); i++) {
				if (i > 0) {
					convertPngToPdf.newPage();
				}
				
				final Image image = PngImage.getImage(snapshots.get(i).getFileName());
				setImagePosition(image);
				convertPngToPdf.add(image);
			}
			
			convertPngToPdf.close();

		} catch (Exception e) {
			LOG.error("Errors during {} pdf file generation", fileName, e);
		}

	}

	private VsConfig config() {
		return configService.getConfig();
	}

	private void setImagePosition(final Image image) {
		image.setAbsolutePosition(0, 0);
		image.scaleAbsolute(PAGE_FORMAT.getWidth(), PAGE_FORMAT.getHeight());
	}
}
