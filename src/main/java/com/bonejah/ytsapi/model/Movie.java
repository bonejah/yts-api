package com.bonejah.ytsapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Movie implements Serializable {

	private static final long serialVersionUID = -5433829931414749815L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_current;
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("imdb_code")
	private String imdbCode;

	@JsonProperty("title")
	private String title;

	@JsonProperty("title_english")
	private String title_english;

	@JsonProperty("title_long")
	private String title_long;

	@JsonProperty("slug")
	private String slug;

	@JsonProperty("year")
	private int year;

	@JsonProperty("rating")
	private double rating;

	@JsonProperty("runtime")
	private int runtime;

	@JsonProperty("genres")
	@ElementCollection(targetClass = String.class)
	private List<String> genres;

	@JsonProperty("torrents")
	@ElementCollection(targetClass = Torrent.class)
	private List<Torrent> torrents;

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("description_full")
	private String description_full;

	@JsonProperty("synopsis")
	private String synopsis;

	@JsonProperty("yt_trailer_code")
	private String ytTrailerCode;

	@JsonProperty("language")
	private String language;

	@JsonProperty("mpaRating")
	private String mpaRating;

	@JsonProperty("background_image")
	private String backgroundImage;

	@JsonProperty("background_image_original")
	private String backgroundImageOriginal;

	@JsonProperty("small_cover_image")
	private String smallCoverImage;

	@JsonProperty("medium_cover_image")
	private String mediumCoverImage;

	@JsonProperty("large_cover_image")
	private String largeCoverImage;

	@JsonProperty("state")
	private String state;

	@JsonProperty("date_uploaded")
	private String dateUploaded;

	@JsonProperty("date_uploaded_unix")
	private String dateUploadedUnix;

}
