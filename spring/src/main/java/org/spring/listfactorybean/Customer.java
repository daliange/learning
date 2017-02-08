package org.spring.listfactorybean;

import java.util.List;

public class Customer 
{
	private List lists;
	//...
	
	public String toString() {
		return "Customer [lists=" + lists + "]" ;
	}

	public List getLists() {
		return lists;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}
	
}
