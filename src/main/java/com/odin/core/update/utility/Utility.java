package com.odin.core.update.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utility {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	 public <D, E> E dtoToEntity(D dto, Class<E> entityClass) {
	        return objectMapper.convertValue(dto, entityClass);
	    }

}
