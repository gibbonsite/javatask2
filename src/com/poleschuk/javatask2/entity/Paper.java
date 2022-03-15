package com.poleschuk.javatask2.entity;

import java.time.Period;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.poleschuk.javatask2.parser.PeriodAdapter;

public class Paper extends PaperIssue {
	@XmlElement(name = "has-subscription-index")
	private boolean hasSubscriptionIndex;
	@XmlElement(name = "periodicity")
    @XmlJavaTypeAdapter(PeriodAdapter.class)
	private Period periodicity;
	
	public Paper() {	
	}
	public Paper(PaperIssue paperIssue) {
		this.paperID = paperIssue.paperID;
		this.type = paperIssue.type;
		this.pageSize = paperIssue.pageSize;
		this.title = paperIssue.title;
		this.coloured = paperIssue.coloured;
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
		result = result + 31 * (hasSubscriptionIndex ? 0 : 1 + 31 * periodicity.hashCode());
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
		Paper other = (Paper) obj;
		return hasSubscriptionIndex == other.hasSubscriptionIndex && periodicity.equals(other.periodicity);
	}
	
}
