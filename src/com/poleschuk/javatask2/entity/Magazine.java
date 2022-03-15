package com.poleschuk.javatask2.entity;

import java.time.Period;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.poleschuk.javatask2.parser.PeriodAdapter;

public class Magazine extends PaperIssue {
	@XmlElement(name = "glossy")
	protected boolean glossy;
	@XmlElement(name = "has-subscription-index")
	private boolean hasSubscriptionIndex;
	@XmlElement(name = "periodicity")
    @XmlJavaTypeAdapter(PeriodAdapter.class)
	private Period periodicity;
	
	public Magazine() {
	}
	public Magazine(PaperIssue paperIssue) {
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
	public boolean isHasSubscriptionIndex() {
		return hasSubscriptionIndex;
	}
	public void setHasSubscriptionIndex(boolean hasSubscriptionIndex) {
		this.hasSubscriptionIndex = hasSubscriptionIndex;
	}
	public Period getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Period periodicity) {
		this.periodicity = periodicity;
	}
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result + 31 * (hasSubscriptionIndex ? 0 : 1 + 31 * (
				periodicity.hashCode() + 31 * (glossy ? 0 : 1)));
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
		Magazine other = (Magazine) obj;
		return glossy == other.glossy && hasSubscriptionIndex == other.hasSubscriptionIndex &&
				periodicity.equals(other.periodicity);
	}
}