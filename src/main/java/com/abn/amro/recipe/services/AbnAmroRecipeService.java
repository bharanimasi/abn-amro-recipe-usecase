package com.abn.amro.recipe.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.abn.amro.recipe.model.request.AbnAmroRecipeRequest;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;

public interface AbnAmroRecipeService {

	List<AbnAmroRecipeResponse> addAbnAmroRecipe(@Valid List<AbnAmroRecipeRequest> recipeList);

	AbnAmroRecipeResponse updateRecipe(@Valid Integer recipeId, AbnAmroRecipeRequest request);

	void removeRecipe(@Valid Integer recipeId);

	List<AbnAmroRecipeResponse> findRecipes(Map<String, Object> criteriaParams);

}
