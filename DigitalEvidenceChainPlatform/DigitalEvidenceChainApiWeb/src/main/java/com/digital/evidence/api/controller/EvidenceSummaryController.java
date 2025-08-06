package com.digital.evidence.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.evidence.service.EvidenceSummaryService;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceSummaryController {

    private final EvidenceSummaryService summaryService;

    @Autowired
    public EvidenceSummaryController(EvidenceSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<Map<String,Object>> getSummary(@PathVariable long id) {
        String narrative = summaryService.generateEvidenceSummary(id);

        // package up the raw AI text plus minimal metadata
        Map<String,Object> body = new HashMap<>();
        body.put("id", id);
        body.put("summary", narrative);

        return ResponseEntity.ok(body);
    }
}
