package com.abn.amro.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abn.amro.recipe.dao.converter.StringCaseConverter;

@RunWith(MockitoJUnitRunner.class)
public class StringCaseConverterTest {

	@MockBean
	StringCaseConverter caseConverter;

	@BeforeEach
	void setup() {
		caseConverter = new StringCaseConverter();
	}

	@Test
	void test_convertToDatabaseColumn() {

		// ACT
		String result = caseConverter.convertToDatabaseColumn("test");

		// ASSERT
		assertEquals("TEST", result);
	}

	@Test
	void test_convertToDatabaseColumn_NULL() {

		// ACT
		String result = caseConverter.convertToDatabaseColumn(null);

		// ASSERT
		assertEquals(null, result);
		;
	}

	@Test
	void test_convertToEntityAttribute() {

		// ACT
		String result = caseConverter.convertToEntityAttribute("test");

		// ASSERT
		assertEquals("TEST", result);
	}

	@Test
	void test_convertToEntityAttribute_NULL() {

		// ACT
		String result = caseConverter.convertToEntityAttribute(null);

		// ASSERT
		assertEquals(null, result);
	}

}
