package net.freunds.softoff;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;

public class SoftOffServletTest {
	private SoftOffServlet servlet = null;
	
	@Before
	public void setUp() throws Exception {
		servlet = new SoftOffServlet();
	}

	@Test
	public void testInit(){
		ServletConfig config = new ServletConfigImpl();
		
		try {
			servlet.init(config);
			assertFalse(servlet.getController().available());
		} catch (ServletException e) {
			e.printStackTrace();
			fail("Exception caught: " + e.getMessage());
		}
	}
	
	public class ServletConfigImpl implements ServletConfig {
		private Vector parameterNames = new Vector();
		private HashMap<String, String> parameterValues = new HashMap<String, String>();
		
		public ServletConfigImpl(){
			parameterNames.add("test_alwaysPasses");
			parameterNames.add("test_alwaysFails");

			parameterValues.put("test_alwaysPasses", "net.freunds.softoff.DemoTests:alwaysPasses");
			parameterValues.put("test_alwaysFails", "net.freunds.softoff.DemoTests:alwaysFails");
		}

		public String getInitParameter(String name) {
			return parameterValues.get(name);
		}

		public Enumeration getInitParameterNames() {
			return parameterNames.elements();
		}

		public ServletContext getServletContext() {
			return new ServletContextImpl();
		}

		public String getServletName() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	public class ServletContextImpl implements ServletContext {

		public Object getAttribute(String name) {
			return null;
		}

		public Enumeration getAttributeNames() {
			return null;
		}

		public ServletContext getContext(String uripath) {
			return null;
		}

		public String getInitParameter(String name) {
			return null;
		}

		public Enumeration getInitParameterNames() {
			return null;
		}

		public int getMajorVersion() {
			return 0;
		}

		public String getMimeType(String file) {
			return null;
		}

		public int getMinorVersion() {
			return 0;
		}

		public RequestDispatcher getNamedDispatcher(String name) {
			return null;
		}

		public String getRealPath(String path) {
			return null;
		}

		public RequestDispatcher getRequestDispatcher(String path) {
			return null;
		}

		public URL getResource(String path) throws MalformedURLException {
			return null;
		}

		public InputStream getResourceAsStream(String path) {
			return null;
		}

		public Set getResourcePaths(String path) {
			return null;
		}

		public String getServerInfo() {
			return null;
		}

		public Servlet getServlet(String name) throws ServletException {
			return null;
		}

		public String getServletContextName() {
			return "unittest";
		}

		public Enumeration getServletNames() {
			return null;
		}

		public Enumeration getServlets() {
			return null;
		}

		public void log(Exception exception, String msg) {
		}

		public void log(String message, Throwable throwable) {
		}

		public void log(String msg) {
		}

		public void removeAttribute(String name) {
		}

		public void setAttribute(String name, Object object) {
		}
	}
}
