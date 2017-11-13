package br.les.opus.instagram.api;

import br.les.opus.instagram.api.endpoints.MediaEndpointService;
import br.les.opus.instagram.api.endpoints.TagService;

public class InstagramClient {

	private String clientId;
	
	private String accessToken;
	
	InstagramClient() {

	}
	
	public TagService getTagService() {
		return new TagService(this);
	}
	
	public MediaEndpointService getMediaService() {
		return new MediaEndpointService(this);
	}
	
	void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientId() {
		return clientId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String acessToken) {
		this.accessToken = acessToken;
	}
	
}
