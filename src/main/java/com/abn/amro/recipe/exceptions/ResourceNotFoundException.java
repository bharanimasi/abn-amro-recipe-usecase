package com.abn.amro.recipe.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

	/**
	 *
	 * @param message
	 */
	public ResourceNotFoundException(final String message) {
		super(message);
		logger.error(message);
	}

	/**
	 *
	 * @param message
	 * @param cause
	 */
	public ResourceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
		logger.error(message, cause);
	}
}
