package com.abn.amro.recipe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abn.amro.recipe.model.request.AbnAmroRecipeRequest;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;
import com.abn.amro.recipe.services.AbnAmroRecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/recipes")
@Validated
public class AbnAmroRecipeController {

	private static Logger logger = LoggerFactory.getLogger(AbnAmroRecipeController.class);

	@Autowired
	AbnAmroRecipeService abnAmroRecipeService;

	public AbnAmroRecipeController(AbnAmroRecipeService abnAmroRecipeService) {
		this.abnAmroRecipeService = abnAmroRecipeService;
	}

	@Operation(summary = "Method allow to add different recipes ")
	@PostMapping
	public ResponseEntity<List<AbnAmroRecipeResponse>> addAbnAmroRecipe(
			@Parameter(description = "Input list of recipe(s) to be created") @Valid @RequestBody List<AbnAmroRecipeRequest> recipeList) {
		logger.info("Add recipes", recipeList);
		return new ResponseEntity<>(abnAmroRecipeService.addAbnAmroRecipe(recipeList), HttpStatus.CREATED);
	}

	@Operation(summary = "Method allow to update recipe based on recipeId")
	@PutMapping(value = "/{recipeId}")
	public ResponseEntity<AbnAmroRecipeResponse> updateRecipe(
			@Parameter(description = "Pass recipeId to be updated") @Valid @PathVariable(value = "recipeId") Integer recipeId,
			@Parameter(description = "Input recipe details to be updated") @RequestBody AbnAmroRecipeRequest request) {
		logger.info("Update Request: ", request);

		return new ResponseEntity<>(abnAmroRecipeService.updateRecipe(recipeId, request), HttpStatus.OK);
	}

	@Operation(summary = "Method allow to delete recipe based on recipeId")
	@DeleteMapping(value = "/{recipeId}")
	public ResponseEntity<String> removeRecipe(
			@Parameter(description = "Pass recipeId to be deleted") @Valid @PathVariable Integer recipeId) {
		logger.info("Delete Recipe: ", recipeId);
		abnAmroRecipeService.removeRecipe(recipeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Method allow to get recipe based on different search criterias")
	@GetMapping
	public ResponseEntity<List<AbnAmroRecipeResponse>> retrieveRecipes(
			@Parameter(description = "Pass recipe type") @Valid @RequestParam(required = false) String recipetype,
			@Parameter(description = "Pass serving number") @Valid @RequestParam(required = false) Integer servings,
			@Parameter(description = "Pass ingredient to be available in recipe") @RequestParam(required = false) String includeIngredient,
			@Parameter(description = "Pass ingredient to be excluded from recipe") @RequestParam(required = false) String excludeIngredient,
			@Parameter(description = "Pass instruction value to be texted search") @RequestParam(required = false) String instructions,
			@Parameter(description = "Pass pageNumber value to be searched with n+1") @RequestParam(required = false) Integer pageNumber,
			@Parameter(description = "Pass pageSize value to be get number reults per page") @RequestParam(required = false) Integer pageSize) {

		logger.info("Fetch Recipes based on seacr criteria:");

		Map<String, Object> criteriaParams = new HashMap<>();
		criteriaParams.put("recipetype", recipetype);
		criteriaParams.put("servingNumber", servings);
		criteriaParams.put("includeIngredient", includeIngredient);
		criteriaParams.put("excludeIngredient", excludeIngredient);
		criteriaParams.put("instructions", instructions);
		criteriaParams.put("pageNumber", pageNumber);
		criteriaParams.put("pageSize", pageSize);

		return new ResponseEntity<>(abnAmroRecipeService.findRecipes(criteriaParams), HttpStatus.OK);
	}

}
