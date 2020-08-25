package com.ehens86.customerdemo.dao;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.ehens86.customerdemo.enums.EntityTypeEnum;

@Document
public class Country {
	
    @Field
    private EntityTypeEnum type;

    @Field
    private String name;
    
    @Field
    private String abbrev;
    
    public Country() {
    	
    }
    
    public Country(EntityTypeEnum type, String name, String abbrev) {
    	this.type = type;
    	this.name = name;
    	this.abbrev = abbrev;
    }

	/**
	 * @return the type
	 */
	public EntityTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EntityTypeEnum type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the abbrev
	 */
	public String getAbbrev() {
		return abbrev;
	}

	/**
	 * @param abbrev the abbrev to set
	 */
	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	} 
    
}
