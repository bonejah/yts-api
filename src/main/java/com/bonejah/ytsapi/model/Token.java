package com.bonejah.ytsapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
*
* brunolima on Mar 15, 2021
* 
*/

@Data
@AllArgsConstructor
public class Token implements Serializable {

	private static final long serialVersionUID = 8937067284008740951L;

	@JsonProperty("access_token")
	private String accessToken;
	
}
