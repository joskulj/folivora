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
package de.jochenskulj.taskmanager.filter;

import de.jochenskulj.taskmanager.component.GridBagPanel;
import de.jochenskulj.taskmanager.model.ApplicationModel;

/**
 * Base class for filter panels
 */
public abstract class FilterPanelBase extends GridBagPanel {

	private ApplicationModel model;
	
	/**
	 * creates an instance
	 * @param aModel
	 *        model to apply the filter
	 */
	public FilterPanelBase(ApplicationModel aModel) {
		model = aModel;
	}
	
	/**
	 * applies the filter
	 */
	public abstract void applyFilter();
	
	/**
	 * clears the filter
	 */
	public abstract void clearFilter();
}
