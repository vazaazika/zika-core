package br.les.opus.instagram.api.endpoints;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import br.les.opus.instagram.api.InstagramClient;
import br.les.opus.instagram.api.util.InstagramURIBuilder;

public abstract class EndpointConsumer {

	private InstagramClient client;
	
	private HttpClient httpClient;

	public EndpointConsumer(InstagramClient client) {
		this.client = client;
		
		RequestConfig config = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .build();
		this.httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
	}

	protected InstagramClient getClient() {
		return client;
	}
	
	protected InstagramURIBuilder getURIBuilder() {
		InstagramURIBuilder builder = new InstagramURIBuilder();
		builder.setClientId(client.getClientId());
		builder.setAccessToken(client.getAccessToken());
		return builder;
	}
	
	protected String getStringContent(HttpResponse response) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		HttpEntity entity = response.getEntity();
		InputStream input = entity.getContent();
		IOUtils.copy(input, output);
		input.close();
		return output.toString("UTF-8");
	}

	protected HttpResponse executeRequest(HttpUriRequest request) throws IOException {
		HttpResponse response = httpClient.execute(request);
		return response;
	}
}
