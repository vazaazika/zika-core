package br.les.opus.instagram.api.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonInstagramBuilder {

	public Gson build() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateGsonDeserializer());
		return gsonBuilder.create();
	}
}
