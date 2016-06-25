package com.electrocucaracha.apps.cdp.errorhandling;

@SuppressWarnings("serial")
public class InvalidInputException extends Exception {

	public InvalidInputException(String msg){
		super(msg);
	}
}
