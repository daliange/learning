package org.spring.mapfactorybean;

import java.util.List;
import java.util.Map;

public class Customer 
{
	private Map maps;
	//...
	
	public String toString() {
		return "Customer [lists=" + maps + "]" ;
	}

	public Map getMaps() {
		return maps;
	}

	public void setMaps(Map maps) {
		this.maps = maps;
	}

	
}
