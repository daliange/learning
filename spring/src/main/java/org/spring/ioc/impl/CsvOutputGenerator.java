package org.spring.ioc.impl;

import org.spring.ioc.IOutputGenerator;

public class CsvOutputGenerator implements IOutputGenerator
{
	public void generateOutput(){
		System.out.println("Csv Output Generator");
	}
}