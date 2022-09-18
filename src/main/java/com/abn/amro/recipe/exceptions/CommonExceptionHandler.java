package com.abn.amro.recipe.exceptions;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abn.amro.recipe.model.response.ErrorResponse;

@RestControllerAdvice
public class CommonExceptionHandler {

	/**
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse resourceNotFound(final ResourceNotFoundException ex) {
		return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse HandleException(final Exception ex) {
		return new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	public ErrorResponse handle(ValidationException ex) {
		return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}