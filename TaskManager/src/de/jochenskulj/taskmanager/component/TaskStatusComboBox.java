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
	public static String EMPTY_STRING = "";
	
	/**
     * creates an instance
     */
    public TaskStatusComboBox() {
    	initItems(false);
    }
    
    /**
     * creates an instance
     * @param emptyFlag
     *        flag, if an empty selection should be allowed
     */
    public TaskStatusComboBox(boolean emptyFlag) {
    	initItems(emptyFlag);
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
		String statusLabel = (String) getSelectedItem();
		if (statusLabel != null) {
			if (statusLabel.equals(EMPTY_STRING) == false) {
				result = new TaskStatus(statusLabel);
			}
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
    
    /**
     * initializes the items to select
     * @param emptyFlag
     *        flag, if an empty selection should be allowed
     */
    protected void initItems(boolean emptyFlag) {
    	if (emptyFlag) {
    		addItem(EMPTY_STRING);
    	}
    	for (String item : VALUES) {
    		addItem(item);
    	}
    	
    }
}
