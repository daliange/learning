package org.spring.ioc.test2;

import org.spring.ioc.IOutputGenerator;
import org.spring.ioc.impl.CsvOutputGenerator;

public class OutputHelper
{
	IOutputGenerator outputGenerator;
	
	public OutputHelper(){
		outputGenerator = new CsvOutputGenerator();
	}
	
	public void generateOutput(){
		outputGenerator.generateOutput();
	}
	
}