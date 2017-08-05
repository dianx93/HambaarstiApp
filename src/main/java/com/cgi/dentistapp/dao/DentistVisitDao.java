package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

import java.util.Date;
import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistVisitEntity visit) {
        entityManager.persist(visit);
    }

    public List<DentistVisitEntity> getAllVisits() {
        return entityManager.createQuery("SELECT e FROM DentistVisitEntity e").getResultList();
    }

	public List<DentistVisitEntity> getVisitsByParameters(String dentistName,
			Date visitDate, Date visitTime) {
		StringBuilder sb = new StringBuilder();
		if(dentistName != null) sb.append(" AND dentist_name = '" + dentistName + "'");
		if(visitDate != null) sb.append(" AND visit_date = '" +  new java.sql.Timestamp(visitDate.getTime()) + "'");
		if(visitTime != null) sb.append(" AND visit_time = '" + new java.sql.Timestamp(visitTime.getTime()) + "'");
		return entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE 0=0" + sb.toString()).getResultList();
	}
}
