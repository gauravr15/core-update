package com.odin.core.update.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.odin.core.update.constants.LanguageConstants;
import com.odin.core.update.constants.ResponseCodes;
import com.odin.core.update.dto.JwtDTO;
import com.odin.core.update.dto.ProfileDTO;
import com.odin.core.update.dto.ResponseDTO;
import com.odin.core.update.entity.Profile;
import com.odin.core.update.repo.ProfileRepository;
import com.odin.core.update.utility.JwtTokenUtil;
import com.odin.core.update.utility.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FetchCustomerServiceImpl implements FetchService {
	
	   @Autowired
	    private ProfileRepository profileRepo;

	    @Autowired
	    private ResponseObject response;

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Value("${max.incorrect.password.count}")
	    private String maxIncorrectPasswordCount;

	    @Override
	    public ResponseDTO fetch(ProfileDTO profileDTO) {
	        log.info("Fetching customer profile by mobile : {} or email : {}", profileDTO.getMobile(),
	                profileDTO.getEmail());
	        profileDTO.setMobile(ObjectUtils.isEmpty(profileDTO.getMobile()) ? profileDTO.getEmail()
	                : profileDTO.getMobile());
	        Profile profile = profileRepo.findByMobileOrEmail(profileDTO.getMobile(), profileDTO.getEmail());
	        if(ObjectUtils.isEmpty(profile)) {
	        	return response.buildResponse(LanguageConstants.EN, ResponseCodes.USER_NOT_EXISTS);
	        }
	        if (ObjectUtils.isEmpty(profile.getAuth())) {
	            log.error("Failed to fetch auth instance for customer with identifier : {}", profileDTO.getMobile());
	            return response.buildResponse(LanguageConstants.EN, ResponseCodes.INTERNAL_SERVER_ERROR);
	        }

	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        boolean isPasswordMatch = passwordEncoder.matches(profileDTO.getAuth().getPassword(),
	                profile.getAuth().getPassword());

	        if (isPasswordMatch) {
	            // Update login details
	            profile.getAuth().setLastLoginTimestamp(new Timestamp(System.currentTimeMillis()));
	            profile.getAuth().setTempLockCount(0);
	            profile.getAuth().setPermLockCount(0);
	            profile.getAuth().setIncorrectPasswordCount(0);
	            profileRepo.save(profile);

	            // Generate JWT token
	            String token = jwtTokenUtil.generateToken(profileDTO.getMobile());
	            JwtDTO jwtResponse = JwtDTO.builder().accessToken(token).build();
	            // Return response with JWT token
	            return response.buildResponse(LanguageConstants.EN, ResponseCodes.SUCCESS_CODE, jwtResponse);
	        } else {
	            // Handle incorrect password case
	            profile.getAuth().setIncorrectPasswordCount(profile.getAuth().getIncorrectPasswordCount() + 1);
	            if (profile.getAuth().getIncorrectPasswordCount() == Integer.valueOf(maxIncorrectPasswordCount)) {
	                profile.getAuth().setTempLockDate(new Timestamp(System.currentTimeMillis()));
	                profile.getAuth().setTempLockCount(1);
	            }
	            profileRepo.save(profile);
	            return response.buildResponse(LanguageConstants.EN, ResponseCodes.FAILURE_CODE);
	        }
	    }

}
