package br.les.opus.instagram.domain;

import com.google.gson.annotations.SerializedName;

public class Pagination {
	
	@SerializedName("next_url")
	private String nextUrl;
	
	@SerializedName("next_max_id")
	private Long nextMaxId;

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public Long getNextMaxId() {
		return nextMaxId;
	}

	public void setNextMaxId(Long nextMaxId) {
		this.nextMaxId = nextMaxId;
	}
	
	
}
