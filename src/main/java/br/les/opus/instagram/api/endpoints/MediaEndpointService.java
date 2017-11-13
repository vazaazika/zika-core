package br.les.opus.instagram.api.endpoints;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import br.les.opus.instagram.api.InstagramClient;
import br.les.opus.instagram.api.InstagramException;
import br.les.opus.instagram.api.util.GsonInstagramBuilder;
import br.les.opus.instagram.api.util.InstagramURIBuilder;
import br.les.opus.instagram.domain.Media;
import br.les.opus.instagram.domain.SingleMediaEnvelope;

import com.google.gson.Gson;

public class MediaEndpointService extends EndpointConsumer {
	
private Logger logger = Logger.getLogger(getClass());
	
	private static final String BASE_ENDPOINT = "media/";

	public MediaEndpointService(InstagramClient client) {
		super(client);
	}
	
	public Media findById(String id) {
		try {
			StringBuffer buffer = new StringBuffer(BASE_ENDPOINT);
			buffer.append(id);
			
			InstagramURIBuilder uriBuilder = getURIBuilder();
			uriBuilder.setPath(buffer.toString());
			
			URI uri = uriBuilder.build();
			logger.info("Requisitando: " + uri.toString());
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = this.executeRequest(httpGet);
			
			int status = response.getStatusLine().getStatusCode();
			logger.info("Resposta obtida: " + status);
			if (status == HttpStatus.SC_BAD_REQUEST) {
				return null;
			}
			if (status != HttpStatus.SC_OK) {
				throw new InstagramException("Unexpected response: " + status);
			}
			String json = this.getStringContent(response);
			Gson gson = new GsonInstagramBuilder().build();
			SingleMediaEnvelope mediaEnvelope = gson.fromJson(json, SingleMediaEnvelope.class);
			return mediaEnvelope.getData();
		} catch (Exception e) {
			throw new InstagramException(e.getMessage(), e);
		}
	}

}
