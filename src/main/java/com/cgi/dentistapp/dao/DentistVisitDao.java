package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;

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
		if(dentistName != "") sb.append(" AND dentist_name = '" + dentistName + "'");
		if(visitDate != null) sb.append(" AND visit_date = '" +  new java.sql.Timestamp(visitDate.getTime()) + "'");
		if(visitTime != null) sb.append(" AND visit_time = '" + new java.sql.Timestamp(visitTime.getTime()) + "'");
		return entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE 0=0" + sb.toString()).getResultList();
	}

	public DentistVisitEntity getVisitById(Long id){
		return (DentistVisitEntity) entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE id = " + id.toString()).getResultList().get(0);
	}

	public void deleteVisit(DentistVisitEntity dentistVisitEntity) {
		entityManager.remove(dentistVisitEntity);
	}

	public void changeVisit(DentistVisitEntity dentistVisitEntity, String dentistName, String gpName,
			Date visitDate, Date visitTime) {
		dentistVisitEntity.setDentistName(dentistName);
		dentistVisitEntity.setGpName(gpName);
		dentistVisitEntity.setVisitDate(visitDate);
		dentistVisitEntity.setVisitTime(visitTime);
		entityManager.merge(dentistVisitEntity);
	}
	
	public boolean isConflictFree(DentistVisitDTO visit, Long id){
		String idQuery = "";
		if (id != null) idQuery = "id != '" + id + "' AND";
		int conflicts = entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE " + idQuery + " dentist_name = '" + visit.getDentistName() 
				+ "' AND visit_date = '" + new java.sql.Timestamp(visit.getVisitDate().getTime()) 
				+ "' AND visit_time between DATEADD(minute, -29, '" + new java.sql.Timestamp(visit.getVisitTime().getTime()) + "')"
				+ " AND DATEADD(minute, 29, '" + new java.sql.Timestamp(visit.getVisitTime().getTime()) + "')").getResultList().size();
		return conflicts == 0;
	}
	
}
