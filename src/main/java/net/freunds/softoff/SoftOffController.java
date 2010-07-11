package net.freunds.softoff;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.lang.reflect.Method;

public class SoftOffController {
	private Logger log = Logger.getLogger(SoftOffController.class.getName());
	private Map<Object, Method> availabilityTests = new HashMap<Object, Method>();
	
	public void addTest(String className, String methodName) throws SecurityException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException{
		Class targetClass = Class.forName(className);
		targetClass.getMethod(methodName);
		addTest(targetClass, targetClass.getMethod(methodName));
	}
	
	public void addTest(Class targetClass, Method testMethod) throws IllegalAccessException, InstantiationException{
		addTest(targetClass.newInstance(), testMethod);
	}
	
	public void addTest(Object target, Method testMethod){
		availabilityTests.put(target, testMethod);
	}
	
	public boolean available(){
		for(Object target : availabilityTests.keySet()){
			Method testMethod = availabilityTests.get(target);
			try {
				Object rc = testMethod.invoke(target);
				if(rc == null){
					log.fine(target.getClass().getName() + ":" + testMethod.getName() + " marks system as down with null return code");
					return false;
				} else if (Boolean.class.isInstance(rc)){
					if(!((Boolean)rc).booleanValue()){
						log.fine(target.getClass().getName() + ":" + testMethod.getName() + " marks system as down with false return code");
						return false;
					}
				} else {
					log.fine(target.getClass().getName() + ":" + testMethod.getName() + " marks system as down with invalid return type: " + rc.getClass().getName());
					return false;
				}
			} catch (Exception e) {
				log.fine(target.getClass().getName() + ":" + testMethod.getName() + " marks system as down with exception: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
}
