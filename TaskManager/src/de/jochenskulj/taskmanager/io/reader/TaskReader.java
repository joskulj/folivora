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
package de.jochenskulj.taskmanager.io.reader;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ContextElement;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.PeopleElement;
import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElement;
import de.jochenskulj.taskmanager.model.TaskStatus;

/**
 * Class to read a Task element 
 */
public class TaskReader {
	
	private static Logger logger = Logger.getRootLogger();
	
	private ApplicationModel model;
	
	/**
	 * creates instance
	 * @param aModel
	 *        model to add elements
	 */
	public TaskReader(ApplicationModel aModel) {
		model = aModel;
	}

	/**
	 * reads a Task element from a XML-Structure
	 * @param aNodelist
	 *        XML-Structure to read
	 */
	public void createElement(NodeList aNodeList) {
		
		logger.debug("Method entered");
		
		long taskId = -1;
		long projectId = -1;
		long dueDateTimestamp = -1;
		String taskName = null;
		String taskStatus = null;
		ArrayList<Long> peopleRefs = null;
		ArrayList<Long> contextRefs = null;
		String taskNotes = null;
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				
				logger.debug("element.getTagName" + element.getTagName());
				
				if (element.getTagName().equals(TagNames.TASK_ID)) {
					String text = element.getTextContent();
					taskId = Long.valueOf(text);
				}
				if (element.getTagName().equals(TagNames.TASK_NAME)) {
					taskName = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.TASK_PROJECT_ID)) {
				String text = element.getTextContent();
					projectId = Long.valueOf(text);					
				}
				if (element.getTagName().equals(TagNames.TASK_DUE_DATE)) {
					String text = element.getTextContent();
					dueDateTimestamp = Long.valueOf(text);
				}
				if (element.getTagName().equals(TagNames.TASK_STATUS)) {
					taskStatus = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.TASK_NOTES)) {
					taskNotes = element.getTextContent();
				}
				if (element.getTagName().equals(TagNames.TASK_CONTEXT_REFS)) {
					contextRefs = readIds(element.getChildNodes(), 
							TagNames.TASK_CONTEXT_ID);
				}
				if (element.getTagName().equals(TagNames.TASK_PEOPLE_REFS)) {
					peopleRefs = readIds(element.getChildNodes(), 
							TagNames.TASK_PEOPLE_ID);
				}
			}
		}
		if (taskId != -1 && taskName != null) {
			TaskElement taskElement = new TaskElement(model);
			taskElement.setName(taskName);
			if (projectId != -1) {
				ElementBase projectElement = ElementBase.get(projectId);
				if (projectElement instanceof ProjectElement) {
					taskElement.setProject((ProjectElement) projectElement);
				}
			}
			if (dueDateTimestamp != -1) {
				Date dueDate = new Date();
				dueDate.setTime(dueDateTimestamp);
				taskElement.setDueDate(dueDate);
			}
			if (taskStatus != null) {
				taskElement.setStatus(new TaskStatus(taskStatus));
			}
			if (taskNotes != null) {
				taskElement.setNotes(taskNotes);
			}
			for (Long id : contextRefs) {
				ElementBase refElement = ElementBase.get(id);
				if (refElement instanceof ContextElement) {
					taskElement.getContextSelection().addSelection(refElement);
				}
			}
			for (Long id : peopleRefs) {
				ElementBase refElement = ElementBase.get(id);
				if (refElement instanceof PeopleElement) {
					taskElement.getPeopleSelection().addSelection(refElement);
				}
			}
			taskElement.register(taskId);
			model.getTaskList().addElement(taskElement);
		}
		
		logger.debug("Method exited");
		
	}
	
	/**
	 * reads Ids from a XML-Structure
	 * @param aNodeList
	 *        XML-Stucture to read
	 * @param aTagName
	 *        Tag Names that identify the Ids
	 * @return ArrayList if Ids
	 */
	public ArrayList<Long> readIds(NodeList aNodeList, String aTagName) {
		ArrayList<Long> list = new ArrayList<Long>();
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(aTagName)) {
					String text = element.getTextContent();
					Long id = Long.valueOf(text);
					list.add(id);
				}
			}
		}
		return list;
	}
}