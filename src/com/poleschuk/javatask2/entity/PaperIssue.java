package com.poleschuk.javatask2.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class PaperIssue {
	@XmlElement(name = "title")
	protected String title;
	@XmlID
	@XmlAttribute(name = "id")
	protected String paperID;
	@XmlAttribute(name = "note")
	protected String paperNote;
	@XmlElement(name = "type")
	protected String type;
	@XmlElement(name = "coloured")
	protected boolean coloured;
	@XmlElement(name = "page-size")
	protected int pageSize;

	public PaperIssue() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaperID() {
		return paperID;
	}

	public void setPaperID(String paperID) {
		this.paperID = paperID;
	}

	public String getPaperNote() {
		return paperNote;
	}

	public void setPaperNote(String paperNote) {
		this.paperNote = paperNote;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isColoured() {
		return coloured;
	}

	public void setColoured(boolean coloured) {
		this.coloured = coloured;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o.getClass() != PaperIssue.class) {
			return false;
		}
		PaperIssue paper = (PaperIssue) o;
		return paper.paperID.equals(paperID) &&
			    paper.type.equals(type) &&
			    paper.pageSize == pageSize &&
			    paper.coloured == coloured &&
			    paper.title.equals(title);
	}
	
	@Override
	public int hashCode() {
		return paperID.hashCode() + 31 *(
			    type.hashCode() + 31 *(
			    pageSize + 31 *(
			    coloured ? 0 : 1 + 31 *(
			    title.hashCode()))));
	}
	
}
