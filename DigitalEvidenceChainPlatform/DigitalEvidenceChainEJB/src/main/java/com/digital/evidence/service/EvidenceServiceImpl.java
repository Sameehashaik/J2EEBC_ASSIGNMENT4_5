package com.digital.evidence.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.evidence.model.Evidence;
import com.digital.evidence.repository.EvidenceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EvidenceServiceImpl implements EvidenceService{

	private final EvidenceRepository evidenceRepository;

    @Autowired
    public EvidenceServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Evidence saveEvidence(Evidence evidence) {
        return evidenceRepository.save(evidence);
    }

    @Override
    public List<Evidence> findAllEvidence() {
        return evidenceRepository.findAll();
    }

    @Override
    public List<Evidence> findEvidenceInCustody() {
        return evidenceRepository.findByCustodyStatus("In Custody");
    }

    @Override
    public List<Evidence> filterEvidence(String officer, Boolean encryptionStatus, LocalDate date) {
        boolean hasOfficer = officer != null && !officer.isBlank();
        boolean hasEncrypted = encryptionStatus != null;
        boolean hasDate = date != null;

        if (hasOfficer && hasEncrypted && hasDate) {
            return evidenceRepository.findBySourceOfficerContainingIgnoreCaseAndEncryptionStatusAndAcquisitionDate(officer, encryptionStatus, date);
        } else if (hasOfficer && hasEncrypted) {
            return evidenceRepository.findBySourceOfficerContainingIgnoreCaseAndEncryptionStatus(officer, encryptionStatus);
        } else if (hasOfficer && hasDate) {
            return evidenceRepository.findBySourceOfficerContainingIgnoreCaseAndAcquisitionDate(officer, date);
        } else if (hasEncrypted && hasDate) {
            return evidenceRepository.findByEncryptionStatusAndAcquisitionDate(encryptionStatus, date);
        } else if (hasOfficer) {
            return evidenceRepository.findBySourceOfficerContainingIgnoreCase(officer);
        } else if (hasEncrypted) {
            return evidenceRepository.findByEncryptionStatus(encryptionStatus);
        } else if (hasDate) {
            return evidenceRepository.findByAcquisitionDate(date);
        } else {
            return findAllEvidence();
        }
    }
    
	@Override
	public Evidence findEvidenceById(Long id) {
		// TODO Auto-generated method stub
	    return evidenceRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteEvidenceById(Long id) {
		// TODO Auto-generated method stub
	    evidenceRepository.deleteById(id);
	}
}
