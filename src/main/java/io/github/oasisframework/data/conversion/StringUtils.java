package io.github.oasisframework.data.conversion;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StringUtils {
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String SEMI_COLON = ";";
	public static final String DOT = ".";

	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

		return pattern.matcher(nfdNormalizedString).replaceAll(EMPTY);
	}

	public static double convertStringToDouble(String value, double defaultValue) {
		try {
			if (isNotBlank(value)) {
				return Double.parseDouble(value.trim().replaceAll(COMMA, DOT));
			}
		} catch (NumberFormatException e) {
			return defaultValue;
		}
		return defaultValue;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((!Character.isWhitespace(str.charAt(i))))
				return false;
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isTrimEmpty(String text) {
		return text == null || text.trim().isEmpty();
	}

	public static String strJoin(String separator, final Object... args) {
		if (args == null) {
			return null;
		}

		String sep = isNotBlank(separator) ? separator : EMPTY;

		StringJoiner joiner = new StringJoiner(sep);

		Arrays.stream(args).filter(Objects::nonNull).map(String::valueOf).forEachOrdered(joiner::add);

		return joiner.toString();
	}

	public static String convertToBullet(String value, int maxSize) {
		String[] bulletList = convertToInfoArray(value);
		return convertToBullet(bulletList, maxSize);
	}

	public static String convertToBullet(String[] bulletList, int maxSize) {
		StringBuilder newText = new StringBuilder(EMPTY);

		int bulletLength = (maxSize == -1 || bulletList.length < maxSize) ? bulletList.length : maxSize;

		for (int i = 0; i < bulletLength; i++) {
			if (i != 0) {
				newText.append(System.getProperty("line.separator"));
			}
			newText.append("? ").append(bulletList[i].trim());
		}
		return newText.toString();
	}

	public static String[] convertToInfoArray(String value) {
		if (isBlank(value)) {
			return new String[] {};
		}

		String separatorRegex = "\\.";
		String trimmedText = value.trim();
		String text = trimmedText.endsWith(DOT) ? trimmedText.substring(0, trimmedText.length() - 1) : trimmedText;

		return text.split(separatorRegex);
	}

	public static String trim(String str) {
		if (isBlank(str)) {
			return EMPTY;
		}
		return str.trim();
	}

	public static List<String> parseStringByComma(String value) {
		if (isBlank(value)) {
			return new ArrayList<>();
		}

		return Arrays.stream(value.split(COMMA)).filter(StringUtils::isNotBlank).map(String::trim).collect(toList());
	}

	public static boolean isSearchStringContainedInInputString(String inputString, String searchStr, String delimiter) {
		if (searchStr == null) {
			return false;
		}

		return split(inputString, delimiter).stream().filter(Objects::nonNull).anyMatch(searchStr::equals);
	}

	public static String getNonNullString(String actualValue, String defaultValue) {
		if (isNotBlank(actualValue)) {
			return actualValue;
		}
		if (isNotBlank(defaultValue)) {
			return defaultValue;
		}

		return EMPTY;
	}

	public static List<String> split(String text, String splitter) {
		try {
			return Arrays.stream(text.split(splitter)).collect(toList());
		} catch (Throwable t) {
			log.warn("Text splitting operation is faced with an exception. Text={} :: Splitter={}", text, splitter,
					t);
			return Collections.emptyList();
		}

	}
}

