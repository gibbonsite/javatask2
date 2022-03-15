package com.poleschuk.javatask2.main;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.testng.TestException;
import org.testng.annotations.Test;

import com.poleschuk.javatask2.entity.Booklet;
import com.poleschuk.javatask2.entity.Magazine;
import com.poleschuk.javatask2.entity.Paper;
import com.poleschuk.javatask2.entity.PaperIssue;
import com.poleschuk.javatask2.entity.Papers;

public class TestJAXBParser {
	private static final String JAXB_XML_FILENAME = "files/papers.xml";
	
    @Test
    public void testUnmarshal() {
    	try {
	        JAXBContext context = JAXBContext.newInstance(Papers.class, PaperIssue.class,
	        		Paper.class, Magazine.class, Booklet.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        Papers papers = (Papers) unmarshaller.unmarshal(new FileReader(JAXB_XML_FILENAME));
	        List<PaperIssue> listOfPapers = papers.getListOfPapers();
    		assertEquals(listOfPapers.size(), 16);
	    } catch (JAXBException | FileNotFoundException e) {
	    	throw new TestException("Error occured while reading XML.", e);
	    }
    }
}
