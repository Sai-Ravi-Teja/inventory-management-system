package com.ims.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtils {

	private DateTimeUtils() {
	}

	/**
	 * <p>
	 * Returns DateTime in the requested format.
	 * </p>
	 */
	public static LocalDateTime formatDateTime(String dateTime, String dateTimePattern) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
		return LocalDateTime.parse(dateTime, formatter);
	}

	/**
	 * <p>
	 * Returns DateTime in the requested format for the given ISO 8601 format.
	 * </p>
	 */
	public static String formatISODateTime(String dateTime, String dateTimePattern) {

		LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
		return localDateTime.format(formatter);
	}

	/**
	 * <p>
	 * Returns current DateTime in the requested timeZone and format.
	 * </p>
	 */
	public static String localTimezoneData(String timeZone, String dateTimeFormat) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		String[] timeZoneArray = timeZone.split(" ");
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(timeZoneArray[0]));
		return zdt.format(formatter);

	}
}
