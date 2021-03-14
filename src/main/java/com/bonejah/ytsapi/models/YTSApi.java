package com.bonejah.ytsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * brunolima on Mar 12, 2021
 * 
 */
@Data
@NoArgsConstructor
public class YTSApi {

	@JsonProperty("movie_count")
	private Integer movieCount;

}
