package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by serkp on 2.03.2017.
 */
public class DentistVisitSearchDTO {

    @Size(min = 0, max = 50)
    String dentistName;
    
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date visitDate;

    @DateTimeFormat(pattern = "HH:mm")
    Date visitTime;
    
    public DentistVisitSearchDTO() {
    }

    public DentistVisitSearchDTO(String dentistName, Date visitDate, Date visitTime) {
        this.dentistName = dentistName;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
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
