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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElement;

/**
 * Panel to display Task references
 */
public class TaskReferencePanel extends JPanel {

	private String[] columnNames = { "Task", "Status", "Due Date" };
	private TaskReferenceModel model;
	private JTable table;

	/**
	 * create instance
	 */
	public TaskReferencePanel() {
		setLayout(new GridBagLayout());
		model = new TaskReferenceModel();
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 10;
		c.weighty = 10;
		add(new JScrollPane(scrollPane), c);
	}

	/**
	 * updates the references to display
	 * @param aReferences
	 *        object which tasks should be added
	 */
	public void setTaskReferences(TaskReference aReferences) {
		model.setTaskReferences(aReferences);
	}
}
