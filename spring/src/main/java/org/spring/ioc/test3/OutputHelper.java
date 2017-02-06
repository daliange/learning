package org.spring.ioc.test3;

import org.spring.ioc.IOutputGenerator;

public class OutputHelper
{
	IOutputGenerator outputGenerator;
	
	public void generateOutput(){
		outputGenerator.generateOutput();
	}
	
	public void setOutputGenerator(IOutputGenerator outputGenerator){
		this.outputGenerator = outputGenerator;
	}
}