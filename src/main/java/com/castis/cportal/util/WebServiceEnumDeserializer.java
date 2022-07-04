package com.castis.cportal.util;

import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Component
public class WebServiceEnumDeserializer implements JsonDeserializer<Object> {

	@Override
	public Object deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		Object obj = null;
		Class<?> clz = (Class<?>) type;
		try {
			if (json.isJsonPrimitive()) { // enum 값이 value만 올때. ex) "status":"CONFIRM"
				obj = clz.getDeclaredMethod("valueof", String.class).invoke(
						null, json.getAsString());
			} else if (json.isJsonObject()) { // enum 값이 json object로 올때. ex) "status":{"_value_":"CONFIRM"}
				JsonObject jobject = (JsonObject) json;
				if(jobject.has("value")){
					obj = clz.getDeclaredMethod("valueof", String.class).invoke(
							null, jobject.get("value").getAsString());
				}else if(jobject.has("_value_")){
					obj = clz.getDeclaredMethod("valueof", String.class).invoke(
							null, jobject.get("_value_").getAsString());
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return obj;
	}

}
