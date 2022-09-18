package com.abn.amro.recipe.dao.converter;

import javax.persistence.AttributeConverter;

public class StringCaseConverter implements AttributeConverter<String, String> {
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute == null ? null : attribute.toUpperCase();
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData == null ? null : dbData.toUpperCase();
	}
}
