package com.poleschuk.javatask2.parser;

import java.time.Period;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.poleschuk.javatask2.entity.Booklet;
import com.poleschuk.javatask2.entity.Magazine;
import com.poleschuk.javatask2.entity.Paper;
import com.poleschuk.javatask2.entity.PaperIssue;

public class PaperHandler extends DefaultHandler {
	public static final String ELEMENT_PAPER = "paper";
	public static final String BOOKLET_TYPE = "Booklet";
	public static final String MAGAZINE_TYPE = "Magazine";
	public static final String PAPER_TYPE = "Paper";
	private Set<PaperIssue> papers;
	private PaperIssue current;
	private PaperXmlTag currentXmlTag;
	private EnumSet<PaperXmlTag> withText;
	
	
	public PaperHandler() {
		papers = new HashSet<PaperIssue>();
		withText = EnumSet.range(PaperXmlTag.TITLE, PaperXmlTag.GLOSSY);
	}					
	
	public Set<PaperIssue> getPapers() {
		return papers;					
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attrs) {					
		if (ELEMENT_PAPER.equals(qName)) {
			current = new PaperIssue();
			String firstAttr = attrs.getValue(0);
			if (firstAttr != null) {
				if ("id".equals(attrs.getLocalName(0))) {
					current.setPaperID(firstAttr);
				} else {
					current.setPaperNote(firstAttr);
				}
			}
			if (attrs.getLength() == 2) {
				String secondAttr = attrs.getValue(1);
				if (secondAttr != null) {
					if ("id".equals(attrs.getLocalName(1))) {
						current.setPaperID(secondAttr);
					} else {
						current.setPaperNote(secondAttr);
					}
				}
			}
		} else {
			qName = qName.replaceAll("-", "");
			PaperXmlTag temp = PaperXmlTag.valueOf(qName.toUpperCase());
			if (withText.contains(temp)) {					
				currentXmlTag = temp;
			}
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (ELEMENT_PAPER.equals(qName)) {
			papers.add(current);
		}					
	}
	
	public void characters(char[] ch, int start, int length) {					
		String data = new String(ch, start, length).strip();
		if (currentXmlTag!= null) {					
			switch (currentXmlTag) {
				case TITLE -> current.setTitle(data);
				case TYPE -> current.setType(data);
				case PAGESIZE -> current.setPageSize(Integer.parseInt(data));
				case COLOURED -> current.setColoured(Boolean.parseBoolean(data));
				case GLOSSY -> {
					if (MAGAZINE_TYPE.equals(current.getType())) {
						Magazine magazine = new Magazine(current);
						magazine.setGlossy(Boolean.parseBoolean(data));
						current = magazine;
					} else if (BOOKLET_TYPE.equals(current.getType())) {
						Booklet booklet = new Booklet(current);
						booklet.setGlossy(Boolean.parseBoolean(data));
						current = booklet;
					}
				}
				case PERIODICITY -> {
					if (MAGAZINE_TYPE.equals(current.getType())) {
						Magazine magazine = new Magazine(current);
						magazine.setPeriodicity(Period.parse(data));
						current = magazine;
					} else if (PAPER_TYPE.equals(current.getType())) {
						Paper paper = new Paper(current);
						paper.setPeriodicity(Period.parse(data));
						current = paper;
					} else {
						Booklet booklet = new Booklet(current);
						booklet.setTopic(data);
						current = booklet;
					}
				}
				case HASSUBSCRIPTIONINDEX -> {
					if (MAGAZINE_TYPE.equals(current.getType())) {
						Magazine magazine = new Magazine(current);
						magazine.setHasSubscriptionIndex(Boolean.parseBoolean(data));
						current = magazine;
					} else if (PAPER_TYPE.equals(current.getType())) {
						Paper paper = new Paper(current);
						paper.setHasSubscriptionIndex(Boolean.parseBoolean(data));
						current = paper;
					} else {
						Booklet booklet = new Booklet(current);
						current = booklet;
					}
				}
				default -> throw new EnumConstantNotPresentException(currentXmlTag.getDeclaringClass(), 
						currentXmlTag.name());					
			}
		}
		currentXmlTag = null;
	}
}
