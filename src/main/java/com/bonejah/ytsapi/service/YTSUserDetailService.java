package com.bonejah.ytsapi.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bonejah.ytsapi.model.YTSUser;
import com.bonejah.ytsapi.repository.YTSUserRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * brunolima on Mar 13, 2021
 * 
 */

@Component
@RequiredArgsConstructor
public class YTSUserDetailService implements UserDetailsService {
	
	private final YTSUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		YTSUser ytsUser = loadApplicationUserByUsername(username);
		return new User(ytsUser.getUsername(), ytsUser.getPassword(), ytsUser.getAuthorities());
	}

	private YTSUser loadApplicationUserByUsername(String username) {
		return Optional.ofNullable(repository.findByUsername(username))
					.orElseThrow(() -> new UsernameNotFoundException("User not found."));
	}

}

