package br.les.opus.instagram.api.endpoints;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import br.les.opus.instagram.api.InstagramClient;
import br.les.opus.instagram.api.InstagramException;
import br.les.opus.instagram.api.util.GsonInstagramBuilder;
import br.les.opus.instagram.api.util.InstagramURIBuilder;
import br.les.opus.instagram.domain.MediaTagEnvelope;

public class TagService extends EndpointConsumer {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String BASE_ENDPOINT = "tags/";

	public TagService(InstagramClient client) {
		super(client);
	}

	private MediaTagEnvelope findAllRecentMediaByTag(String tagName, Map<String, String> parameters) {
		try {
			StringBuffer buffer = new StringBuffer(BASE_ENDPOINT);
			buffer.append(tagName);
			buffer.append("/media/recent");
			
			InstagramURIBuilder uriBuilder = getURIBuilder();
			uriBuilder.setPath(buffer.toString());
			for (String key : parameters.keySet()) {
				uriBuilder.setParameter(key, parameters.get(key));
			}
			
			URI uri = uriBuilder.build();
			logger.info("Requisitando: " + uri.toString());
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = this.executeRequest(httpGet);
			
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				throw new InstagramException("Unexpected response: " + status);
			}
			String json = this.getStringContent(response);
			return new GsonInstagramBuilder().build().fromJson(json, MediaTagEnvelope.class);
		} catch (Exception e) {
			throw new InstagramException(e.getMessage(), e);
		}
	}
	
	public MediaTagEnvelope findAllRecentMediaByTag(String tagName) {
		return findAllRecentMediaByTag(tagName, new HashMap<String, String>());
	}
	
	public MediaTagEnvelope findAllRecentMediaByTag(String tagName, String maxTagId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("max_tag_id", maxTagId);
		return findAllRecentMediaByTag(tagName, parameters);
	}
}
