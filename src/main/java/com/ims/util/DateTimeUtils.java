package com.ims.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.ims.common.InventoryConstants;

@Component
public class DateTimeUtils {

	private DateTimeUtils() {
	}

	public static LocalDateTime formatDateTime(String dateTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(InventoryConstants.DATE_TIME_FORMAT);
		return LocalDateTime.parse(dateTime, formatter);
	}

}
