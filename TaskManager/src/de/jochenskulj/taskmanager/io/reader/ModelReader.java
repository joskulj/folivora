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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.io.TagNames;
import de.jochenskulj.taskmanager.model.ApplicationModel;

/**
 * Class to read model data from a XML-File 
 */
public class ModelReader {

	private static Logger logger = Logger.getRootLogger();
		
	private ApplicationModel model;

	/**
	 * creates a model
	 * @param aModel
	 *        model to read
	 */
	public ModelReader(ApplicationModel aModel) {
		model = aModel;
	}

	/**
	 * reads the model data from a XML-File
	 * @param aFilename
	 *        name of the file to read
	 * @throws ApplicationException
	 *         is thrown of the file can't be read
	 */
	public void read(String aFilename) throws ApplicationException {
		
		logger.debug("Method entered");
		logger.debug("aFilename: " + aFilename);
		
		Document doc = readDocument(aFilename);
		NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
		
		model.setActive(false);
		model.reset();
		
		// read Contexts, People, Projects
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				
				logger.debug("element.TagName()" + element.getTagName());
				
				if (element.getTagName().equals(TagNames.CONTEXT_LIST)) {
					readContextElements(element.getChildNodes());
				}
				if (element.getTagName().equals(TagNames.PEOPLE_LIST)) {
					readPeopleElements(element.getChildNodes());
				}
				if (element.getTagName().equals(TagNames.PROJECT_LIST)) {
					readProjectElements(element.getChildNodes());
				}

			}
		}

		// read Tasks
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.TASK_LIST)) {
					readTaskElements(element.getChildNodes());
				}
			}
		}

		model.resetSelection();
		model.setActive(true);
		
		logger.debug("Method exited");
	}
	
	/**
	 * reads an XML-Document
	 * @param aFilename
	 *        name of the file to read
	 * @return read XML-File
	 * @throws ApplicationException
	 * 		   is thrown of the file can't be read
	 */
	private Document readDocument(String aFilename) throws ApplicationException  {
		File file = new File(aFilename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ApplicationException(e);
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(file);
		} catch (SAXException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(
					ApplicationException.FILE_READ_ERROR, aFilename);
		}
		return doc;
	}
	
	/**
	 * reads Context-Elements from a XML-Structure
	 * @param aNodeList
	 *        XML-Structure to read
	 */
	private void readContextElements(NodeList aNodeList) {
		ContextReader reader = new ContextReader(model);
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.CONTEXT)) {
					reader.createElement(element.getChildNodes());
				}
			}
		}
	}

	/**
	 * reads People-Elements from a XML-Structure
	 * @param aNodeList
	 *        XML-Structure to read
	 */
	private void readPeopleElements(NodeList aNodeList) {
		PeopleReader reader = new PeopleReader(model);
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.PEOPLE)) {
					reader.createElement(element.getChildNodes());
				}
			}
		}
	}

	/**
	 * reads Project-Elements from a XML-Structure
	 * @param aNodeList
	 *        XML-Structure to read
	 */
	private void readProjectElements(NodeList aNodeList) {
		ProjectReader reader = new ProjectReader(model);
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.PROJECT)) {
					reader.createElement(element.getChildNodes());
				}
			}
		}
	}
	
	/**
	 * reads Task-Elements from a XML-Structure
	 * @param aNodeList
	 *        XML-Structure to read
	 */
	private void readTaskElements(NodeList aNodeList) {
		TaskReader reader = new TaskReader(model);
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node node = aNodeList.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equals(TagNames.TASK)) {
					reader.createElement(element.getChildNodes());
				}
			}
		}
	}
}
