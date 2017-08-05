package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
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
    public String showDentistVisitsList(DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("list", dentistVisitService.listVisits());
        return "visitsList";
    }

    @PostMapping("/list")
    public String postVisitsSearch(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "visitsList";
        }

        model.addAttribute("list", dentistVisitService.listVisitsByParameters(dentistVisitDTO.getDentistName(), 
        		dentistVisitDTO.getVisitDate(), dentistVisitDTO.getVisitTime()));
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
