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
package de.jochenskulj.taskmanager.model;

/**
 * Model class for Priotities
 */
public class Priority {
    
    public static final String HIGH = "High";
    public static final String MEDIUM = "Medium";
    public static final String LOW = "Low";
    public static final String DONE = "Done";

    public static final String[] VALID_VALUES = {
        HIGH, MEDIUM, LOW, DONE  
    };
    
    private String value = MEDIUM;
    
    /**
     * creates an instance with the default value
     */
    public Priority() {
        
    }
    
    /**
     * creates an instance with a given value
     * @param aValue
     */
    public Priority(String aValue) {
        setValue(aValue);
    }
 
    /**
     * returns the value
     * @return value of the instance
     */
    public String getValue() {
        return value;
    }
    
    /**
     * sets the value
     * @param aValue
     *        value to set
     */
    public void setValue(String aValue) {
        // check value
        boolean valid = false;
        for (String checkValue : VALID_VALUES) {
            if (value.equals(checkValue)) {
                valid = true;
                break;
            }
        }
        // set value
        if (valid) {
            value = aValue;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
