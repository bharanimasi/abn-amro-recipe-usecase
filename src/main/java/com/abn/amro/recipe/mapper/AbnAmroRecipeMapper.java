package com.abn.amro.recipe.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.abn.amro.recipe.dao.entity.Recipes;
import com.abn.amro.recipe.model.request.AbnAmroRecipeRequest;
import com.abn.amro.recipe.model.response.AbnAmroRecipeResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AbnAmroRecipeMapper {

	AbnAmroRecipeMapper MAPPER = Mappers.getMapper(AbnAmroRecipeMapper.class);

	@Mapping(target = "ingredients", expression = "java(java.util.Collections.singletonList(source.getIngredients()))")
	AbnAmroRecipeResponse sourceToDestination(Recipes source);

	@Mapping(target = "ingredients", expression = "java(java.util.Collections.singletonList(source.getIngredients()))")
	List<AbnAmroRecipeResponse> sourceToDestinations(List<Recipes> sources);

	@Mapping(target = "ingredients", expression = "java(String.join(\",\", destination.getIngredients()))")
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "updatedDate", ignore = true)
	@Mapping(target = "id", ignore = true)
	Recipes destinationToSource(AbnAmroRecipeRequest destination);

	@Mapping(target = "ingredients", expression = "java(String.join(\",\", destination.getIngredients()))")
	List<Recipes> destinationToSources(List<AbnAmroRecipeRequest> destinations);

	@Mapping(target = "ingredients", expression = "java(String.join(\",\", destination.getIngredients()))")
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "updatedDate", ignore = true)
	@Mapping(target = "id", ignore = true)
	void updateDtotoEntity(@MappingTarget Recipes source, AbnAmroRecipeRequest destination);

}
