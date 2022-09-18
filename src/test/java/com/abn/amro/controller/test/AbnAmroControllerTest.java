package com.abn.amro.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abn.amro.recipe.AbnAmroReceipeCaseApplication;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;
import com.abn.amro.recipe.services.AbnAmroRecipeService;

@AutoConfigureMockMvc
@SpringBootTest(classes = AbnAmroReceipeCaseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbnAmroControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AbnAmroRecipeService abnAmroRecipeService;

	List<AbnAmroRecipeResponse> abnAmroRecipeResponses;

	AbnAmroRecipeResponse abnAmroRecipeResponse;

	@BeforeEach
	void setup() {

		// Assign
		List<String> ingredients = new ArrayList<>();
		ingredients.add("Paneer");
		ingredients.add("Onion");
		ingredients.add("Oil");

		abnAmroRecipeResponses = new ArrayList<>();

		abnAmroRecipeResponse = new AbnAmroRecipeResponse();
		abnAmroRecipeResponse.setRecipeName("Test Recipe");
		abnAmroRecipeResponse.setServingsNumber(10);
		abnAmroRecipeResponse.setRecipetype("Veg");
		abnAmroRecipeResponse.setRecipeInstructions("Toast in Oven");
		abnAmroRecipeResponse.setIngredients(ingredients);

		abnAmroRecipeResponses.add(abnAmroRecipeResponse);

	}

	@Test
	@DisplayName(value = "This api is used to search  favourite recipes based on different filter criterieas")
	void retrieveRecipes() throws Exception {

		// ACT
		when(abnAmroRecipeService.findRecipes(any())).thenReturn(abnAmroRecipeResponses);

		// PERFORM
		mvc.perform(get("/recipes").queryParam("recipeName", "Test Recipe")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].recipetype").value("Veg"));

	}

	@Test
	@DisplayName(value = "This API test is uesd to add favourite recipes with details descriptions and details")
	void addAbnAmroRecipe() throws Exception {

		// ACT
		when(abnAmroRecipeService.addAbnAmroRecipe(any())).thenReturn(abnAmroRecipeResponses);

		// PERFORM
		mvc.perform(post("/recipes")
				.content("[\r\n" + "  {\r\n" + "    \"recipeName\": \"Test Recipe\",\r\n"
						+ "    \"servingsNumber\": 10,\r\n" + "    \"recipetype\": \"Veg\",\r\n"
						+ "    \"ingredients\": [\r\n" + "      \"Paneer\",\"Oil\"\r\n" + "    ],\r\n"
						+ "    \"recipeInstructions\": \"Fry and Mix\"\r\n" + "  }\r\n" + "]")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].servingsNumber").value(10));

	}

	@Test
	@DisplayName(value = "This API is used to validate add recipe without details . As a result will throw error")
	void addAbnAmroRecipe_Exception() throws Exception {

		// PERFORM
		mvc.perform(post("/recipes")).andExpect(status().is5xxServerError());

	}

	@Test
	@DisplayName(value = "This API is used to update the existing recipe")
	void updateRecipe() throws Exception {

		// ACT
		when(abnAmroRecipeService.updateRecipe(any(), any())).thenReturn(abnAmroRecipeResponse);

		// PERFORM
		mvc.perform(put("/recipes/1")
				.content("{\r\n" + "    \"recipeName\": \"Test Recipe\",\r\n" + "    \"servingsNumber\": 10,\r\n"
						+ "    \"recipetype\": \"Veg\",\r\n" + "    \"ingredients\": [\r\n"
						+ "      \"Paneer\",\"Oil\"\r\n" + "    ],\r\n"
						+ "    \"recipeInstructions\": \"Fry and Mix\"\r\n" + "  }")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipeName").value("Test Recipe"));

	}

	@Test
	@DisplayName(value = "This API is used to remove the existing recipe")
	void removeRecipe() throws Exception {

		// PERFORM
		mvc.perform(delete("/recipes/1")).andExpect(status().is2xxSuccessful());

	}

}
