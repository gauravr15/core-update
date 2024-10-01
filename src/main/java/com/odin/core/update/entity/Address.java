package com.odin.core.update.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "core_address")
public class Address {
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@CreationTimestamp
	@Column(name = "creation_timestamp")
	private Timestamp creationTimestamp;
	
	@UpdateTimestamp
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	@Column(name = "address_type")
	private String addressType;
	
	@Column(name = "house_no")
	private String houseNo;
	
	@Column(name = "street_no")
	private String streetNo;
	
	@Column(name = "locality")
	private String locality;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "last_used")
	private Timestamp lastUsed;
}
