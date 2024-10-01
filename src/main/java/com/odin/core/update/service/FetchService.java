package com.odin.core.update.service;

import com.odin.core.update.dto.ProfileDTO;
import com.odin.core.update.dto.ResponseDTO;

public interface FetchService {

	ResponseDTO fetch(ProfileDTO profileDTO);

}
