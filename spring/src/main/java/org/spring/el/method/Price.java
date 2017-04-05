package org.spring.el.method;

import org.springframework.stereotype.Component;

@Component("priceBean")
public class Price {
	
	public double getSpecialPrice() {
		return new Double(199.09);
	}

}
