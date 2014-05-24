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

import de.jochenskulj.taskmanager.model.Priority;

import javax.swing.JComboBox;

/**
 * ComboBox to select the Priority
 */
public class PriorityCombobox extends JComboBox {
    
    public static String[] VALUES = Priority.VALID_VALUES;
    
    /**
     * creates an instance
     */
    public PriorityCombobox() {
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
    public Priority getValue() {
        Priority result = null;
        int index = getSelectedIndex();
        if (index != -1) {
            result = new Priority(VALUES[index]);
        }
        return result;
    }
    
    /**
     * sets the value of the combobox
     * @param aPriority
     *        value to set
     */
    public void setValue(Priority aPriority) {
        int index = getIndex(aPriority.getValue());
        setSelectedIndex(index);
    }
}
