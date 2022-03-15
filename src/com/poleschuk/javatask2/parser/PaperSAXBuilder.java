package com.poleschuk.javatask2.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.poleschuk.javatask2.exception.XMLParserConfigurationException;
import com.poleschuk.javatask2.exception.XMLParsingException;

public class PaperSAXBuilder extends AbstractPaperBuilder {
	private static Logger logger = LogManager.getLogger();
	private PaperHandler handler = new PaperHandler();
	private XMLReader reader;
	
	public PaperSAXBuilder() throws XMLParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			reader = saxParser.getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
			logger.error("Configuration failed");
			throw new XMLParserConfigurationException("Configuration failed", e);
		}
		reader.setContentHandler(handler);
	}
	
	public void buildSetPapers(String filename) throws XMLParsingException {
		try {
			reader.parse(filename);
		} catch (IOException | SAXException e) { 
			logger.error("Parsing failed");
			throw new XMLParsingException("Parsing failed", e);
		}
		
		papers = handler.getPapers(); 
	}
}
