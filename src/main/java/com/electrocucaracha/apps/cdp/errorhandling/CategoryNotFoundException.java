package com.electrocucaracha.apps.cdp.errorhandling;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends Exception {

	public CategoryNotFoundException(String msg) {
		super(msg);
	}
}
