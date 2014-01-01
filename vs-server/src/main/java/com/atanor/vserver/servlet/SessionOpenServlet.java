package com.atanor.vserver.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atanor.vserver.facades.PresentationFacade;

@Singleton
@SuppressWarnings("serial")
public class SessionOpenServlet extends HttpServlet {

	@Inject
	private PresentationFacade presentationFacade;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		presentationFacade.startPresentation();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
