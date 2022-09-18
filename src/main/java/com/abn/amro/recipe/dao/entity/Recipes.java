package com.abn.amro.recipe.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.abn.amro.recipe.dao.converter.StringCaseConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "recipeName")
	@NonNull
	@NotBlank
	@Convert(converter = StringCaseConverter.class)
	private String recipeName;

	@Column(name = "recipeInstructions")
	@Convert(converter = StringCaseConverter.class)
	private String recipeInstructions;

	@Column(name = "recipetype")
	@NotNull
	@NotBlank
	@Convert(converter = StringCaseConverter.class)
	private String recipetype;

	@Column(name = "servingsNumber")
	@Max(100)
	private Integer servingsNumber;

	@Column(name = "ingredients")
	@NonNull
	@NotBlank
	@Convert(converter = StringCaseConverter.class)
	private String ingredients;

	@Column(updatable = false)
	@CreationTimestamp
	private Timestamp createdDate;

	@Column
	@UpdateTimestamp
	private Timestamp updatedDate;

}
