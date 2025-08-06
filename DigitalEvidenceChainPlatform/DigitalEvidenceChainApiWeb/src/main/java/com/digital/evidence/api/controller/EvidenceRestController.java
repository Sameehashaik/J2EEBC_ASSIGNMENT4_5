package com.digital.evidence.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digital.evidence.api.dto.EvidenceDTO;
import com.digital.evidence.api.service.EvidenceApiService;
import com.digital.evidence.service.EvidenceSummaryService;

import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/evidence")
public class EvidenceRestController {
   
	@Autowired
    private EvidenceApiService evidenceApiService;
	
	@Autowired
    private EvidenceSummaryService evidenceSummaryService;

	@GetMapping("/summary/{id}")
    public ResponseEntity<Map<String, Object>> getEvidenceSummary(@PathVariable Long id) {
        // Fetch the evidence data (as DTO) by ID using existing service
        Optional<EvidenceDTO> optEvidence = evidenceApiService.findById(id);
        if (optEvidence.isEmpty()) {
            // If not found, return 404 Not Found with no body
            return ResponseEntity.notFound().build();
        }
        EvidenceDTO evidence = optEvidence.get();
        
        // Generate the narrative summary using the AI-backed service
        String summaryText = evidenceSummaryService.generateEvidenceSummary(id);
        
        // Build a structured JSON response with all required fields
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("description", evidence.getDescription());
        responseBody.put("acquisitionOfficer", evidence.getSourceOfficer());
        responseBody.put("acquisitionDate", evidence.getAcquisitionDate());  // will be serialized as ISO date
        responseBody.put("custodyStatus", evidence.getCustodyStatus());
        // Add a human-readable explanation for the custody status
        String statusMeaning;
        if ("Released".equalsIgnoreCase(evidence.getCustodyStatus())) {
            statusMeaning = "The evidence has been released from official custody.";
        } else if ("In Custody".equalsIgnoreCase(evidence.getCustodyStatus())) {
            statusMeaning = "The evidence is currently secured in official custody.";
        } else {
            statusMeaning = "Custody status: " + evidence.getCustodyStatus();
        }
        responseBody.put("custodyStatusMeaning", statusMeaning);
        responseBody.put("lastCustodianName", evidence.getCustodianName());
        // Include encryption status and explanation
        Boolean isEncrypted = evidence.getEncryptionStatus();
        responseBody.put("encryptionStatus", isEncrypted);  // true or false
        String encryptionMeaning = Boolean.TRUE.equals(isEncrypted)
                ? "This evidence is encrypted and requires appropriate keys to access."
                : "This evidence is not encrypted and can be accessed without decryption.";
        responseBody.put("encryptionStatusMeaning", encryptionMeaning);
        // Include the AI-generated summary narrative (HTML/text format)
        responseBody.put("summary", summaryText);
        
        // Return the response as JSON with 200 OK
        return ResponseEntity.ok(responseBody);
    }
	
    @GetMapping("/all")
    public ResponseEntity<List<EvidenceDTO>> listAll() {
        return ResponseEntity.ok(evidenceApiService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvidenceDTO> getById(@PathVariable Long id) {
        return evidenceApiService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<EvidenceDTO> create(@RequestBody EvidenceDTO dto) {
        return ResponseEntity.status(201).body(evidenceApiService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvidenceDTO> update(@PathVariable Long id, @RequestBody EvidenceDTO dto) {
        Optional<EvidenceDTO> updated = evidenceApiService.update(id, dto);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        evidenceApiService.delete(id);
        boolean deleted = true;
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EvidenceDTO>> filter(
            @RequestParam(required = false) String officer,
            @RequestParam(required = false) Boolean encrypted,
            @RequestParam(required = false) String date
    ) {
        LocalDate parsedDate = (date != null) ? LocalDate.parse(date) : null;
        return ResponseEntity.ok(evidenceApiService.filterEvidence(officer, encrypted, parsedDate));
    }
}
