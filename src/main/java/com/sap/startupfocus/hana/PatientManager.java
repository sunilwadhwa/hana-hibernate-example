package com.sap.startupfocus.hana;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PatientManager {
	private static SessionFactory factory;
	private final static Logger log = LogManager.getLogger(PatientManager.class);
	   
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			factory = new Configuration().
					configure().
					addAnnotatedClass(Patient.class).
					buildSessionFactory();
		} catch (Exception ex) {
			log.error("Failed to create sessionFactory object.", ex);
			System.exit (-1);
		}
	      
		PatientManager pManager = new PatientManager ();

		/* Add a few records in database */
		Integer patientId1 = pManager.addPatient(101, "Jack", "Wolf");
		Integer patientId2 = pManager.addPatient(102, "Deepak", "Das");
		Integer patientId3 = pManager.addPatient(103, "John", "Jones");

		/* List all patients */
		pManager.listPatients();

		/* Delete a patient from the database */
		pManager.deletePatient(patientId2);

		/* Show updated list */
		pManager.listPatients();
		
		factory.close();
	}
	   
	/* Method to create a patient in the database */
	public Integer addPatient (Integer patientId, String fname, String lname) {
		Session session = factory.openSession();
	      
		Patient patient = new Patient();
		patient.setFirstName(fname);
		patient.setLastName(lname);
		patient.setId(patientId.intValue());
	      
		Transaction txn = session.beginTransaction();
		try {
			Object o = session.save(patient);
			log.debug("Value of save return = {}", o);
			txn.commit();
		} catch (HibernateException e) {
			log.error("Exception received while adding patient.", e);
			if (txn != null) txn.rollback();
			return -1;
	    } finally {
	         session.close();
	    }
	      
		return patientId;
	}
	   
	/* Method to list all patients */
	public void listPatients () {
		Session session = factory.openSession();
	      
		try {
			List<Patient> patientList = session.createQuery("from Patient", Patient.class).getResultList();
			for (Patient p : patientList) {
				log.info("{}: {} {}", p.getId(), p.getFirstName(), p.getLastName());
	        }
		} catch (HibernateException e) {
			log.error("Exception received while listing patients.", e);
			return;
	    } finally {
	        session.close();
	    }
	}

	/* Method to delete a patient from the records */
	public void deletePatient (Integer patientId) {
		Session session = factory.openSession();
		Transaction txn = session.beginTransaction();
	      
		try {
			Patient p = (Patient) session.get(Patient.class, patientId);
			session.delete(p);
			txn.commit();
		} catch (HibernateException e) {
			log.error("Exception received while deleting patient.", e);
			if (txn != null) txn.rollback();
			return;
		} finally {
			session.close();
		}
	}
}
