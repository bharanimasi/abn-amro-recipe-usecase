package com.abn.amro.recipe.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abn.amro.recipe.constants.Messages;
import com.abn.amro.recipe.dao.entity.Recipes;
import com.abn.amro.recipe.dao.repository.RecipesCustomeRepository;
import com.abn.amro.recipe.dao.repository.RecipesRepository;
import com.abn.amro.recipe.exceptions.ResourceNotFoundException;
import com.abn.amro.recipe.mapper.AbnAmroRecipeMapper;
import com.abn.amro.recipe.model.request.AbnAmroRecipeRequest;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;

@Service("abnAmroRecipeService")
public class AbnAmroRecipeServiceImpl implements AbnAmroRecipeService {

	private static Logger logger = LoggerFactory.getLogger(AbnAmroRecipeServiceImpl.class);

	@Autowired
	RecipesRepository recipesRepository;

	@Autowired
	AbnAmroRecipeMapper abnAmroRecipeMapper;

	@Autowired
	RecipesCustomeRepository recipesCustomRepository;

	public AbnAmroRecipeServiceImpl(RecipesRepository recipesRepository, AbnAmroRecipeMapper abnAmroRecipeMapper,
			RecipesCustomeRepository recipesCustomRepository) {

		this.recipesRepository = recipesRepository;
		this.abnAmroRecipeMapper = abnAmroRecipeMapper;
		this.recipesCustomRepository = recipesCustomRepository;
	}

	@Override
	public List<AbnAmroRecipeResponse> addAbnAmroRecipe(@Valid List<AbnAmroRecipeRequest> recipeList) {

		logger.info("AbnAmroRecipeServiceImpl ::addAbnAmroRecipe ");

		List<Recipes> recipes = abnAmroRecipeMapper.destinationToSources(recipeList);

		return abnAmroRecipeMapper.sourceToDestinations(recipesRepository.saveAll(recipes));

	}

	@Override
	public AbnAmroRecipeResponse updateRecipe(@Valid Integer recipeId, AbnAmroRecipeRequest request) {

		logger.info("AbnAmroRecipeServiceImpl ::updateRecipe ");

		Recipes recipes = recipesRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

		abnAmroRecipeMapper.updateDtotoEntity(recipes, request);

		return abnAmroRecipeMapper.sourceToDestination(recipesRepository.save(recipes));
	}

	@Override
	public void removeRecipe(@Valid Integer recipeId) {

		logger.info("AbnAmroRecipeServiceImpl ::removeRecipe");

		recipesRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

		recipesRepository.deleteById(recipeId);
	}

	@Override
	public List<AbnAmroRecipeResponse> findRecipes(Map<String, Object> criteriaParams) {
		logger.info("AbnAmroRecipeServiceImpl ::findRecipes");

		return abnAmroRecipeMapper.sourceToDestinations(recipesCustomRepository.findByCriteria(criteriaParams));
	}

}
