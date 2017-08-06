package com.cgi.dentistapp.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public void addVisit(String dentistName, String gpName, Date visitDate, Date visitTime) {
        DentistVisitEntity visit = new DentistVisitEntity(dentistName, gpName, visitDate, visitTime);
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitEntity> listVisits () {
        return dentistVisitDao.getAllVisits();
    }
    
    public List<DentistVisitEntity> listVisitsByParameters (String dentistName, Date visitDate, Date visitTime) {
        return dentistVisitDao.getVisitsByParameters(dentistName, visitDate, visitTime);
    }
    
    public DentistVisitEntity getVisitById (Long id){
    	return dentistVisitDao.getVisitById(id);
    }

	public void deleteVisit(Long id) {
		dentistVisitDao.deleteVisit(getVisitById(id));		
	}

	public void changeVisit(Long id, String dentistName, String gpName,
			Date visitDate, Date visitTime) {
		dentistVisitDao.changeVisit(getVisitById(id), dentistName, gpName, visitDate, visitTime);
	}

}
