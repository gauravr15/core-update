package com.odin.core.update.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odin.core.update.entity.ResponseMessages;


@Repository
public interface ResponseMessagesRepository extends JpaRepository<ResponseMessages, Integer> {

}
