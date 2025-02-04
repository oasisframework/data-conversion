package io.github.oasisframework.data.conversion.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class JsonUtil {
	private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
		OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * Object to Json
	 *
	 * @param src - The parameter type Object
	 * @return returns String
	 */
	public static String objectToJson(Object src) {
		try {
			return OBJECT_WRITER.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			log.error("[objectToJson()] :: exception occurred: ", e);
			return null;
		}
	}

	/**
	 * Json to Object
	 *
	 * @param json - The parameter type String
	 * @return returns Object
	 */
	public static <T> T jsonToObject(String json, Class<? extends T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			log.error("[jsonToObject()] :: exception occurred: ", e);
			return null;
		}
	}

	/**
	 * Json to Parameterized Object
	 *
	 * @param json - Parameter type String
	 * @return Return type Parameterized Object
	 */
	public static Object jsonToParameterizedObject(String json, TypeReference<?> tr) {
		try {
			return OBJECT_MAPPER.readValue(json, tr);
		} catch (IOException e) {
			log.info("[jsonToParameterizedObject()] :: exception occurred: ", e);
			return null;
		}
	}
}

