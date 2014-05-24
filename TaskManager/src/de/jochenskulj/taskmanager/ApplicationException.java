/*
 *  Folivora -- Java Swing Application for Task Management
 *  Copyright (C) 2013 Jochen Skulj <jochen@jochenskulj.de>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/
 */
package de.jochenskulj.taskmanager;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * Class for Application Exceptions 
 */
public class ApplicationException extends Exception {

	private static Logger logger = Logger.getRootLogger();
	
	public static final String FILE_WRITE_ERROR = "Unable to write file {0}";
	public static final String FILE_READ_ERROR = "Unable to read file {0}";

	private String message;
	
	/**
	 * creates an ApplicationException from another exception
	 * @param anException
	 *        causing exception
	 */
	public ApplicationException(Exception anException) {
		MessageFormat mf = new MessageFormat("{0} ({1})");
		Object[] args = { anException.getMessage(), anException.getClass().toString() };
		message = mf.format(args);
		logger.error(message);
	}
	
	/**
	 * creates an ApplicationException with a given message
	 * @param aMessage
	 *        message of the application
	 */
	public ApplicationException(String aMessage) {
		message = aMessage;
		logger.error(message);
	}

	/**
	 * creates an ApplicationException by a given message
	 * @param aFormat
	 *        message format to use
	 * @param anArgument
	 *        argument to include in the message
	 */
	public ApplicationException(String aFormat, Object anArgument) {
		MessageFormat mf = new MessageFormat(aFormat);
		Object[] args = { anArgument };
		message = mf.format(args);
		logger.error(message);
	}
	
	/**
	 * returns message of the exception
	 * @return message of the exception
	 */
	public String getMessage() {
		return message;
	}
}
