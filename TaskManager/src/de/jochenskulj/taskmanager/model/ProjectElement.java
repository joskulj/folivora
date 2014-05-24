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
 * Element class  for projects
 */
public class ProjectElement extends ElementBase implements TaskReference {
    
    private String name;
    private Priority priority;
    private ArrayList<TaskElement> tasks = new ArrayList<TaskElement>(); 
    
    /**
     * creates an instance
     */
    public ProjectElement() {
        name = "";
        priority = new Priority(Priority.MEDIUM);
    }
    
    /**
     * returns the name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * returns the priority
     * @return priority
     */
    public Priority getPriority() {
        return priority;
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
            result = name;
        }
        if (anIndex == 1) {
            result = priority;
        }
        return result;
    }
    
    /**
     * sets the name
     * @param aName
     *        name to set
     */
    public void setName(String aName) {
    	if (name != null && aName != null) {
    		if (name.equals(aName) == false) {
    			setModified();
    		}
    	} else {
    		setModified();
    	}
        name = aName;
        fireChanged();
    }
    
    /**
     * sets the priority
     * @param aPriority
     *        priority to set
     */
    public void setPriority(Priority aPriority) {
    	if (priority != null && aPriority != null) {
    		if (priority.getValue().equals(aPriority.getValue()) == false) {
    			setModified();  		                                                                
    		}
    	} else {
    		setModified();
    	}
        priority = aPriority;
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
    		aTask.setProject(this);
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
    		if (aTask.getProject() == this) {
    			aTask.setProject(null);
    		}
    		fireChanged();
    	}
    }

    /**
     * adds a task to the project (internal operation)
     * @param aTask
     *        task to add
     */
    void addTaskInternal(TaskElement aTask) {
    	if (!tasks.contains(aTask)) {
    		tasks.add(aTask);
    		fireChanged();
    	}
    }
    
    /**
     * removes a task from the project (internal operation)
     * @param aTask
     *        task to remove
     */
    void removeTaskInternal(TaskElement aTask) {
    	if (tasks.contains(aTask)) {
    		tasks.remove(aTask);
    		fireChanged();
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
            setName((String) anObject);
        }
        if ((anIndex == 1) && (anObject instanceof Priority)) {
            setPriority((Priority) anObject);
        }

    }
    
    public String toString() {
    	return name;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ProjectElement) {
			ProjectElement otherProject = (ProjectElement) obj;
			result = name.equals(otherProject.name);
		}
		return result;
	}
}
