package org.spring.ioc.impl;

import org.spring.ioc.IOutputGenerator;

public class JsonOutputGenerator implements IOutputGenerator
{
	public void generateOutput(){
		System.out.println("Json Output Generator");
	}
}