package com.odin.core.update.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.odin.core.update.constants.LanguageConstants;
import com.odin.core.update.constants.ResponseCodes;
import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.entity.Profile;
import com.odin.core.update.repo.ProfileRepository;
import com.odin.core.update.utility.GenericSpecification;
import com.odin.core.update.utility.ResponseObject;
import com.odin.core.update.utility.SearchCriteria;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FetchCustomerServiceImpl implements FetchService {

	@Autowired
	private ProfileRepository profileRepo;

	@Autowired
	private ResponseObject responseObj;

	@Override
	public ResponseDTO searchProfiles(List<SearchCriteria> searchCriteriaList) {
	    Specification<Profile> resultSpecification = null;

	    for (SearchCriteria criteria : searchCriteriaList) {
	    	System.out.println(
	    			"Processing criteria: key=" + criteria.getKey() + ", operation={}," + criteria.getOperation()
	    					+ " value=" + criteria.getValue() + ", condition=" + criteria.getCondition());

	        // Ensure criteria are processed even if value is empty, since the condition (OR) is important
	        GenericSpecification<Profile> spec = new GenericSpecification<>(criteria);

	        if (resultSpecification == null) {
	            resultSpecification = spec;
	        } else {
	            // Apply condition (AND/OR) between criteria
	            if ("OR".equalsIgnoreCase(criteria.getCondition())) {
	                resultSpecification = Specification.where(resultSpecification).or(spec);
	            } else {
	                resultSpecification = Specification.where(resultSpecification).and(spec); // Default to AND
	            }
	        }
	    }

	    if (resultSpecification == null) {
	        throw new IllegalArgumentException("No valid search criteria provided.");
	    }

	    // Execute the query using the OR logic for mobile and email
	    List<Profile> profiles = profileRepo.findAll(resultSpecification);
	    if (profiles.isEmpty()) {
	        return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.NO_DATA_FOUND);
	    }
	    return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, profiles.get(0));
	}

	@Override
	public ResponseDTO update(Profile profile) {
		profileRepo.save(profile);
		return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, profile);
	}
}
