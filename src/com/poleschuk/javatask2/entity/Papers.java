package com.poleschuk.javatask2.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="papers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Papers {

    @XmlElement(name="paper")
    private List<PaperIssue> listOfPapers;

    public List<PaperIssue> getListOfPapers() {
        return listOfPapers;
    }
}
