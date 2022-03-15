package com.poleschuk.javatask2.exception;

public class XMLParsingException extends Exception {
	public XMLParsingException() {
	}
	
	public XMLParsingException(String message) {
		super(message);
	}

	public XMLParsingException(Throwable throwable) {
		super(throwable);
	}

	public XMLParsingException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
