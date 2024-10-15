package com.odin.core.update.dto;

import java.sql.Timestamp;

import com.odin.core.update.enums.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDetailsDTO {

	private String parentCustomerId;
	private Integer customerId;
	private String mobile;
	private String email;
	private String firstName;
	private String lastName;
	private CustomerType customerType;
	private String customerSubType;
	private Boolean isActive;
}
