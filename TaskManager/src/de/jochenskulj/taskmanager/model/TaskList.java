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

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.util.Formatter;

/**
 * List for Task elements
 */
public class TaskList extends ElementListBase {

	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param aModel
     *        application model that the list belongs to
     */
    public TaskList(ApplicationModel aModel) {
        super(aModel, new ElementType(ElementType.TASK));
        getType().setDetailFlag(true);
        getType().setFilterFlag(true);
        addColumnName("Task");
        addColumnName("Due Date");
        addColumnName("Status");
    }

    /**
     * creates a new element in the list
     * @return the new element
     */
    public ElementBase createNewElement() {
    	
    	logger.debug("TaskList.createNewElement() entered");
    	
    	setActive(false);
        TaskElement result = new TaskElement(getModel());
        setSelectedRow(getRowCount() - 1);
        StringBuffer buffer = new StringBuffer();
        buffer.append("New Task ");
        buffer.append(getRowCount() + 1);
        result.setValue(0, buffer.toString());
        setModeInternal(ElementListBase.DETAIL_MODE);
        addElement(result);
        setActive(true);
    	
    	logger.debug("TaskList.createNewElement() exit	ered");
    	
        return result;
    }

    @Override
    public Class<?> getColumnClass(int i) {
    	Class result = null;
    	if (i == 0) {
    		result = String.class;
    	}
    	if (i == 1) {
    		result = String.class;
    	}
    	if (i == 2) {
    		result = TaskStatus.class;
    	}
        return result;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object result = null;
        if (row < getElements().size()) {
            TaskElement element = (TaskElement) getElements().get(row);
            if (element != null) {
                if (col == 0) {
                	result = element.getName();
                }
               if (col == 1) {
        	   		result = Formatter.formatDateString(element.getDueDate());
                }
               if (col == 2) {
            	   result = element.getStatus();
               }
            }
        }
        return result;
    }
}
