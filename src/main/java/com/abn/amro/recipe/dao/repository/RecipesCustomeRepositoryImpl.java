package com.abn.amro.recipe.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abn.amro.recipe.constants.Constants;
import com.abn.amro.recipe.dao.entity.Recipes;

@Repository
public class RecipesCustomeRepositoryImpl implements RecipesCustomeRepository {

	private static Logger logger = LoggerFactory.getLogger(RecipesCustomeRepositoryImpl.class);

	@Autowired
	EntityManager em;

	@Override
	public List<Recipes> findByCriteria(Map<String, Object> params) {

		logger.info("RecipesCustomeRepositoryImpl :: findByCriteria");

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Recipes> cq = cb.createQuery(Recipes.class);

		Root<Recipes> recipes = cq.from(Recipes.class);
		List<Predicate> predicates = buildFilterCriteria(params, recipes, cb);
		cq.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Recipes> typedQuery = em.createQuery(cq);

		if (params.get(Constants.PAGE_SIZE) != null && params.get(Constants.PAGE_NUMBER) != null) {
			typedQuery.setFirstResult(((Integer) params.get(Constants.PAGE_SIZE)).intValue()
					* ((Integer) params.get(Constants.PAGE_NUMBER)).intValue());

			typedQuery.setMaxResults((Integer) params.get(Constants.PAGE_SIZE));
		}
		return typedQuery.getResultList();
	}

	private List<Predicate> buildFilterCriteria(final Map<String, Object> params, Root<Recipes> recipes,
			CriteriaBuilder cb) {
		logger.info("RecipesCustomeRepositoryImpl :: buildFilterCriteria");
		final List<Predicate> predicate = new ArrayList<>();
		if (params.get(Constants.RECIPE_TYPE) != null)
			predicate.add(
					cb.equal(recipes.get("recipetype"), params.get(Constants.RECIPE_TYPE).toString().toUpperCase()));

		if (params.get(Constants.SERVING_NUMBER) != null)
			predicate.add(cb.le(recipes.get("servingsNumber"),
					Integer.parseInt(params.get(Constants.SERVING_NUMBER).toString())));

		if (params.get(Constants.INCLUDE_INGREDIENT) != null)
			predicate.add(cb.like(recipes.get("ingredients"), Constants.PERCENTAGE
					+ params.get(Constants.INCLUDE_INGREDIENT).toString().toUpperCase() + Constants.PERCENTAGE));

		if (params.get(Constants.EXCLUDE_INGREDIENT) != null)
			predicate.add(cb.notLike(recipes.get("ingredients"), Constants.PERCENTAGE
					+ params.get(Constants.EXCLUDE_INGREDIENT).toString().toUpperCase() + Constants.PERCENTAGE));

		if (params.get(Constants.INSTRUCTIONS) != null)
			predicate.add(cb.like(recipes.get("recipeInstructions"), Constants.PERCENTAGE
					+ params.get(Constants.INSTRUCTIONS).toString().toUpperCase() + Constants.PERCENTAGE));

		return predicate;
	}

}
