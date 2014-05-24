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
package de.jochenskulj.taskmanager.io;

/**
 * Constants for XML-Tags 
 */
public class TagNames {
	
	// General tags
	public static final String MODEL = "folivora";
	
	// Tags for Task
	public static final String TASK_LIST = "task-list";
	public static final String TASK = "task";
	public static final String TASK_ID = "task-id";
	public static final String TASK_NAME = "task-name";
	public static final String TASK_PROJECT_ID = "task-project-id";
	public static final String TASK_DUE_DATE = "task-due-date";
	public static final String TASK_STATUS = "task-status";
	public static final String TASK_NOTES = "task-notes";
	public static final String TASK_PEOPLE_REFS = "task-people-refs";
	public static final String TASK_PEOPLE_ID = "task-people-id";
	public static final String TASK_CONTEXT_REFS = "task-context-refs";
	public static final String TASK_CONTEXT_ID = "task-context-id";
	
	// Tags for Projects
	public static final String PROJECT_LIST = "project-list";
	public static final String PROJECT = "project";
	public static final String PROJECT_ID = "project-id";
	public static final String PROJECT_NAME = "project-name";
	public static final String PROJECT_PRIORITY = "project-priority";
	
	// Tags for People
	public static final String PEOPLE_LIST = "people-list";
	public static final String PEOPLE = "people";
	public static final String PEOPLE_ID = "people-id";
	public static final String PEOPLE_FIRSTNAME = "people-firstname";
	public static final String PEOPLE_LASTNAME = "people-lastname";
	public static final String PEOPLE_INITIALS = "people-initials";
	
	// Tags for Contexts
	public static final String CONTEXT_LIST = "context-list";
	public static final String CONTEXT = "context";
	public static final String CONTEXT_ID = "context-id";
	public static final String CONTEXT_NAME = "context-name";

}
