/*
 *  Folivora -- Java Swing Application for Task Management
 *  Copyright (C) 2014 Jochen Skulj <jochen@jochenskulj.de>
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
package de.jochenskulj.taskmanager.model;

/**
 * Base class to filter list elements
 */
public abstract class ElementFilterBase {

	/**
	 * returns the type, for which the filter is designed
	 * @return type of the elements that could be checked
	 */
	public abstract ElementType getType();
	
	/**
	 * checks, if a given element matches the filter criteria
	 * @param anElement
	 *        element to check
	 * @return <code>true</code> if the elements match the filter criteria;
	 *         otherwise <code>false</code>
	 */
	public abstract boolean checkElement(ElementBase anElement);
}
