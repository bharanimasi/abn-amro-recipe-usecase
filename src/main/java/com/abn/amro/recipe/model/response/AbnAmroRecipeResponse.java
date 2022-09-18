package com.abn.amro.recipe.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AbnAmroRecipeResponse {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("recipeName")
	String recipeName;

	@JsonProperty("servingsNumber")
	Integer servingsNumber;

	@JsonProperty("recipetype")
	String recipetype;

	@JsonProperty("ingredients")
	List<String> ingredients;

	@JsonProperty("recipeInstructions")
	String recipeInstructions;

}
