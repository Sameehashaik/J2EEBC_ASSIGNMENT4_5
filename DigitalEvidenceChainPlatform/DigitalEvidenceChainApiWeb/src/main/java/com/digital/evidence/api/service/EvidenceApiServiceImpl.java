package com.digital.evidence.api.service;

import com.digital.evidence.api.dto.EvidenceDTO;
import com.digital.evidence.model.Evidence;
import com.digital.evidence.service.EvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvidenceApiServiceImpl implements EvidenceApiService {

    @Autowired
    private EvidenceService evidenceService;

    @Override
    public List<EvidenceDTO> findAll() {
        List<Evidence> evidenceList = evidenceService.findAllEvidence();
        return evidenceList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EvidenceDTO> findById(Long id) {
        Evidence evidence = evidenceService.findEvidenceById(id);
        if (evidence == null) {
            return Optional.empty();
        }
        return Optional.of(toDTO(evidence));
    }

    @Override
    public EvidenceDTO create(EvidenceDTO dto) {
        Evidence entity = toEntity(dto);
        Evidence saved = evidenceService.saveEvidence(entity);
        return toDTO(saved);
    }

    @Override
    public Optional<EvidenceDTO> update(Long id, EvidenceDTO dto) {
        Evidence existing = evidenceService.findEvidenceById(id);
        if (existing == null) {
            return Optional.empty();
        }

        existing.setDescription(dto.getDescription());
        existing.setSourceOfficer(dto.getSourceOfficer());
        existing.setEncryptionStatus(dto.getEncryptionStatus());
        existing.setAcquisitionDate(dto.getAcquisitionDate());
        existing.setCustodyStatus(dto.getCustodyStatus());
        existing.setCustodianName(dto.getCustodianName());

        Evidence updated = evidenceService.saveEvidence(existing);
        return Optional.of(toDTO(updated));
    }

    @Override
    public void delete(Long id) {
         evidenceService.deleteEvidenceById(id);
    }

    @Override
    public List<EvidenceDTO> filterEvidence(String officer, Boolean encrypted, LocalDate date) {
        return evidenceService.filterEvidence(officer, encrypted, date).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Mapping methods ---

    private EvidenceDTO toDTO(Evidence entity) {
        EvidenceDTO dto = new EvidenceDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setSourceOfficer(entity.getSourceOfficer());
        dto.setEncryptionStatus(entity.getEncryptionStatus());
        dto.setAcquisitionDate(entity.getAcquisitionDate());
        dto.setCustodyStatus(entity.getCustodyStatus());
        dto.setCustodianName(entity.getCustodianName());
        return dto;
    }

    private Evidence toEntity(EvidenceDTO dto) {
        Evidence entity = new Evidence();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setSourceOfficer(dto.getSourceOfficer());
        entity.setEncryptionStatus(dto.getEncryptionStatus());
        entity.setAcquisitionDate(dto.getAcquisitionDate());
        entity.setCustodyStatus(dto.getCustodyStatus());
        entity.setCustodianName(dto.getCustodianName());
        return entity;
    }
}
