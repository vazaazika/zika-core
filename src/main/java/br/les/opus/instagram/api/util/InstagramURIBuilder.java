package br.les.opus.instagram.api.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public class InstagramURIBuilder {

	private URIBuilder uriBuilder;
	
	private static final String BASE_PATH = "/v1/"; 
	
	public InstagramURIBuilder() {
		this.uriBuilder = new URIBuilder();
		this.uriBuilder.setScheme("https");
		this.uriBuilder.setHost("api.instagram.com");
	}
	
	public void setClientId(String clientId) {
		this.uriBuilder.setParameter("client_id", clientId);
	}
	
	public void setAccessToken(String token) {
		this.uriBuilder.setParameter("access_token", token);
	}
	
	public void setPath(String path) {
		this.uriBuilder.setPath(BASE_PATH + path);
	}
	
	public void setParameter(String name, String value) {
		this.uriBuilder.setParameter(name, value);
	}
	
	public URI build() throws URISyntaxException {
		return this.uriBuilder.build();
	}
}
