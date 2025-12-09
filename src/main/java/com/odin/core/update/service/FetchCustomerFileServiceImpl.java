package com.odin.core.update.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.odin.core.update.constants.LanguageConstants;
import com.odin.core.update.constants.ResponseCodes;
import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.entity.FileEntity;
import com.odin.core.update.repo.FileRepository;
import com.odin.core.update.utility.GenericSpecification;
import com.odin.core.update.utility.ResponseObject;
import com.odin.core.update.utility.SearchCriteria;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "fileService")
public class FetchCustomerFileServiceImpl implements FetchService<FileEntity> {

	@Autowired
	private FileRepository fileRepo;

	@Autowired
	private ResponseObject responseObj;

	@Override
	public ResponseDTO search(List<SearchCriteria> searchCriteriaList) {
		Specification<FileEntity> resultSpecification = null;

		for (SearchCriteria criteria : searchCriteriaList) {
			System.out.println(
					"Processing criteria: key=" + criteria.getKey() + ", operation={}," + criteria.getOperation()
							+ " value=" + criteria.getValue() + ", condition=" + criteria.getCondition());

			// Ensure criteria are processed even if value is empty, since the condition
			// (OR) is important
			GenericSpecification<FileEntity> spec = new GenericSpecification<>(criteria);

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
		List<FileEntity> profiles = fileRepo.findAll(resultSpecification);
		if (profiles.isEmpty()) {
			return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.NO_DATA_FOUND);
		}
		return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, profiles);
	}

	@Override
	public ResponseDTO update(FileEntity file) {
		fileRepo.save(file);
		return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, file);
	}

}
