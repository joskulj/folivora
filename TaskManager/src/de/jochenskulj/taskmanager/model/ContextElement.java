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
import java.util.Collection;

import de.jochenskulj.taskmanager.component.TaskReference;

/**
 * Element class for contextes
 */
public class ContextElement extends ElementBase implements TaskReference {
    
    private String context;
    private ArrayList<TaskElement> tasks = new ArrayList<TaskElement>();
    
    /**
     * creates an instance
     */
    public ContextElement() {
        context = "";
    }
    
    /**
     * returns the context value
     * @return context value
     */
    public String getContext() {
        return context;
    }
    
    /**
     * returns the tasks that are assigned to the project
     * @return collection of the tasks of the project
     */
    public Collection<TaskElement> getTasks() {
    	return tasks;
    }
    
    /**
     * checks, if the context contains a given task
     * @param aTask
     *        task to check
     * @return <code>true</code>, if the task is assigned, <code>false</code>,
     *         if not
     */
    public boolean containsTask(TaskElement aTask) {
    	return tasks.contains(aTask);
    }
    
    /**
     * returns the list, that the element belongs to
     * @return list, that the element belongs to
     */
    @Override
    public Object getValue(int anIndex) {
        Object result = null;
        if (anIndex == 0) {
            result = context;
        }
        return result;
    }

    /**
     * sets the context value
     * @param aContext
     *        context value
     */
    public void setContext(String aContext) {
    	if (context != null && aContext != null) {
    		if (context.equals(aContext) == false) {
    			setModified();
    		}
    	} else {
    		setModified();
    	}
        context = aContext;
        fireChanged();
    }
    
    /**
     * adds a task to the project
     * @param aTask
     *        task to add
     */
    public void addTask(TaskElement aTask) {
    	if (!tasks.contains(aTask)) {
    		tasks.add(aTask);
    		fireChanged();
    	}
    }
    
    /**
     * removes a task from the project
     * @param aTask
     *        task to remove
     */
    public void removeTask(TaskElement aTask) {
    	if (tasks.contains(aTask)) {
    		tasks.remove(aTask);
    	}
    }

    /**
     * sets the value object at a given index
     * @param anIndex
     *        index of a value object
     * @param anObject
     *        new value object to set
     */
    @Override
    public void setValue(int anIndex, Object anObject) {
        if ((anIndex == 0) && (anObject instanceof String)) {
        	setContext((String) anObject);
        }
    }
    
    @Override
    public String toString() {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("[");
    	buffer.append("context");
    	buffer.append(" = ");
    	buffer.append(context);
    	buffer.append("]");
    	return buffer.toString();
    }
}
