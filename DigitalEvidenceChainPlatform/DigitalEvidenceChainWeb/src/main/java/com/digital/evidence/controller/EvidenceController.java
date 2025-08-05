package com.digital.evidence.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digital.evidence.model.Evidence;
import com.digital.evidence.service.EvidenceService;
import com.digital.evidence.service.EvidenceSummaryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/evidence")
public class EvidenceController {
	
	@Autowired
	private EvidenceService evidenceService;
	
	@Autowired
	private EvidenceSummaryService evidenceSummaryService;
	
	@GetMapping("/form")
	public String showForm(Model model) {
		model.addAttribute("evidence", new Evidence());
		return "evidenceForm";
	}

	@PostMapping("/save")
	public String saveEvidence(@ModelAttribute("evidence") @Valid Evidence evidence, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Please fix the form errors.");
			return "evidenceForm";
		}

		evidenceService.saveEvidence(evidence);
		model.addAttribute("success", "Evidence saved successfully.");
		return "redirect:/evidence/all";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
	    Evidence evidence = evidenceService.findEvidenceById(id);
	    if (evidence == null) {
	        return "redirect:/evidence/all";
	    }
	    model.addAttribute("evidence", evidence);
	    return "evidenceForm";
	}
	

	@GetMapping("/all")
	public String showAllEvidence(Model model) {
		model.addAttribute("evidences", evidenceService.findAllEvidence());
		return "allEvidence";
	}

	@GetMapping("/incustody")
	public String showInCustodyEvidence(Model model) {
		model.addAttribute("evidences", evidenceService.findEvidenceInCustody());
		return "inCustodyEvidence";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteEvidence(@PathVariable Long id) {
	    evidenceService.deleteEvidenceById(id);
	    return "redirect:/evidence/all";
	}

	@GetMapping("/filter")
	public String filter(@RequestParam(required = false) String officer,
			@RequestParam(required = false) Boolean encrypted, @RequestParam(required = false) String date,
			Model model) {
		LocalDate parsedDate = null;
		if (date != null && !date.isBlank()) {
			parsedDate = LocalDate.parse(date);
		}

		List<Evidence> filtered = evidenceService.filterEvidence(officer, encrypted, parsedDate);
		model.addAttribute("evidences", filtered);
		return "allEvidence";
	}
	
	@GetMapping("/summary/{id}")
	public String showSummary(@PathVariable Long id, Model model) {
	    Evidence evidence = evidenceService.findEvidenceById(id);
	    if (evidence == null) {
	        return "redirect:/evidence/all";
	    }

	    String summary = evidenceSummaryService.generateEvidenceSummary(id);
	    model.addAttribute("summary", summary);
	    model.addAttribute("evidenceId", id);
	    return "evidenceSummary"; // a new view we’ll create
	}
	
}
