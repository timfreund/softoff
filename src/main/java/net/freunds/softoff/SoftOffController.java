package net.freunds.softoff;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.lang.reflect.Method;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public class SoftOffController {
	private Logger log = Logger.getLogger(SoftOffController.class.getName());
	private Map<Object, Method> availabilityTests = new HashMap<Object, Method>();
	private SoftOff softOff = null;
	private String instanceName = "default";
	private ObjectName softOffObjectName = null;
	
	public SoftOffController(){

	}
	
	public SoftOffController(String instanceName){
		this.instanceName = instanceName;
		this.initialize();
	}
	
	public void initialize(){
		try {
			softOffObjectName = new ObjectName("net.freunds.softoff:type=SoftOff,name=" + instanceName);

			Iterator iter = MBeanServerFactory.findMBeanServer(null).iterator();
			softOff = new SoftOff();
			while(iter.hasNext()){
				MBeanServer server = (MBeanServer)iter.next();
				if(server.isRegistered(softOffObjectName)){
					server.unregisterMBean(softOffObjectName);
				}
				server.registerMBean(softOff, softOffObjectName);
				addTest(softOff, softOff.getClass().getMethod("getEnabled"));
			}	
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public void shutdown(){
		Iterator iter = MBeanServerFactory.findMBeanServer(null).iterator();
		softOff = new SoftOff();
		while(iter.hasNext()){
			MBeanServer server = (MBeanServer)iter.next();
			try {
				server.unregisterMBean(softOffObjectName);
			} catch (JMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
}
