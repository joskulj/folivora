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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.jochenskulj.taskmanager.component.GridBagPanel;
import de.jochenskulj.taskmanager.component.ProjectCombobox;
import de.jochenskulj.taskmanager.component.TaskStatusComboBox;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElementFilter;
import de.jochenskulj.taskmanager.model.TaskStatus;

public class TaskFilterDialog extends JDialog {

	private ElementListBase list;
	private ProjectCombobox project;
	private TaskStatusComboBox taskStatus;
	/**
	 * creates an instance
	 * @param aParent
	 *        Frame that owns the dialog
	 * @param aList
	 *        list to filter
	 */
	public TaskFilterDialog(JFrame aParent, ElementListBase aList) {
		super(aParent);
		list = aList;
		setSize(new Dimension(300, 250));
	    if (aParent != null) {
	        Dimension parentSize = aParent.getSize(); 
	        Point p = aParent.getLocation(); 
	        setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }
	    init();
	    // pack();
	}

	/**
	 * sets the filter
	 */
	public void setFilter() {
		TaskElementFilter filter = new TaskElementFilter();
		ProjectElement selectedProject = 
				(ProjectElement) project.getSelectedItem();
		filter.setProject(selectedProject);
		TaskStatus selectedStatus = taskStatus.getValue();
		System.out.println(selectedStatus);
		if (selectedStatus != null) {
			filter.setStatus(selectedStatus);
		}
		list.setFilter(filter);
	    setVisible(false); 
	    dispose(); 
	}
	
	/**
	 * clears the filter
	 */
	public void clearFilter() {
		list.clearFilter();
	    setVisible(false); 
	    dispose(); 
	}

	/**
	 * closes the dialog
	 */
	public void closeDialog() {
	    setVisible(false); 
	    dispose(); 
	}
	
	/**
	 * inits the dialog
	 */
	protected void init() {
		setTitle("Task Filter");
		setLayout(new GridBagLayout());
		
		JPanel contentPanel = createContentPanel();
		JPanel buttonPanel = createButtonPanel();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2;
		c.weighty = 2;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.insets = new Insets(5, 5, 5, 5);
		add(contentPanel, c);
				
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.SOUTHEAST;
		add(buttonPanel, c);
	}

	/**
	 * creates the panel for input element
	 * @return panel containing the input panels
	 */
	protected JPanel createContentPanel() {
		JPanel panel = new JPanel();
		project = new ProjectCombobox(list.getModel());
		taskStatus = new TaskStatusComboBox(true);
		
		panel.setLayout(new GridBagLayout());
				
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(new JLabel("Project:"), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(project, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(new JLabel("Status:"), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(taskStatus, c);
		
		//panel.addComponent("Status:", taskStatus);
		return panel;
	}
	
	/**
	 * creates the panel for buttons
	 * @return panel containing the buttons
	 */
	protected JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JButton setFilterButton = new JButton("Filter");
		setFilterButton.addActionListener(
				new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						setFilter();				
					}
				}
		);
		JButton clearFilterButton = new JButton("Clear");
		clearFilterButton.addActionListener(
				new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						clearFilter();				
					}
				}
		);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(new JLabel(), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(setFilterButton, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(clearFilterButton, c);

		return panel;
	}
}
