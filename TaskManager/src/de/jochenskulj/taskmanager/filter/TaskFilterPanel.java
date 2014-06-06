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

import javax.swing.JPanel;

import de.jochenskulj.taskmanager.component.ProjectCombobox;
import de.jochenskulj.taskmanager.component.TaskStatusComboBox;
import de.jochenskulj.taskmanager.model.ApplicationModel;

/**
 * Panel to filter task elements 
 */
public class TaskFilterPanel extends FilterPanelBase {

	private ApplicationModel model;
	private ProjectCombobox project;
	private TaskStatusComboBox taskStatus;
	
	/**
	 * creates an instance
	 * @param aModel
	 *        model to apply the filter
	 */
	public TaskFilterPanel(ApplicationModel aModel) {
		model = aModel;
		initElements();
	}
	
	/**
	 * applies the filter
	 */
	@Override
	public void applyFilter() {
		
	}
	
	/**
	 * clears the filter
	 */
	@Override
	public void clearFilter() {
		
	}

	/**
	 * initializes the elements
	 */
	protected void initElements() {
		project = new ProjectCombobox(model);
		taskStatus = new TaskStatusComboBox();
		JPanel buttonPanel = createButtonPanel();
		
		addComponent("Project:", project);
		addComponent("Status:", taskStatus);
		addComponent(buttonPanel);
	}
}