package com.poleschuk.javatask2.main;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.TestException;
import org.testng.annotations.Test;

import com.poleschuk.javatask2.entity.PaperIssue;
import com.poleschuk.javatask2.exception.XMLParsingException;
import com.poleschuk.javatask2.parser.PaperStaxBuilder;

public class TestStaxParser {
	private static final String STAX_XML_FILENAME = "files/papers.xml";
	
    @Test
    public void testStaxParser() {
    	try {
    		PaperStaxBuilder builder = new PaperStaxBuilder();
    		builder.buildSetPaperIssues(STAX_XML_FILENAME);
    		Set<PaperIssue> papers = builder.getPapers();
    		assertEquals(papers.size(), 16);
    	} catch (XMLParsingException e) {
    		throw new TestException("Error occured while reading XML.", e);
    	}
    }
}
