package br.les.opus.instagram.domain;

import com.google.gson.annotations.SerializedName;

public class TagPagination {

	@SerializedName("min_tag_id")
	private String minTagId;
	
	@SerializedName("max_tag_id")
	private String maxTagId;
	
	@SerializedName("next_url")
	private String nextUrl;
	
	@SerializedName("next_max_tag_id")
	private String nextMaxTagId;

	public String getMinTagId() {
		return minTagId;
	}

	public void setMinTagId(String minTagId) {
		this.minTagId = minTagId;
	}

	public String getMaxTagId() {
		return maxTagId;
	}

	public void setMaxTagId(String maxTagId) {
		this.maxTagId = maxTagId;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	@Override
	public String toString() {
		return "TagPagination [minTagId=" + minTagId + ", maxTagId=" + maxTagId + "]";
	}

	public String getNextMaxTagId() {
		return nextMaxTagId;
	}

	public void setNextMaxTagId(String nextMaxTagId) {
		this.nextMaxTagId = nextMaxTagId;
	}
	
	public boolean hasNextPage() {
		return this.nextUrl != null && !this.nextMaxTagId.isEmpty();
	}
	
}
