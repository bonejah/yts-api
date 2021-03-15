package com.bonejah.ytsapi.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bonejah.ytsapi.service.YTSUserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 *
 * brunolima on Mar 13, 2021
 * 
 */

@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final YTSUserDetailService ytsUserDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/yts-api/admin/**").hasRole("ADMIN")
				.antMatchers("/yts-api/**").hasRole("USER")
				.antMatchers("/login").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.anyRequest().authenticated()
			.and()
//				.formLogin().and().httpBasic(); // Authentication via Form
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager(), ytsUserDetailService));
			
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		log.info("Password encoded {}", passwordEncoder.encode("ytsapi"));
		
		auth.userDetailsService(ytsUserDetailService).passwordEncoder(passwordEncoder);
	}

	// Providers InMemory used in conjuction with formLogin
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//		log.info("Password encoded {}", passwordEncoder.encode("ytsapi"));
//
//		auth.inMemoryAuthentication()
//			.withUser("bonejah").password(passwordEncoder.encode("ytsapi")).roles("USER", "ADMIN")
//			.and()
//			.withUser("devdojo2").password(passwordEncoder.encode("academy")).roles("USER");
//	}
	
}
