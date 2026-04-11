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
		log.info("[CORE-UPDATE-FILE] search called with {} criteria: {}", searchCriteriaList.size(), searchCriteriaList);
		for (SearchCriteria criteria : searchCriteriaList) {
			log.info("[CORE-UPDATE-FILE] criteria: key={} op={} value={} condition={}",
					criteria.getKey(), criteria.getOperation(), criteria.getValue(), criteria.getCondition());
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
		log.info("[CORE-UPDATE-FILE] Query returned {} rows for {} criteria",
				profiles.size(), searchCriteriaList.size());
		if (!profiles.isEmpty()) {
			profiles.forEach(f -> log.info(
				"[CORE-UPDATE-FILE] Found row: id={} customerId={} fileType={} isActive={} filePath={} (absolute)",
				f.getId(), f.getCustomerId(), f.getFileType(), f.getIsActive(), f.getFilePath()));
		}
		if (profiles.isEmpty()) {
			log.info("[CORE-UPDATE-FILE] No file data found for criteria");
			return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.NO_DATA_FOUND);
		}
		log.info("[CORE-UPDATE-FILE] Returning {} records to caller", profiles.size());
		return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, profiles);
	}

	@Override
	public ResponseDTO update(FileEntity file) {
		fileRepo.save(file);
		return responseObj.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, file);
	}

}
