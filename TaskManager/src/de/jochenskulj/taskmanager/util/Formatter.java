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
package de.jochenskulj.taskmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.ApplicationProperty;

/**
 * Helper class to format values 
 */
public class Formatter {

	/**
	 * returns pattern to scan date values
	 * @return date pattern
	 */
	public static String getDatePattern() {
		String result = null;
		ApplicationProperty property = new ApplicationProperty();
		result = property.get(ApplicationProperty.DATEFORMAT);
		if (result == null) {
			result = "MM/dd/yyyy";
		}
		return result;
	}

	/**
	 * formats a date to a string
	 * @param aDate
	 *        date to format
	 * @return formatted string
	 */
	public static String formatDateString(Date aDate) {
		String result = "";
		if (aDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());
			result = sdf.format(aDate);
		}
		return result;
	}
	
	/**
	 * checks if a String is a valid date
	 * @param aString
	 *        String to check
	 * @return <code>true</code> string is a valid date
	 *         <code>false</code> string is not a valid date
	 */
	public static boolean checkDateString(String aString) {
		boolean result = true;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());
			sdf.setLenient(false);
			Date d = sdf.parse(aString);
		} catch(ParseException e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * converts a string to a date
	 * @param aString
	 *        date with a string value
	 * @return parsed Date
	 */
	public static Date convertStringDate(String aString) {
		Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());
			sdf.setLenient(false);
			result = sdf.parse(aString);
		} catch(ParseException e) {
			result = null;
		}
		return result;
	}
}
