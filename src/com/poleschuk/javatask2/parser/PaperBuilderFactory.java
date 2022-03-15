package com.poleschuk.javatask2.parser;

import com.poleschuk.javatask2.exception.XMLParserConfigurationException;

public class PaperBuilderFactory {

	private enum TypeParser { 
		SAX,
		STAX,
		DOM
	}
	
	private PaperBuilderFactory() {
		
	}

	public static AbstractPaperBuilder createPaperBuilder(String typeParser) throws XMLParserConfigurationException {
		TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
		switch (type) {
			case DOM -> {
				return new PaperDOMBuilder();
			}
			case STAX -> {
				return new PaperStaxBuilder();
			}
			case SAX -> {
				return new PaperSAXBuilder();
			}
			default -> throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
		}
	}
}
