package com.ims.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ims.model.Product;

@Component
public class DatabaseUtils {

	private DatabaseUtils() {
	}

	public static void updateNonNullProperties(Product source, Product target) {
		for (Field field : Product.class.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(source);
				if (value != null) {
					field.set(target, value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	// This is currently not in use while saving details into database.
	// This will not allow to input null values into database, instead it will save
	// the default values set below
	public static void applyDefaultValues(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				if (field.get(obj) == null) {
					if (field.getType().equals(String.class)) {
						field.set(obj, StringUtils.EMPTY);

					} else if (field.getType().equals(Long.class)) {
						field.set(obj, 0L);

					} else if (field.getType().equals(Integer.class)) {
						field.set(obj, 0);
					}
					// Add more default value rules for other data types if needed
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Failed to set default value for field: " + field.getName() + "Error is : " + e.getMessage());
			}
		}
	}
}
