package org.spring.helloworld;

/**
 * Spring bean
 * 
 */
public class HahaWorld {
	private String name;
	private String address;

	public void setName(String name) {
		this.name = name;
	}
	

	public void setAddress(String address) {
		this.address = address;
	}



	public void printHello() {
		System.out.println("Spring 3 : Hello ! " + name + "   " +address);
	}
}