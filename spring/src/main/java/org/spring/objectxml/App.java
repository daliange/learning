package org.spring.objectxml;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
private static final String XML_FILE_NAME = "customer.xml";
	
	public static void main(String[] args) throws IOException, JAXBException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("objectxml/App.xml");
		XMLConverter converter = (XMLConverter) appContext.getBean("XMLConverter");
		
		Customer customer = new Customer();
		customer.setName("yiibai");
		customer.setAge(28);
		customer.setFlag(true);
		customer.setAddress("Haikou haidiandao");
		
		System.out.println("Convert Object to XML!");
		//from object to XML file
		converter.convertFromObjectToXML(customer, XML_FILE_NAME);
		System.out.println("Done \n");
		
		System.out.println("Convert XML back to Object!");
		//from XML to object
		Customer customer2 = (Customer)converter.convertFromXMLToObject(XML_FILE_NAME);
		System.out.println(customer2);
		System.out.println("Done");
		
	}

}
