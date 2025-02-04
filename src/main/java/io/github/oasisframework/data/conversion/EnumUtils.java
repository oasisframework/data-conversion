package io.github.oasisframework.data.conversion;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class EnumUtils {
	public static String getEnumName(Enum<?> enumObject) {
		if (enumObject == null) {
			return StringUtils.EMPTY;
		}
		return enumObject.name();
	}
}
