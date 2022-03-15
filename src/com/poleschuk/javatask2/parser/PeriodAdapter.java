package com.poleschuk.javatask2.parser;

import java.time.Period;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PeriodAdapter extends XmlAdapter<String, Period> {

    @Override
    public Period unmarshal(String v) throws Exception {
        return Period.parse(v);
    }

    @Override
    public String marshal(Period v) throws Exception {
        return v.toString();
    }

}
