package com.poleschuk.javatask2.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Period;
import java.util.HashSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.javatask2.entity.Booklet;
import com.poleschuk.javatask2.entity.Magazine;
import com.poleschuk.javatask2.entity.Paper;
import com.poleschuk.javatask2.entity.PaperIssue;
import com.poleschuk.javatask2.exception.XMLParsingException;

public class PaperStaxBuilder extends AbstractPaperBuilder {
	public static final String ID_ATTRIBUTE = "id";
	public static final String BOOKLET_TYPE = "Booklet";
	public static final String MAGAZINE_TYPE = "Magazine";
	public static final String PAPER_TYPE = "Paper";
	private static Logger logger = LogManager.getLogger();

	private XMLInputFactory inputFactory;
	
	public PaperStaxBuilder() {
		inputFactory = XMLInputFactory.newInstance();
		papers = new HashSet<PaperIssue>();
	}

	public void buildSetPaperIssues(String filename) throws XMLParsingException {
	
		XMLStreamReader reader;
		String name;
		try(FileInputStream inputStream = new FileInputStream(new File(filename))) {
		
			reader = inputFactory.createXMLStreamReader(inputStream);
			
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (name.equals(PaperXmlTag.PAPER.getValue())) {
						PaperIssue paper = buildPaper(reader);
						papers.add(paper);
					}
			
				}
			}
		
		} catch (XMLStreamException | IOException e) {
			logger.error("Parsing failed");
			throw new XMLParsingException("Parsing failed", e);
		} 
	
	}



	private PaperIssue buildPaper(XMLStreamReader reader) throws XMLStreamException {
		PaperIssue paperIssue = new PaperIssue();
		String firstAttrValue = reader.getAttributeValue(0);
		String firstAttrName = reader.getAttributeLocalName(0);
		if (firstAttrValue != null) {
			if (ID_ATTRIBUTE.equals(firstAttrName)) {
				paperIssue.setPaperID(firstAttrValue);
			} else {
				paperIssue.setPaperNote(firstAttrValue);
				
			}
		}
		if (reader.getAttributeCount() > 1) {
			String secondAttrValue = reader.getAttributeValue(1);
			String secondAttrName = reader.getAttributeLocalName(1);
			if (secondAttrValue != null) {
				if (ID_ATTRIBUTE.equals(secondAttrName)) {
					paperIssue.setPaperID(secondAttrValue);
				} else {
					paperIssue.setPaperNote(secondAttrValue);
				}
			}
		}
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					name = reader.getLocalName();
					name = name.replaceAll("-", "");
					switch (PaperXmlTag.valueOf(name.toUpperCase())) {
						case TITLE -> paperIssue.setTitle(getXMLText(reader));
						case TYPE -> paperIssue.setType(getXMLText(reader));
						case COLOURED -> paperIssue.setColoured(Boolean.parseBoolean(getXMLText(reader)));
						case PAGESIZE -> paperIssue.setPageSize(Integer.parseInt(getXMLText(reader)));
						case GLOSSY -> {
							if (MAGAZINE_TYPE.equals(paperIssue.getType())) {
								Magazine magazine = new Magazine(paperIssue);
								magazine.setGlossy(Boolean.parseBoolean(getXMLText(reader)));
								paperIssue = magazine;
							} else if (BOOKLET_TYPE.equals(paperIssue.getType())) {
								Booklet booklet = new Booklet(paperIssue);
								booklet.setGlossy(Boolean.parseBoolean(getXMLText(reader)));
								paperIssue = booklet;
							}
						}
						case PERIODICITY -> {
							if (MAGAZINE_TYPE.equals(paperIssue.getType())) {
								Magazine magazine = new Magazine(paperIssue);
								magazine.setPeriodicity(Period.parse(getXMLText(reader)));
								paperIssue = magazine;
							} else if (PAPER_TYPE.equals(paperIssue.getType())) {
								Paper paper = new Paper(paperIssue);
								paper.setPeriodicity(Period.parse(getXMLText(reader)));
								paperIssue = paper;
							} else {
								Booklet booklet = new Booklet(paperIssue);
								paperIssue = booklet;
							}
						}
						case HASSUBSCRIPTIONINDEX -> {
							if (MAGAZINE_TYPE.equals(paperIssue.getType())) {
								Magazine magazine = new Magazine(paperIssue);
								magazine.setHasSubscriptionIndex(Boolean.parseBoolean(getXMLText(reader)));
								paperIssue = magazine;
							} else if (PAPER_TYPE.equals(paperIssue.getType())) {
								Paper paper = new Paper(paperIssue);
								paper.setHasSubscriptionIndex(Boolean.parseBoolean(getXMLText(reader)));
								paperIssue = paper;
							} else {
								Booklet booklet = new Booklet(paperIssue);
								booklet.setTopic(getXMLText(reader));
								paperIssue = booklet;
							}
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					name = reader.getLocalName();
					name = name.replaceAll("-", "");
					if (PaperXmlTag.valueOf(name.toUpperCase()) == PaperXmlTag.PAPER) {
						return paperIssue;
					}
			}
		}
		throw new XMLStreamException("Unknown element in tag <paper>.");
	}
				
	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}

} 
