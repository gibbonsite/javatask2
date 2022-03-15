package com.poleschuk.javatask2.exception;

public class XMLParserConfigurationException extends Exception {
	public XMLParserConfigurationException() {
	}
	
	public XMLParserConfigurationException(String message) {
		super(message);
	}

	public XMLParserConfigurationException(Throwable throwable) {
		super(throwable);
	}

	public XMLParserConfigurationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
