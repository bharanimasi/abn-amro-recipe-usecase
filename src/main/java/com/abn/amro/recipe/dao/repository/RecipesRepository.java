package com.abn.amro.recipe.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abn.amro.recipe.dao.entity.Recipes;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer>  {

}
