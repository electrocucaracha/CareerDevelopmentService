package com.electrocucaracha.apps.cdp.errorhandling;

@SuppressWarnings("serial")
public class TaskNotFoundException extends Exception {

	public TaskNotFoundException(String msg) {
		super(msg);
	}
}
