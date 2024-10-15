package com.odin.core.update.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odin.core.update.constants.ApplicationConstants;
import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.entity.Profile;
import com.odin.core.update.service.FetchService;
import com.odin.core.update.utility.ResponseObject;
import com.odin.core.update.utility.SearchCriteria;

@RestController
@RequestMapping(value = ApplicationConstants.API_VERSION)
public class ProfileController {
	
	@Autowired
	ResponseObject response;
	
	@Autowired
	private FetchService fetch;
	
	
	@PostMapping(ApplicationConstants.CUSTOMER + ApplicationConstants.DETAILS)
    public ResponseEntity<ResponseDTO> searchProfiles(@RequestBody List<SearchCriteria> searchCriteriaList) {
		ResponseDTO response = fetch.searchProfiles(searchCriteriaList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping(ApplicationConstants.CUSTOMER + ApplicationConstants.UPDATE)
    public ResponseEntity<ResponseDTO> updateProfiles(@RequestBody Profile profileDTO) {
		ResponseDTO response = fetch.update(profileDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
