package com.digital.evidence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.evidence.model.Evidence;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
	 // Find only evidence currently in custody
    List<Evidence> findByCustodyStatus(String custodyStatus);

    // Filter by officer
    List<Evidence> findBySourceOfficer(String sourceOfficer);

    // Filter by encryption status
    List<Evidence> findByEncryptionStatus(boolean encryptionStatus);

    // Filter by acquisition date
    List<Evidence> findByAcquisitionDate(LocalDate acquisitionDate);

    // Composite filters
    List<Evidence> findBySourceOfficerAndEncryptionStatus(String sourceOfficer, boolean encryptionStatus);

    List<Evidence> findBySourceOfficerAndAcquisitionDate(String sourceOfficer, LocalDate acquisitionDate);

    List<Evidence> findByEncryptionStatusAndAcquisitionDate(boolean encryptionStatus, LocalDate acquisitionDate);

    List<Evidence> findBySourceOfficerAndEncryptionStatusAndAcquisitionDate(
            String sourceOfficer, boolean encryptionStatus, LocalDate acquisitionDate
    );
    
    List<Evidence> findBySourceOfficerContainingIgnoreCase(String officer);

    List<Evidence> findBySourceOfficerContainingIgnoreCaseAndEncryptionStatus(String officer, Boolean encryptionStatus);

    List<Evidence> findBySourceOfficerContainingIgnoreCaseAndAcquisitionDate(String officer, LocalDate date);

    List<Evidence> findBySourceOfficerContainingIgnoreCaseAndEncryptionStatusAndAcquisitionDate(String officer, Boolean encryptionStatus, LocalDate date);

    List<Evidence> findByEncryptionStatus(Boolean encryptionStatus);

    List<Evidence> findByEncryptionStatusAndAcquisitionDate(Boolean encryptionStatus, LocalDate date);
}
