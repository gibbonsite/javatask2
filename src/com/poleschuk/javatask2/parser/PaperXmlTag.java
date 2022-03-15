package com.poleschuk.javatask2.parser;

public enum PaperXmlTag {
	PAPERS("papers"),
	PAPER("paper"),
	PAPERID("id"),
	PAPERNOTE("note"),
	TITLE("title"),
	TYPE("type"),
	COLOURED("coloured"),
	PAGESIZE("page-size"),
	PERIODICITY("periodicity"),
	HASSUBSCRIPTIONINDEX("has-subscription-index"),
	GLOSSY("glossy"),
	TOPIC("topic");

	private String value;
	PaperXmlTag(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
