package com.digital.evidence.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.digital.evidence.api.dto.EvidenceDTO;

public interface EvidenceApiService {
	
	List<EvidenceDTO> findAll();

	Optional<EvidenceDTO> findById(Long id);

	EvidenceDTO create(EvidenceDTO dto);

	Optional<EvidenceDTO> update(Long id, EvidenceDTO dto);

	void delete(Long id);
	
	List<EvidenceDTO> filterEvidence(String officer, Boolean encrypted, LocalDate date);

}
