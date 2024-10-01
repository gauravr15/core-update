package com.odin.core.update.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odin.core.update.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{
	
	public Profile findByMobileOrEmail(String mobile, String email);
}
