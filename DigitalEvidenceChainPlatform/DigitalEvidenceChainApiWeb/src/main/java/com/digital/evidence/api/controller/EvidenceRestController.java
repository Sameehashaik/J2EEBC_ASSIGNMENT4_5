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

@RestController
@RequestMapping("/evidence")
public class EvidenceRestController {
   
	@Autowired
    private EvidenceApiService evidenceApiService;

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
