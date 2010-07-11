package net.freunds.softoff;

import static org.junit.Assert.*;

import java.util.Enumeration;

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

		public String getInitParameter(String name) {
			return "net.freunds.softoff.DemoTests:alwaysPasses,net.freunds.softoff.DemoTests:alwaysFails";
		}

		public Enumeration getInitParameterNames() {
			// TODO Auto-generated method stub
			return null;
		}

		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getServletName() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
