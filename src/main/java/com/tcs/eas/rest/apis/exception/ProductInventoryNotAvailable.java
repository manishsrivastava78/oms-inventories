package com.tcs.eas.rest.apis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author 44745
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Inventory for product not available",value=HttpStatus.NOT_FOUND)
public class ProductInventoryNotAvailable extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6177494240148170519L;

	/**
	 * 
	 * @param message
	 */
	public ProductInventoryNotAvailable(String message) {
		super(message);
	}
}
