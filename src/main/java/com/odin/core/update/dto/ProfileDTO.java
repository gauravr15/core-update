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
public class ProfileDTO {
	
	private String parentCustomerId;
	private Integer customerId;
	private Timestamp creationTimestamp;
	private Timestamp updateTimestamp;
	private String mobile;
	private String email;
	private String firstName;
	private String lastName;
	private String fathersName;
	private CustomerType customerType;
	private String customerSubType;
	private String idType;
	private String idNum;
	private String address;
	private Boolean isActive;
	private Boolean isDeleted;
	private Boolean isNotificationEnabled;
	private Boolean isTransactionEnabled;
	private String bankDetails;
	private AuthDTO auth;

}
