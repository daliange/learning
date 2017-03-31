package org.spring.setfactorybean;

import java.util.Set;

public class Customer 
{
	private Set sets;
	//...
	
	public String toString() {
		return "Customer [sets=" + sets + "]" ;
	}

	public Set getSets() {
		return sets;
	}

	public void setSets(Set sets) {
		this.sets = sets;
	}
	
	

}
