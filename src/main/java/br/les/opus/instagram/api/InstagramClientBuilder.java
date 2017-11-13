package br.les.opus.instagram.api;

public class InstagramClientBuilder {

	private InstagramClient client;
	
	public InstagramClientBuilder() {
		this.client = new InstagramClient();
	}
	
	public InstagramClientBuilder clientId(String clientId) {
		this.client.setClientId(clientId);
		return this;
	}
	
	public InstagramClientBuilder accessToken(String accessToken) {
		this.client.setAccessToken(accessToken);
		return this;
	}
	
	public InstagramClient build() {
		return client;
	}
}
