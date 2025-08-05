package com.digital.evidence.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "evidence")
public class Evidence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{evidence.description.required}")
	private String description;

	@NotBlank(message = "{evidence.sourceOfficer.required}")
	@Column(name = "source_officer")
	private String sourceOfficer;

	@NotNull(message = "{evidence.acquisitionDate.required}")
	@PastOrPresent(message = "{evidence.acquisitionDate.past}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_acquisition")
	private LocalDate acquisitionDate;

	@NotBlank(message = "{evidence.custodyStatus.required}")
	@Column(name = "custody_status")
	private String custodyStatus; // "In Custody" or "Released"

	@NotBlank(message = "{evidence.custodianName.required}")
	@Column(name = "last_custodian_name")
	private String custodianName;
  
	@Column(name = "encryption_status")
	private Boolean encryptionStatus;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Getters and Setters

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

	public Boolean getEncryptionStatus() {
		return encryptionStatus;
	}

	public void setEncryptionStatus(Boolean encryptionStatus) {
		this.encryptionStatus = encryptionStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
