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
 * Class for the Task Status
 */
public class TaskStatus {
	
    public static final String PROPOSED = "Proposed";
    public static final String IN_PROGRESS = "In Progress";
    public static final String COMPLETED = "Completed";
    public static final String WAITING = "Waiting";

    public static final String[] VALID_VALUES = {
        PROPOSED, IN_PROGRESS, COMPLETED, WAITING  
    };
	
	private String value = PROPOSED;

	/**
	 * creates an instance
	 */
	public TaskStatus() {
		
	}

	/**
	 * creates an instance
	 * @param aValue
	 *        status value
	 */
	public TaskStatus(String aValue) {
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
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof TaskStatus) {
			TaskStatus otherStatus = (TaskStatus) obj;
			result = value.equals(otherStatus.getValue());
		}
		return result;
	}
}
