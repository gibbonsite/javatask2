package com.poleschuk.javatask2.main;


import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.TestException;
import org.testng.annotations.Test;

import com.poleschuk.javatask2.entity.PaperIssue;
import com.poleschuk.javatask2.exception.XMLParserConfigurationException;
import com.poleschuk.javatask2.exception.XMLParsingException;
import com.poleschuk.javatask2.parser.PaperDOMBuilder;

public class TestDOMParser {
	private static final String DOM_XML_FILENAME = "files/papers.xml";
	
    @Test
    public void testDOMParser() {
    	try {
    		PaperDOMBuilder builder = new PaperDOMBuilder();
    		builder.buildSetPapers(DOM_XML_FILENAME);
    		Set<PaperIssue> papers = builder.getPapers();
    		assertEquals(papers.size(), 16);
    	} catch (XMLParsingException | XMLParserConfigurationException e) {
    		throw new TestException("Error occured while reading XML.", e);
    	}
    }
}
