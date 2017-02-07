package org.spring.ioc.test4;

import org.spring.ioc.IOutputGenerator;

public class OutputHelper
{
	IOutputGenerator outputGenerator;
	
	OutputHelper(IOutputGenerator outputGenerator){
		this.outputGenerator = outputGenerator;
	}
	
	public void generateOutput(){
		outputGenerator.generateOutput();
	}
	
}