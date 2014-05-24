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

import javax.swing.JTable;

import de.jochenskulj.taskmanager.model.ElementSelection;

public class ElementSelectionTable extends JTable {

	private ElementSelectionModel model;
	
	/**
	 * creates instance
	 */
	public ElementSelectionTable() {
		model = new ElementSelectionModel();
		setModel(model);
		setFillsViewportHeight(true);
	}

	/**
	 * sets the element selection to display
	 * @param aSelection
	 *        element selection to display
	 */
	public void setSelection(ElementSelection aSelection) {
		model.setSelection(aSelection);
	}
	
	/**
	 * returns element selection
	 * @return element selection
	 */
	public ElementSelection getSelection() {
		return model.getSelection();
	}
}
