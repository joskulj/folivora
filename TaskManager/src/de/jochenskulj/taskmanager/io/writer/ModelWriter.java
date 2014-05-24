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
package de.jochenskulj.taskmanager.io.writer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ContextElement;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.PeopleElement;
import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElement;

/**
 * Class to write the model data to XML 
 */
public class ModelWriter {

	private static Logger logger = Logger.getRootLogger();
			
	private ApplicationModel model;
	private Document currentDocument;
	private Element rootElement;

	/**
	 * creates an instance
	 * @param aModel
	 *        model to save
	 */
	public ModelWriter(ApplicationModel aModel) {
		model = aModel;
	}

	/**
	 * saves the model data to XML
	 * @param aFilename
	 *        filename to save
	 * @throws ApplicationException
	 *         is thrown if the file cannot be saved
	 */
	public void save(String aFilename) throws ApplicationException {	
		
		logger.debug("Method entered");
		logger.debug("aFilename: " + aFilename);
		
		try {
			
			// initializes XML-Factories
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// create XML-Document and the root elements
			currentDocument = docBuilder.newDocument();
			rootElement = currentDocument.createElement(TagNames.MODEL);
			currentDocument.appendChild(rootElement);
			
			// create XML-Nodes for model data
			createTaskNodes();
			createProjectNodes();
			createPeopleNodes();
			createContextNodes();
			
			// write XML-File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(currentDocument);
			StreamResult result = new StreamResult(new File(aFilename));
	 		transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			throw new ApplicationException(e);
		} catch (TransformerException e) {
			throw new ApplicationException(
					ApplicationException.FILE_WRITE_ERROR,
					aFilename);
		}
		
		logger.debug("Method exited");
	}
	
	/**
	 * creates XML-Nodes for Task-Elements
	 */
	private void createTaskNodes() {
		
		logger.debug("Method entered");
		
		TaskNodeFactory factory = new TaskNodeFactory(currentDocument);
		Element taskListElement = currentDocument.createElement(
				TagNames.TASK_LIST);
		ElementListBase list = model.getTaskList();
		for (ElementBase element : list.getElements()) {
			Element taskElement = factory.createXMLElement(
					(TaskElement) element);
			taskListElement.appendChild(taskElement);
		}
		rootElement.appendChild(taskListElement);
		
		logger.debug("Method exited");
	}

	/**
	 * creates XML-Nodes for Project-Elements
	 */
	private void createProjectNodes() {
		
		logger.debug("Method entered");
		
		ProjectNodeFactory factory = new ProjectNodeFactory(currentDocument);
		Element projectListElement = currentDocument.createElement(
				TagNames.PROJECT_LIST);
		ElementListBase list = model.getProjectList();
		for (ElementBase element : list.getElements()) {
			Element projectElement = factory.createXMLElement(
					(ProjectElement) element);
			projectListElement.appendChild(projectElement);
		}
		rootElement.appendChild(projectListElement);
		
		logger.debug("Method exited");
	}
	
	/**
	 * creates XML-Nodes for People-Elements
	 */
	private void createPeopleNodes() {
		
		logger.debug("Method entered");
		
		PeopleNodeFactory factory = new PeopleNodeFactory(currentDocument);
		Element peopleListElement = currentDocument.createElement(
				TagNames.PEOPLE_LIST);
		ElementListBase list = model.getPeopleList();
		for (ElementBase element : list.getElements()) {
			Element peopleElement = factory.createXMLElement(
					(PeopleElement) element);
			peopleListElement.appendChild(peopleElement);
		}
		rootElement.appendChild(peopleListElement);
		
		logger.debug("Method exited");
	}
	
	/**
	 * creates XML-Nodes for Context-Elements
	 */
	private void createContextNodes() {
		
		logger.debug("Method entered");
		
		ContextNodeFactory factory = new ContextNodeFactory(currentDocument);
		Element contextListElement = currentDocument.createElement(
				TagNames.CONTEXT_LIST);
		ElementListBase list = model.getContextList();
		for (ElementBase element : list.getElements()) {
			Element contextElement = factory.createXMLElement(
					(ContextElement) element);
			 contextListElement.appendChild(contextElement);
		}
		rootElement.appendChild(contextListElement);
		
		logger.debug("Method entered");
	}
}
