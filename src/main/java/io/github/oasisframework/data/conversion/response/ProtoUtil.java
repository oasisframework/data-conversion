package io.github.oasisframework.data.conversion.response;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class ProtoUtil {
	private static final ZoneId ISTANBUL_ZONE = ZoneId.of("Europe/Istanbul");
	private static final String DATE_FORMAT_DATE_TIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssXXX";
	private static final ConcurrentHashMap<Class<?>, Method> PROTO_BUILDER_METHOD_CACHE = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertProtoToPlainObject(GeneratedMessageV3 messageV3) {
		try {
			String json = JsonFormat.printer().print(messageV3);
			return JsonUtil.jsonToObject(json, Map.class);
		} catch (InvalidProtocolBufferException e) {
			return null;
		}
	}

	public static <T extends Message.Builder> T createBuilder(Class<? extends Message> clazz) {
		T builder = getMessageBuilder(clazz);
		Descriptors.FieldDescriptor fdCurrentTime = builder.getDescriptorForType().findFieldByName("currentTime");
		if (fdCurrentTime != null) {
			builder.setField(fdCurrentTime, getFormattedTime());
		}
		return builder;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Message.Builder> T getMessageBuilder(Class<? extends Message> clazz) {
		try {
			Method method = PROTO_BUILDER_METHOD_CACHE.getOrDefault(clazz, clazz.getMethod("newBuilder"));
			PROTO_BUILDER_METHOD_CACHE.putIfAbsent(clazz, method);

			return (T) method.invoke(clazz);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static String getFormattedTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_DATE_TIME_WITH_TIMEZONE).withZone(ISTANBUL_ZONE);
		return ZonedDateTime.now().format(formatter);
	}
}

