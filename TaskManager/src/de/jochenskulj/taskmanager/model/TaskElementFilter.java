/*
 *  Folivora -- Java Swing Application for Task Management
 *  Copyright (C) 2014 Jochen Skulj <jochen@jochenskulj.de>
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
 * Class to filter Task elements
 */
public class TaskElementFilter extends ElementFilterBase {

	private ProjectElement project;
	private TaskStatus status;
	private ElementType type = new ElementType(ElementType.TASK);
	
	/**
	 * creates an instance
	 */
	public TaskElementFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * returns the type, for which the filter is designed
	 * @return type of the elements that could be checked
	 */
	@Override
	public ElementType getType() {
		return type;
	}
	
	/**
	 * sets the project to be used as filter criteria
	 * @param aProject
	 *        project to be used as filter criteria
	 */
	public void setProject(ProjectElement aProject) {
		project = aProject;
	}
	
	/**
	 * sets the task status to be used as filter criteria
	 * @param aStatus
	 *        task status to be used as filter criteria
	 */
	public void setStatus(TaskStatus aStatus) {
		status = aStatus;
	}

	/**
	 * checks, if a given element matches the filter criteria
	 * @param anElement
	 *        element to check
	 * @return <code>true</code> if the elements match the filter criteria;
	 *         otherwise <code>false</code>
	 */
	@Override
	public boolean checkElement(ElementBase anElement) {
		boolean result = true;
		if (anElement instanceof TaskElement) {
			TaskElement task = (TaskElement) anElement;
			if (project != null) {
				ProjectElement taskProject = task.getProject();
				if (taskProject != null) {
					if (taskProject.equals(project) == false) {
						result = false;
					}
				}
			}
			if (status != null) {
				if (task.getStatus().equals(status) == false) {
					result = false;
				}
			}
		}
		return result;
	}

}
