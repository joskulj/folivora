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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Element class  for Tasks
 */
public class TaskElement extends ElementBase {

	private String name;
	private ProjectElement project;
	private Date dueDate;
	private TaskStatus status;
	private ElementSelection contextSelection;
	private ElementSelection peopleSelection;
	private String notes;
	
	/**
	 * creates an instance
	 * @param anApplicationModel
	 *        model of the application
	 */
	public TaskElement(ApplicationModel anApplicationModel) {
		name = "";
		dueDate = getInitialDueDate();
		status = new TaskStatus(TaskStatus.PROPOSED);
		contextSelection = new ElementSelection(
				anApplicationModel.getContextList());
		peopleSelection = new ElementSelection(
				anApplicationModel.getPeopleList());
		notes = "";
	}
	
	/**
	 * returns the name of the task
	 * @return name of the task
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns the project that the task is assigned to
	 * @return project that the task is assigned to
	 */
	public ProjectElement getProject() {
		if (project != null) {
			if (project.isRegistered() == false) {
				project = null;
			}
		}
		return project;
	}
	
	/**
	 * returns the due date of the task
	 * @return dueDate date of the task
	 */
	public Date getDueDate() {
		return dueDate;
	}
	
	/**
	 * returns the status of the task
	 * @return status of the task
	 */
	public TaskStatus getStatus() {
		return status;
	}
	
	/**
	 * returns selection of contexts
	 * @return selection of contexts
	 */
	public ElementSelection getContextSelection() {
		return contextSelection;
	}

	/**
	 * returns selection of people
	 * @return selection of people
	 */
	public ElementSelection getPeopleSelection() {
		return peopleSelection;
	}
	
	/**
	 * returns the notes of the task
	 * @return notes of the task
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * sets the name of the task
	 * @param aName
	 *        name of the task
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
	 * sets the due date
	 * @param aDate
	 *        due date
	 */
	public void setDueDate(Date aDate) {
		if (dueDate != null && aDate != null) {
			if (dueDate.equals(aDate) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		dueDate = aDate;
		fireChanged();
	}
	
	/**
	 * sets the status
	 * @param aStatus
	 *        status of the date
	 */
	public void setStatus(TaskStatus aStatus) {
		if (status != null && aStatus != null) {
			if (status.equals(aStatus) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		status = aStatus;
		fireChanged();
	}
	
	/**
	 * sets the context selection
	 * @param aSelection
	 *        selection to set
	 */
	public void setContextSelection(ElementSelection aSelection) {
		if (contextSelection != null && aSelection != null) {
			if (contextSelection.equals(aSelection) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		contextSelection = aSelection;
		fireChanged();
	}	
	/**
	 * sets the people selection
	 * @param aSelection
	 *        selection to set
	 */
	public void setPeopleSelection(ElementSelection aSelection) {
		if (peopleSelection != null && aSelection != null) {
			if (peopleSelection.equals(aSelection) == false) {
				setModified();
			}
		} else {
			setModified();
		} 
		peopleSelection = aSelection;
		fireChanged();
	}
	
	/**
	 * sets the project
	 * @param aProject
	 *        project to set
	 */
	public void setProject(ProjectElement aProject) {
		if (project != null && aProject != null) {
			if (project.equals(aProject) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		if (project != aProject) {
			if (project != null) {
				project.removeTaskInternal(this);
			}
			project = aProject;
			if (project != null) {
				project.addTaskInternal(this);
			}
			fireChanged();
		}
	}
	
	/**
	 * sets the notes
	 * @param aNotes
	 *        notes to set
	 */
	public void setNotes(String aNotes) {
		if (notes != null && aNotes != null) {
			if (notes.equals(aNotes) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		notes = aNotes;
		fireChanged();
	}
	
	@Override
	public Object getValue(int anIndex) {
        Object result = null;
        if (anIndex == 0) {
            result = name;
        }
        if (anIndex == 1) {
            result = project;
        }
        if (anIndex == 2) {
        	result = dueDate;
        }
        if (anIndex == 3) {
        	result = status;
        }
        return result;
	}

	@Override
	public void setValue(int anIndex, Object anObject) {
        if (anIndex == 0 && anObject instanceof String) {
        	setName((String) anObject);
        }
        if (anIndex == 1 && anObject instanceof ProjectElement) {
        	setProject((ProjectElement) anObject);
        }
        if (anIndex == 2 && anObject instanceof Date) {
        	setDueDate((Date) anObject);
        }
        if (anIndex == 3 && anObject instanceof TaskStatus) {
        	setStatus((TaskStatus) anObject);
        }
	}

	/**
	 * calculates an initial due date
	 * @return initial due date
	 */
	private Date getInitialDueDate() {
		Date result = null;
		Date today = new Date();

		// get current year
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String yearString = sdf.format(today);
		int yearValue = Integer.parseInt(yearString);

		// get current month
		sdf = new SimpleDateFormat("MM");
		String monthString = sdf.format(today);
		int monthValue = Integer.parseInt(monthString);

		// create date instance
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, 12);
		if (monthValue != 12) {
			yearValue++;
		}
		cal.set(Calendar.YEAR, yearValue - 1);
		result = cal.getTime();
		return result;
	}
}