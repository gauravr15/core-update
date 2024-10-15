package com.odin.core.update.service;

import java.util.List;

import com.odin.core.update.dto.ProfileDTO;
import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.entity.Profile;
import com.odin.core.update.utility.SearchCriteria;

public interface FetchService {

	ResponseDTO searchProfiles(List<SearchCriteria> searchCriteriaList);

	ResponseDTO update(Profile profile);

}
