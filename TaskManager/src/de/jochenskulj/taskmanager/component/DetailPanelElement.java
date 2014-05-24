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
package de.jochenskulj.taskmanager.component;

import de.jochenskulj.taskmanager.view.DetailPanelBase;

/**
 * Interface for elements of DetailPanals 
 */
public interface DetailPanelElement {

	/**
	 * returns, if the element is valid
	 * @return <code>true</code>: element is valid
	 *         <code>false</code>: element is not valid
	 */
	public boolean isValid();

	/**
	 * returns, if the element has been validated
	 * @return <code>true</code>: element  has been validated
	 *         <code>false</code>: element has not been validated
	 */
	public boolean isValidated();
	
	/**
	 * sets the DetalPanel
	 * @param aPanel
	 *        DetailPanel that the element belongs to
	 */
	public void setDetailPanel(DetailPanelBase aPanel);
	
	/**
	 * updates the element according to the validation
	 */
	public void updateValidationStatus();
	
	/**
	 * sets the focus to the element
	 */
	public void setFocus();

	/**
	 * validates the element
	 * @return <code>true</code>: element is valid
	 *         <code>false</code>: element is not valid
	 */
	public boolean validateElement();
}
