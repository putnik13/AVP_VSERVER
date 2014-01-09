package com.atanor.vserver.servlet;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;

@Singleton
@SuppressWarnings("serial")
public class PdfServlet extends HttpServlet {

	private static final String PDF_NAME_PARAM = "docId";

	@Inject
	private ConfigDataService configService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String pdfName = req.getParameter(PDF_NAME_PARAM);
		if (StringUtils.isNotEmpty(PDF_NAME_PARAM)) {

			byte[] bytes = readFile(pdfName);

			final ServletOutputStream out = resp.getOutputStream();
			resp.setContentType("application/pdf");
			resp.addHeader("Content-Type", "application/pdf");
			resp.addHeader("Content-Disposition", "attachment; filename=" + pdfName);
			resp.setContentLength((int) bytes.length);

			out.write(bytes);
			out.flush();
			IOUtils.closeQuietly(out);
		}
	}

	private byte[] readFile(final String fileName) throws IOException {
		final File file = new File(config().getPresentationsOutput() + "/" + fileName);
		return FileUtils.readFileToByteArray(file);
	}

	private VsConfig config() {
		return configService.getConfig();
	}
}
