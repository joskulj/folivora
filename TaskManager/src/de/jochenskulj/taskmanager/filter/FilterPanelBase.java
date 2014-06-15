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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.jochenskulj.taskmanager.component.GridBagPanel;
import de.jochenskulj.taskmanager.model.ApplicationModel;

/**
 * Base class for filter panels
 */
public abstract class FilterPanelBase extends GridBagPanel {

	public FilterPanelBase() {
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		setBackground(Color.LIGHT_GRAY);
	}
	
	/**
	 * applies the filter
	 */
	public abstract void applyFilter();
	
	/**
	 * clears the filter
	 */
	public abstract void clearFilter();
	
	/**
	 * creates the panel for filter buttons
	 * @return panel for filter buttons
	 */
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new GridBagLayout());
		
		FilterPanelButton applyButton = new FilterPanelButton(this,
				FilterPanelButton.MODE_APPLY_FILTER);
		FilterPanelButton clearButton = new FilterPanelButton(this,
				FilterPanelButton.MODE_CLEAR_FILTER);
		JPanel spacePanel = new JPanel();
		spacePanel.setBackground(Color.LIGHT_GRAY);
		
		GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(applyButton, c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(clearButton, c);
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(spacePanel, c);
		
		return buttonPanel;
	}
}
