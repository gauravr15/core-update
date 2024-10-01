package com.odin.core.update.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odin.core.update.entity.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long>{

}
