package com.sap.startupfocus.hana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENT")
public class Patient {
	   @Id
	   @GeneratedValue
	   @Column(name = "id")
	   private int id;

	   @Column(name = "first_name")
	   private String firstName;

	   @Column(name = "last_name")
	   private String lastName;

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
