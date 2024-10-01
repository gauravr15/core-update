package com.odin.core.update.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.odin.core.update.dto.ProfileDTO;
import com.odin.core.update.service.FetchCustomerServiceImpl;
import com.odin.core.update.service.FetchService;


@Component
public class CustomerFactory {
	
	@Autowired
	FetchCustomerServiceImpl customer;
	
	public FetchService getInstance(ProfileDTO profile) {
		switch(profile.getCustomerType()) {
		case CUSTOMER:
			return customer;
		default:
			return null;
		}
	}

}
