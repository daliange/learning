package org.spring.ioc.test1;

import org.spring.ioc.IOutputGenerator;
import org.spring.ioc.impl.CsvOutputGenerator;
import org.spring.ioc.impl.JsonOutputGenerator;

/**
 * 直接调用
 * 耦合程度高
 * **/
public class Test1 
{
    public static void main( String[] args )
    {
    	IOutputGenerator output = new CsvOutputGenerator();
    	output.generateOutput();
    	
    	output = new JsonOutputGenerator();
    	output.generateOutput();
    	
    }
}