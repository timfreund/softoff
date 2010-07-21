package net.freunds.softoff;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SoftOffServlet extends HttpServlet {
	private SoftOffController controller = new SoftOffController();
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(controller.available()){
			response.setStatus(200);
			response.getOutputStream().print("OK");
		} else {
			response.setStatus(503);
			response.getOutputStream().print("Unavailable");
		}
		response.getOutputStream().close();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		controller.setInstanceName(config.getServletContext().getServletContextName());
		controller.initialize();
		
		Enumeration names = config.getInitParameterNames();
		String name = null;
		while(names.hasMoreElements()){
			name = (String)names.nextElement();
			if(name.startsWith("test_")){
				String testName = config.getInitParameter(name);
				addTest(testName);
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		controller.shutdown();
	}

	public void addTest(String fullyQualifiedTestName) throws ServletException {
		String[] tokens = fullyQualifiedTestName.split(":");
		String className = tokens[0];
		String methodName = tokens[1];
		try {
			controller.addTest(className, methodName);
		} catch (Exception e) {
			throw new ServletException("Error configuring SoftOff controller", e);
		}
		
	}
	
	public SoftOffController getController() {
		return controller;
	}

	public void setController(SoftOffController controller) {
		this.controller = controller;
	}
}
