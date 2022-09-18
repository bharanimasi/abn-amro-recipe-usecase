package com.abn.amro.recipe.dao.repository;

import java.util.List;
import java.util.Map;

import com.abn.amro.recipe.dao.entity.Recipes;

public interface RecipesCustomeRepository {

	List<Recipes> findByCriteria(Map<String, Object> params);

}
