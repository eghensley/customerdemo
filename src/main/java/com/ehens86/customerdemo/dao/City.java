package com.ehens86.customerdemo.dao;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.ehens86.customerdemo.enums.EntityTypeEnum;

@Document
public class City {

    @Field
    private EntityTypeEnum type;

    @Field
    private String state;
    
    @Field
    private String name;
    
    public City() {
    	
    }
    
    public City(String state, EntityTypeEnum type, String name) {
    	this.state = state;
    	this.type = type;
    	this.name = name;
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "City{" +
				"type=" + type +
				", state='" + state + '\'' +
				", name='" + name + '\'' +
				'}';
	}


}
