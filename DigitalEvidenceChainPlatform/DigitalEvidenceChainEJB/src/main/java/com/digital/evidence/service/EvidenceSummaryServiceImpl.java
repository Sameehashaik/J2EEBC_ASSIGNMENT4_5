package com.digital.evidence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digital.evidence.model.Evidence;
import com.digital.evidence.model.OllamaRequest;
import com.digital.evidence.model.OllamaResponse;
import com.digital.evidence.repository.EvidenceRepository;


@Service
public class EvidenceSummaryServiceImpl implements EvidenceSummaryService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String DEFAULT_MODEL = "llama3";

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String generateEvidenceSummary(long evidenceId) {
        Optional<Evidence> optionalEvidence = evidenceRepository.findById(evidenceId);
        if (optionalEvidence.isEmpty()) {
            throw new IllegalArgumentException("Evidence not found for ID: " + evidenceId);
        }

        Evidence evidence = optionalEvidence.get();
        String prompt = buildPrompt(evidence);

        OllamaRequest request = new OllamaRequest(DEFAULT_MODEL, prompt, false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OllamaResponse> response = restTemplate.exchange(
            OLLAMA_URL,
            HttpMethod.POST,
            entity,
            OllamaResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getResponse();
        } else {
            return "[AI summary generation failed: " + response.getStatusCode() + "]";
        }
    }

    private String buildPrompt(Evidence evidence) {
        return "Summarize the following digital evidence and its custody chain in simple terms:\n"
            + "- Description: " + evidence.getDescription() + "\n"
            + "- Acquired by: " + evidence.getSourceOfficer() + " on " + evidence.getAcquisitionDate() + "\n"
            + "- Custody Status: " + evidence.getCustodyStatus() + "\n"
            + "- Last Custodian: " + evidence.getCustodianName() + "\n"
            + "- Encryption: " + (evidence.getEncryptionStatus() ? "Encrypted" : "Unencrypted");
    }
}

