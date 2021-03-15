package com.bonejah.ytsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bonejah.ytsapi.model.YTSUser;

/**
*
* brunolima on Mar 15, 2021
* 
*/

@Repository
public interface YTSUserRepository extends JpaRepository<YTSUser, Long> {
	
	YTSUser findByUsername(String username);

}

