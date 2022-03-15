package com.poleschuk.javatask2.parser;

import java.util.HashSet;
import java.util.Set;

import com.poleschuk.javatask2.entity.PaperIssue;

public abstract class AbstractPaperBuilder {
	protected Set<PaperIssue> papers;
	
	public AbstractPaperBuilder() {
		papers = new HashSet<PaperIssue>();
	}
	
	public AbstractPaperBuilder(Set<PaperIssue> papers) {
		this.papers = papers;
	}
	
	public Set<PaperIssue> getPapers() {
		return papers;
	}
// 1 08 41
}
