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

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Class to display the status 
 */
public class StatusLabel extends JLabel {
	
	/**
	 * creates an instance
	 */
	public StatusLabel() {
		super();
	}
	
	/**
	 * clears the current status
	 */
	public void clearStatus() {
		setStatus("", false);
	}

	/**
	 * updates the status
	 * @param statusText
	 *        text to display
	 */
	public void setStatus(String statusText) {
		setStatus(statusText, false);
	}

	/**
	 * updates the status
	 * @param statusText
	 *        text to display
	 * @param errorFlag
	 *        indicates if the status represents an error
	 */
	public void setStatus(String statusText, boolean errorFlag) {
		setText(statusText);
		if (errorFlag == true) {
			setForeground(Color.RED);
		} else {
			setForeground(Color.BLACK);
		}
	}
}
