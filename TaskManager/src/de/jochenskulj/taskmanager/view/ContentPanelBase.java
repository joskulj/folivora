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
package de.jochenskulj.taskmanager.view;

import javax.swing.JPanel;

import de.jochenskulj.taskmanager.model.ElementType;

/**
 * Base class for content panels 
 */
public class ContentPanelBase extends JPanel {

	private boolean verticalFill;
	
	public ContentPanelBase() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * sets the flag, if the panel should vertically fill the complete
	 * content panel
	 * @param aFlag
	 *        flag, if the panel should vertically fill the complete the 
	 *        content panel
	 */
	public void setVerticalFill(boolean aFlag) {
		verticalFill = aFlag;
	}
	
	/**
	 * returns the flag, if the panel should vertically fill the complete
	 * content panel
	 * @return flag, if the panel should vertically fill the complete
	 *         content panel
	 */
	public boolean getVerticalFill() {
		return verticalFill;
	}
	
	/**
	 * returns the element type of the panel
	 * @return element type of the panel
	 */
	public ElementType getType() {
		return null;
	}
}
