package br.les.opus.instagram.api.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Dates in Instagram APi are returned as integers. An integer representing a date is the time (in seconds)
 * passed since 1/1/1970. Because of that, to convert to Java date we just need to multiply by 1000. 
 * @author Diego Cedrim
 *
 */
public class DateGsonDeserializer implements JsonDeserializer<Date> {

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Long longTimestamp = json.getAsLong() * 1000;
		return new Date(longTimestamp);
	}


}
