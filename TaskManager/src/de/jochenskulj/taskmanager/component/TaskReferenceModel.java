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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElement;
import de.jochenskulj.taskmanager.util.Formatter;

public class TaskReferenceModel extends AbstractTableModel {

	private String[] columnNames = { "Task", "Status", "Due Date" };
	private ArrayList<TaskElement> tasks = new ArrayList<TaskElement>();
	
	/**
	 * creates an instance
	 */
	public TaskReferenceModel() {
		
	}

	/**
	 * updates the references to display
	 * @param aReferences
	 *        object which tasks should be added
	 */
	public void setTaskReferences(TaskReference aReferences) {
		tasks.removeAll(tasks);
		for (TaskElement task : aReferences.getTasks()) {
			tasks.add(task);
		}
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
        return columnNames.length;
    }

	@Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public int getRowCount() {
		return tasks.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object result = null;
		TaskElement task = tasks.get(row);
		if (task != null) {
			if (col == 0) {
				result = task.getName();
			}
			if (col == 1) {
				result = task.getStatus();
			}
			if (col == 2) {
				result = Formatter.formatDateString(task.getDueDate());
			}
		}
		return result;
	}

	@Override
    public Class getColumnClass(int col) {
        return String.class;
    }

	@Override
	public boolean isCellEditable(int row, int col) {
        return false;
    }
}
