package io.github.oasisframework.data.conversion;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class NumberUtils {
	public static int parseInt(String numericString) {
		return parseInt(numericString, false);
	}

	public static int parseInt(String numericString, boolean returnZeroIfError) {
		if (!StringUtils.isTrimEmpty(numericString)) {
			try {
				return Integer.parseInt(numericString);
			} catch (NumberFormatException e) {
				log.warn("String value cannot be parsed as integer.");
			}
		}
		if (returnZeroIfError) {
			return 0;
		}

		return -1;
	}

	public static long parseLong(String value) {
		return parseLong(value, false);
	}

	public static long parseLong(String numericString, boolean returnZeroIfError) {
		if (!StringUtils.isTrimEmpty(numericString)) {
			try {
				return Long.parseLong(numericString);
			} catch (NumberFormatException e) {
				log.warn("String value cannot be parsed as long.");
			}
		}
		if (returnZeroIfError) {
			return 0;
		}

		return -1;
	}

	public static double parseDouble(String numericString) {
		return parseDouble(numericString, false);
	}

	public static double parseDouble(String numericString, boolean returnZeroIfError) {
		try {
			if (StringUtils.isNotBlank(numericString)) {
				return Double.parseDouble(numericString.trim().replaceAll(",", "."));
			}
		} catch (NumberFormatException e) {
			log.warn("String value cannot be parsed as double.");
		}

		if (returnZeroIfError) {
			return 0;
		}

		return -1;
	}


	public static BigDecimal divideStrings(String dividend, String divider) {
		BigDecimal bg1 = new BigDecimal(dividend);
		BigDecimal bg2 = new BigDecimal(divider);

		return bg2.divide(bg1);
	}
}

