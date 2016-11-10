package com.sap.startupfocus.hana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"com.sap.startupfocus.hana.db::medical.patient\"")
public class Patient {
	@Id
	@Column(name = ID)
	private int id;

	@Column(name = FIRST_NAME)
	private String firstName;

	@Column(name = LAST_NAME)
	private String lastName;

	private final static String ID = "\"id\"";
	private final static String FIRST_NAME = "\"first_name\"";
	private final static String LAST_NAME = "\"last_name\"";
	
	public int getId() {
		return id;
	}
	public void setId( int id ) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName( String first_name ) {
		this.firstName = first_name;
	}
	public String getLastName() {
	   return lastName;
	}
	public void setLastName( String last_name ) {
		this.lastName = last_name;
	}
}
