package com.bonejah.ytsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
*
* brunolima on Mar 13, 2021
* 
*/
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -5323996184656285785L;

	public BadRequestException(String message) {
		super(message);
	}
	
}

