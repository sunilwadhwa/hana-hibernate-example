package com.sap.startupfocus.hana;

import java.util.List; 
	 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class PatientManager {
	   private static SessionFactory factory;
	   
	   @SuppressWarnings("unused")
	   public static void main(String[] args) {
	      try {
	         factory = new Configuration().
	                   configure().
	                   addAnnotatedClass(Patient.class).
	                   buildSessionFactory();
	      } catch (Throwable ex) {
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex);
	      }
	      
	      PatientManager pManager = new PatientManager ();

	      /* Add a few records in database */
	      Integer patientId1 = pManager.addPatient("Jack", "Wolf");
	      Integer patientId2 = pManager.addPatient("Deepak", "Das");
	      Integer patientId3 = pManager.addPatient("John", "Jones");

	      /* List all patients */
	      pManager.listPatients();

	      /* Delete a patient from the database */
	      pManager.deletePatient(patientId2);

	      /* Show updated list */
	      pManager.listPatients();
	   }
	   
	   /* Method to create a patient in the database */
	   public Integer addPatient (String fname, String lname) {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer patientId = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Patient patient = new Patient();
	         patient.setFirstName(fname);
	         patient.setLastName(lname);
	         patientId = (Integer) session.save(patient);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         e.printStackTrace();
	      } finally {
	         session.close(); 
	      }
	      return patientId;
	   }
	   
	   /* Method to list all patients */
	   public void listPatients () {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List<Patient> patientList = session.createQuery("from Patient", Patient.class).getResultList();
	         for (Patient p : patientList) {
	            System.out.print(p.getId() + ": " + p.getFirstName() + " " + p.getLastName());
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	   
	   /* Method to delete a patient from the records */
	   public void deletePatient (Integer patientId) {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try {
	         tx = session.beginTransaction();
	         Patient p = (Patient) session.get(Patient.class, patientId); 
	         session.delete(p);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	    	  session.close(); 
	      }
	   }
}
