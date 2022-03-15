package com.poleschuk.javatask2.parser;

import java.io.IOException;
import java.time.Period;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.poleschuk.javatask2.entity.Booklet;
import com.poleschuk.javatask2.entity.Magazine;
import com.poleschuk.javatask2.entity.Paper;
import com.poleschuk.javatask2.entity.PaperIssue;
import com.poleschuk.javatask2.exception.XMLParserConfigurationException;
import com.poleschuk.javatask2.exception.XMLParsingException;

public class PaperDOMBuilder extends AbstractPaperBuilder {
	private static Logger logger = LogManager.getLogger();
	public static final String MAGAZINETYPE = "Magazine";
	public static final String PAPERTYPE = "Paper";
	private DocumentBuilder docBuilder;
	
	public PaperDOMBuilder() throws XMLParserConfigurationException {					
		papers = new HashSet<PaperIssue>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {					
			logger.error("Parsing failed");
			throw new XMLParserConfigurationException("Parsing failed", e);
		}
	}
	
	public void buildSetPapers(String filename) throws XMLParsingException {
		Document doc;
		try {
			doc = docBuilder.parse(filename);
			Element root = doc.getDocumentElement();
			NodeList papersList = root.getElementsByTagName(PaperXmlTag.PAPER.getValue());
			for (int i = 0; i < papersList.getLength(); i++) {			
				Element paperElement = (Element) papersList.item(i);
				PaperIssue paper = buildPaper(paperElement);
				papers.add(paper);					
			}
		} catch (IOException | SAXException e) {					
			logger.error("Parsing failed");
			throw new XMLParsingException("Parsing failed", e);
		}
	}
		
	private PaperIssue buildPaper(Element paperElement) {					
		PaperIssue paperIssue = new PaperIssue();

		String idValue = paperElement.getAttribute(PaperXmlTag.PAPERID.getValue());
		if (idValue != null) {
			paperIssue.setPaperID(idValue);
		}
		String noteValue = paperElement.getAttribute(PaperXmlTag.PAPERNOTE.getValue());
		if (idValue != null) {
			paperIssue.setPaperNote(noteValue);
		}
		
		paperIssue.setTitle(getElementTextContent(paperElement, PaperXmlTag.TITLE.getValue()));
		paperIssue.setType(getElementTextContent(paperElement, PaperXmlTag.TYPE.getValue()));
		paperIssue.setColoured(Boolean.parseBoolean(getElementTextContent(paperElement, PaperXmlTag.COLOURED.getValue())));
		paperIssue.setPageSize(Integer.parseInt(getElementTextContent(paperElement, PaperXmlTag.PAGESIZE.getValue())));		
		if (MAGAZINETYPE.equals(paperIssue.getType())) {
			Magazine magazine = new Magazine(paperIssue);
			magazine.setGlossy(Boolean.parseBoolean(getElementTextContent(paperElement, PaperXmlTag.GLOSSY.getValue())));
			magazine.setPeriodicity(Period.parse(getElementTextContent(paperElement, PaperXmlTag.PERIODICITY.getValue())));
			magazine.setHasSubscriptionIndex(Boolean.parseBoolean(getElementTextContent(paperElement, PaperXmlTag.HASSUBSCRIPTIONINDEX.getValue())));
			paperIssue = magazine;
		} else if (PAPERTYPE.equals(paperIssue.getType())) {
			Paper paper = new Paper(paperIssue);
			paper.setPeriodicity(Period.parse(getElementTextContent(paperElement, PaperXmlTag.PERIODICITY.getValue())));
			paper.setHasSubscriptionIndex(Boolean.parseBoolean(getElementTextContent(paperElement, PaperXmlTag.HASSUBSCRIPTIONINDEX.getValue())));
			paperIssue = paper;
		} else {
			Booklet booklet = new Booklet(paperIssue);
			booklet.setGlossy(Boolean.parseBoolean(getElementTextContent(paperElement, PaperXmlTag.GLOSSY.getValue())));
			booklet.setTopic(getElementTextContent(paperElement, PaperXmlTag.TOPIC.getValue()));
			paperIssue = booklet;
		}
		return paperIssue;
	}

	private static String getElementTextContent(Element element, String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = nList.item(0);
		return node.getTextContent();
	}
}
