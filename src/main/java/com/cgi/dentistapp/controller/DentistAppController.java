package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.DentistVisitSearchDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.cgi.dentistapp.service.DentistVisitService;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO) {
        return "form";
    }
    
    @GetMapping("/list")
    public String showDentistVisitsList(DentistVisitSearchDTO dentistVisitDTO, Model model) {
        model.addAttribute("list", dentistVisitService.listVisits());
        return "visitsList";
    }

    @GetMapping("/visit/{ID}")
    public String showDetailedVisit(@PathVariable("ID") int id, Model model){
    	model.addAttribute("id", id);
    	model.addAttribute("visit", dentistVisitService.getVisitById(id));
    	return "detailedVisit";
    }
    
    @PostMapping("/list")
    public String postVisitsSearch(@Valid DentistVisitSearchDTO dentistVisitSearchDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "visitsList";
        }

        model.addAttribute("list", dentistVisitService.listVisitsByParameters(dentistVisitSearchDTO.getDentistName(), 
        		dentistVisitSearchDTO.getVisitDate(), dentistVisitSearchDTO.getVisitTime()));
        return "visitsList";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getGpName(), 
        		dentistVisitDTO.getVisitDate(), dentistVisitDTO.getVisitTime());
        return "redirect:/results";
    }

}
