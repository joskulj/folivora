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

import javax.swing.JComboBox;

import de.jochenskulj.taskmanager.model.Priority;
import de.jochenskulj.taskmanager.model.TaskStatus;

/**
 * ComboBox to select a Task Status
 */
public class TaskStatusComboBox extends JComboBox {

	public static String[] VALUES = TaskStatus.VALID_VALUES;
	
	/**
     * creates an instance
     */
    public TaskStatusComboBox() {
        super(VALUES);
    }
    
    /**
     * returns the list index of a given value
     * @param aValue
     *        a value
     * @return list index of the value
     */
    private int getIndex(String aValue) {
        int result = -1;
        for (int i = 0; i < VALUES.length; i++) {
            if (VALUES[i].equals(aValue)) {
                result = i;
                break;
            }
        }
        return result;
    }
    
    /**
     * returns the selected value
     * @return selected index
     */
    public TaskStatus getValue() {
    	TaskStatus result = null;
        int index = getSelectedIndex();
        if (index != -1) {
            result = new TaskStatus(VALUES[index]);
        }
        return result;
    }
    
    /**
     * sets the value of the combobox
     * @param aStatus
     *        value to set
     */
    public void setValue(TaskStatus aStatus) {
        int index = getIndex(aStatus.getValue());
        setSelectedIndex(index);
    }
}
