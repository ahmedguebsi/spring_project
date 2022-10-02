package com.bezkoder.spring.hibernate.manytomany.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EntryUpdatePair {
	
	public Entry entry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date revisionDate; // this field is of type java.util.Date (not java.sql.Date)

    EntryUpdatePair() {}

    public EntryUpdatePair(Entry entry, Date revisionDate) {
        this.entry = entry;
        this.revisionDate = revisionDate;
    }

    public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	

    
}