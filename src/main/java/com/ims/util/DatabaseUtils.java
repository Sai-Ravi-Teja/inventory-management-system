package com.ims.util;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.ims.model.Product;

@Component
public class DatabaseUtils {

	public void updateNonNullProperties(Product source, Product target) {
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
}
