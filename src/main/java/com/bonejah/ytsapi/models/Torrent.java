package com.bonejah.ytsapi.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Torrent implements Serializable {

	private static final long serialVersionUID = -504670623046701279L;

	@JsonProperty("url")
	private String url;

	@JsonProperty("hash")
	private String hash;

	@JsonProperty("quality")
	private String quality;

	@JsonProperty("type")
	private String type;

	@JsonProperty("seeds")
	private int seeds;

	@JsonProperty("peers")
	private int peers;

	@JsonProperty("size")
	private String size;

	@JsonProperty("size_bytes")
	private long sizeBytes;

	@JsonProperty("date_uploaded")
	private String dateUploaded;

	@JsonProperty("date_uploaded_unix")
	private String dateUploadedUnix;

}
