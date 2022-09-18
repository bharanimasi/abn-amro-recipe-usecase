package com.abn.amro.recipe.model.request;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AbnAmroRecipeRequest {

	@NotNull(message = "recipeName field is required")
	@NotBlank(message = "recipeName field value is required")
	@Size(min = 1, max = 50, message = "recipeName must be between 1 and 50 characters")
	String recipeName;

	@NotNull(message = "servings number field is required")
	@Min(1)
	@Max(100)
	Integer servingsNumber;

	@NotNull(message = "recipetype isrequired")
	@NotBlank(message = "recipeName field value is required")
	@Pattern(regexp = "Veg|Non-veg", flags = Pattern.Flag.CASE_INSENSITIVE)
	String recipetype;

	@NotEmpty(message = "ingredients field is required")
	List<String> ingredients;

	@Size(min = 1, max = 1000, message = "instructions must be between 1 and 1000 characters")
	@NotBlank(message = "instructions field value is required")
	String recipeInstructions;

}
