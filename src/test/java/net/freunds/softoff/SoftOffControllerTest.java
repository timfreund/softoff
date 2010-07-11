package net.freunds.softoff;

import static org.junit.Assert.*;

import org.junit.Test;

public class SoftOffControllerTest {
	private SoftOffController controller = null;
	
	public SoftOffControllerTest(){
		controller = new SoftOffController();
	}
	
	@Test
	public void testAddTestStringString() {
		try {
			controller.addTest("net.freunds.softoff.DemoTests", "alwaysPasses");
			assertTrue(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAddTestClassMethod() {
		try {
			controller.addTest(DemoTests.class, DemoTests.class.getMethod("alwaysPasses"));
			assertTrue(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAddTestObjectMethod() {
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("alwaysPasses"));
			assertTrue(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAvailable() {
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("alwaysPasses"));
			assertTrue(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAvailableFalseReturn(){
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("alwaysFails"));
			assertFalse(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}
	
	@Test
	public void testAvailableNullReturn(){
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("voidReturnType"));
			assertFalse(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAvailableStringReturn(){
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("stringReturnType"));
			assertFalse(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}

	@Test
	public void testAvailableMultipleTests(){
		try {
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("stringReturnType"));
			controller.addTest(new DemoTests(), DemoTests.class.getMethod("alwaysPasses"));
			assertFalse(controller.available());
		} catch (Exception e) {
			fail("Exception caught: " + e.getMessage());
		}
	}
}
