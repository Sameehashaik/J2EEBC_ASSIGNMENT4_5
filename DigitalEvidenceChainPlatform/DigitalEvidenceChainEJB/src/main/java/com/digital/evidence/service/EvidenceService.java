package com.digital.evidence.service;

import java.time.LocalDate;
import java.util.List;

import com.digital.evidence.model.Evidence;

public interface EvidenceService {
	Evidence saveEvidence(Evidence evidence);
	List<Evidence> findAllEvidence();
	List<Evidence> findEvidenceInCustody();
	List<Evidence> filterEvidence(String officer, Boolean encryptionStatus, LocalDate date);
	Evidence findEvidenceById(Long id);
	void deleteEvidenceById(Long id);

}
