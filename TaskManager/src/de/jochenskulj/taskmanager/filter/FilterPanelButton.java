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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Class for buttons within the filter panel
 */
public class FilterPanelButton extends JButton implements ActionListener {

	public static final int MODE_APPLY_FILTER = 0;
	public static final int MODE_CLEAR_FILTER = 1;
	
	private FilterPanelBase parentPanel;
	private int mode;
	
	/**
	 * creates an instance
	 * @param aPanel
	 *        parent panel
	 * @param aMode
	 */
	public FilterPanelButton(FilterPanelBase aPanel, int aMode) {
		parentPanel = aPanel;
		mode = aMode;
		if (mode == MODE_APPLY_FILTER) {
			setText("Apply");
		}
		if (mode == MODE_CLEAR_FILTER) {
			setText("Clear");
		}
	}
	
    /**
     * handles an event
     * @param arg0
     *        event to handle
     */
    public void actionPerformed(ActionEvent arg0) {
		if (mode == MODE_APPLY_FILTER) {
			parentPanel.applyFilter();
		}
		if (mode == MODE_CLEAR_FILTER) {
			parentPanel.clearFilter();
		}
    }
}
