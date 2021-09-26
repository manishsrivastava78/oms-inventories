package com.tcs.eas.rest.apis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author 44745
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason="Product already exists",value=HttpStatus.BAD_REQUEST)
public class InventoryProductException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6177494240148170519L;

	/**
	 * 
	 * @param message
	 */
	public InventoryProductException(String message) {
		super(message);
	}
}
