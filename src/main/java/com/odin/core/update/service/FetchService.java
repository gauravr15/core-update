package com.odin.core.update.service;

import java.util.List;

import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.utility.SearchCriteria;

public interface FetchService<T> {

	ResponseDTO search(List<SearchCriteria> searchCriteriaList);
	
	ResponseDTO update(T entity);

}
