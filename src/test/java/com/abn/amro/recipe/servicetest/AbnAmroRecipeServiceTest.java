package com.abn.amro.recipe.servicetest;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.abn.amro.recipe.dao.entity.Recipes;
import com.abn.amro.recipe.dao.repository.RecipesCustomeRepository;
import com.abn.amro.recipe.dao.repository.RecipesCustomeRepositoryImpl;
import com.abn.amro.recipe.dao.repository.RecipesRepository;
import com.abn.amro.recipe.mapper.AbnAmroRecipeMapper;
import com.abn.amro.recipe.mapper.AbnAmroRecipeMapperImpl;
import com.abn.amro.recipe.model.request.AbnAmroRecipeRequest;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;
import com.abn.amro.recipe.services.AbnAmroRecipeService;
import com.abn.amro.recipe.services.AbnAmroRecipeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AbnAmroRecipeServiceTest {

	@Mock
	RecipesRepository recipesRepository;

	@Mock
	RecipesCustomeRepository customeRepository;

	@InjectMocks
	AbnAmroRecipeService abnAmroRecipeService;

	AbnAmroRecipeMapper abnAmroRecipeMapper;

	List<AbnAmroRecipeRequest> abnAmroRequests;

	AbnAmroRecipeRequest abnAmroRequest;

	Recipes recipe;

	List<Recipes> recipes;

	List<AbnAmroRecipeResponse> abnAmroRecipeResponses;

	AbnAmroRecipeResponse abnAmroRecipeResponse;

	@BeforeEach
	void setup() {

		// Assign

		abnAmroRecipeMapper = Mockito.mock(AbnAmroRecipeMapperImpl.class);
		recipesRepository = Mockito.mock(RecipesRepository.class);
		customeRepository = Mockito.mock(RecipesCustomeRepositoryImpl.class);
		abnAmroRecipeService = new AbnAmroRecipeServiceImpl(recipesRepository, abnAmroRecipeMapper, customeRepository);

		List<String> ingredients = new ArrayList<>();
		ingredients.add("Paneer");
		ingredients.add("Onion");
		ingredients.add("Oil");

		abnAmroRequest = new AbnAmroRecipeRequest();
		abnAmroRequest.setRecipeName("Test Recipe");
		abnAmroRequest.setServingsNumber(10);
		abnAmroRequest.setRecipetype("Veg");
		abnAmroRequest.setRecipeInstructions("Toast in Oven");
		abnAmroRequest.setIngredients(ingredients);

		abnAmroRequests = new ArrayList<>();
		abnAmroRequests.add(abnAmroRequest);

		recipe = new Recipes();

		// recipe.setId(1);
		recipe.setRecipeName("Test Recipe");
		recipe.setServingsNumber(10);
		recipe.setRecipetype("Veg");
		recipe.setRecipeInstructions("Toast in Oven");
		recipe.setIngredients("Paneer,Onion,Oil");

		recipes = new ArrayList<Recipes>();
		recipes.add(recipe);

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
	void test_addAbnAmroRecipe() {

		// ACT
		when(abnAmroRecipeMapper.destinationToSources(any())).thenReturn(recipes);
		when(recipesRepository.saveAll(any())).thenReturn(recipes);
		when(abnAmroRecipeMapper.sourceToDestinations(any())).thenReturn(abnAmroRecipeResponses);
		List<AbnAmroRecipeResponse> abnAmroRecipeResponses = abnAmroRecipeService.addAbnAmroRecipe(abnAmroRequests);

		// ASSET
		assertEquals(1, abnAmroRecipeResponses.size());
		assertEquals("Test Recipe", abnAmroRecipeResponses.get(0).getRecipeName());

	}

	@Test
	void test_updateRecipe() {

		// ASSIGN
		recipe.setId(1);

		// ACT
		when(recipesRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipesRepository.save(any())).thenReturn(recipe);
		when(abnAmroRecipeMapper.sourceToDestination(any())).thenReturn(abnAmroRecipeResponse);
		abnAmroRecipeService.updateRecipe(1, abnAmroRequest);

		// ASSERT
		assertEquals("Toast in Oven", abnAmroRecipeResponse.getRecipeInstructions());

	}

	@Test
	void test_updateRecipe_Exception() {
		// ACT
		Exception exception = assertThrows(RuntimeException.class, () -> {
			abnAmroRecipeService.updateRecipe(1, abnAmroRequest);
		});

		// ASSERT
		assertTrue("Recipe not found".contains(exception.getMessage()));
	}

	@Test
	void test_removeRecipe_Exception() {
		// ACT
		Exception exception = assertThrows(RuntimeException.class, () -> {
			abnAmroRecipeService.removeRecipe(1);
		});

		// ASSERT
		assertTrue("Recipe not found".contains(exception.getMessage()));
	}

	@Test
	void test_removeRecipe() {
		// ASSIGN
		recipe.setId(1);

		// ACT
		when(recipesRepository.findById(any())).thenReturn(Optional.of(recipe));
		abnAmroRecipeService.removeRecipe(1);

		// ASSERT
		verify(recipesRepository).deleteById(any());
	}

	@Test
	void test_findRecipes() {

		// ASSIGN
		Map<String, Object> criteriaParams = new HashMap<>();
		criteriaParams.put("recipetype", "Test Recipe");
		criteriaParams.put("servingNumber", 10);

		// ACT
		when(customeRepository.findByCriteria(any())).thenReturn(recipes);
		when(abnAmroRecipeMapper.sourceToDestinations(any())).thenReturn(abnAmroRecipeResponses);
		List<AbnAmroRecipeResponse> response = abnAmroRecipeService.findRecipes(criteriaParams);

		// ASSERT
		assertEquals(1, response.size());
		assertEquals("Veg", response.get(0).getRecipetype());

	}

	@Test
	void test_findRecipes_NoResult() {

		// ASSIGN
		Map<String, Object> criteriaParams = new HashMap<>();
		criteriaParams.put("recipetype", "New Recipe");
		criteriaParams.put("servingNumber", 12);

		// ACT
		when(customeRepository.findByCriteria(any())).thenReturn(recipes);
		when(abnAmroRecipeMapper.sourceToDestinations(any())).thenReturn(new ArrayList<>());
		List<AbnAmroRecipeResponse> response = abnAmroRecipeService.findRecipes(criteriaParams);

		// ASSERT
		assertEquals(0, response.size());

	}

}
