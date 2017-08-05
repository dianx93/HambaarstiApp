package com.cgi.dentistapp.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dentist_name")
    private String dentistName;
    
    @Column(name = "visit_date")
    private Date visitDate;
    
    @Column(name = "visit_time")
    private Date visitTime;

    public DentistVisitEntity() {
    }

    public DentistVisitEntity(String dentistName, String gpName, Date visitDate, Date visitTime) {
    	this.setDentistName(dentistName);
        this.setVisitDate(visitDate);
        this.setVisitTime(visitTime);
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDentistName() {
    	return dentistName;
    }
    
    private void setDentistName(String dentistName) {
		this.dentistName = dentistName;
	}
    
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    
    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

}
