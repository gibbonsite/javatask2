package com.poleschuk.javatask2.entity;

import javax.xml.bind.annotation.XmlElement;

public class Booklet extends PaperIssue {
	@XmlElement(name = "glossy")
	private boolean glossy;
	private String topic = "";
	public Booklet() {	
	}
	public Booklet(PaperIssue paperIssue) {
		this.paperID = paperIssue.paperID;
		this.type = paperIssue.type;
		this.pageSize = paperIssue.pageSize;
		this.title = paperIssue.title;
		this.coloured = paperIssue.coloured;
	}
	public boolean isGlossy() {
		return glossy;
	}

	public void setGlossy(boolean glossy) {
		this.glossy = glossy;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result + 31 * (topic.hashCode() + 31 * (glossy ? 0 : 1));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Booklet other = (Booklet) obj;
		return glossy == other.glossy && topic.equals(other.topic);
	}
}
