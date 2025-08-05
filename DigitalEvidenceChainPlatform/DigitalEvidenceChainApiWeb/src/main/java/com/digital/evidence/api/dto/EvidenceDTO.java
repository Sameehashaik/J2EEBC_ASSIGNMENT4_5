package com.digital.evidence.api.dto;

import java.time.LocalDate;

public class EvidenceDTO {
    private Long id;
    private String description;
    private String sourceOfficer;
    private Boolean encryptionStatus;
    private LocalDate acquisitionDate;
    private String custodyStatus;
    private String custodianName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceOfficer() {
        return sourceOfficer;
    }

    public void setSourceOfficer(String sourceOfficer) {
        this.sourceOfficer = sourceOfficer;
    }

    public Boolean getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(Boolean encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getCustodyStatus() {
        return custodyStatus;
    }

    public void setCustodyStatus(String custodyStatus) {
        this.custodyStatus = custodyStatus;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }
}
