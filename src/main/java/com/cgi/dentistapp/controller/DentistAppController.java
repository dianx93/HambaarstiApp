package com.cgi.dentistapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.DentistVisitSearchDTO;
import com.cgi.dentistapp.service.DentistVisitService;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/deleted").setViewName("deleted");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO) {
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        //kattuvusi ei saa kontrollida, kui juba eelnevalt on mingi väli vigane või tühi.
        dentistVisitService.isConflictFree(dentistVisitDTO, bindingResult, null);
        if (bindingResult.hasErrors()) {
            return "form";
        }
        dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getGpName(), 
        		dentistVisitDTO.getVisitDate(), dentistVisitDTO.getVisitTime());
        return "redirect:/results";
    }

    @GetMapping("/list")
    public String showDentistVisitsList(DentistVisitSearchDTO dentistVisitDTO, Model model) {
        model.addAttribute("list", dentistVisitService.listVisits());
        return "visitsList";
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

    @GetMapping("/visit/{ID}")
    public String showDetailedVisit(@PathVariable("ID") Long id, Model model){
    	model.addAttribute("visit", dentistVisitService.getVisitById(id));
    	return "detailedVisit";
    }

    @PostMapping("/delete/{ID}")
    public String deleteVisit(@PathVariable("ID") Long id) {
    	dentistVisitService.deleteVisit(id);
    	return "redirect:/deleted";
    }
    
    @GetMapping("/visit/change/{ID}")
    public String showChangeVisitForm(DentistVisitDTO dentistVisitDTO, @PathVariable("ID") Long id, Model model){
    	model.addAttribute("visit", dentistVisitService.getVisitById(id));
    	return "changeVisit";
    }    
    
    @PostMapping("/visit/change/{ID}")
    public String postVisitsChange(@PathVariable("ID") Long id, @Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("visit", dentistVisitService.getVisitById(id));
            return "changeVisit";
        }
        dentistVisitService.isConflictFree(dentistVisitDTO, bindingResult, id);
        if (bindingResult.hasErrors()) {
        	model.addAttribute("visit", dentistVisitService.getVisitById(id));
            return "changeVisit";
        }

        dentistVisitService.changeVisit(id, dentistVisitDTO.getDentistName(), dentistVisitDTO.getGpName(), 
        		dentistVisitDTO.getVisitDate(), dentistVisitDTO.getVisitTime());
        model.addAttribute("visit", dentistVisitService.getVisitById(id));
        return "detailedVisit";
    }
    
}
