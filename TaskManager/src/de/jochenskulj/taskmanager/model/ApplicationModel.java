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
import java.util.Hashtable;

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.io.reader.ModelReader;
import de.jochenskulj.taskmanager.io.writer.ModelWriter;

/**
 * Model class of the application
 */
public class ApplicationModel {

	private static Logger logger = Logger.getRootLogger();
	
    public static ElementType NO_SELECTION = new ElementType(ElementType.ELEMENTS);

    private ArrayList<ElementType> elementTypes;
    private ElementType currentElementType;
    private Hashtable<String, ElementListBase> listTable;
    private ArrayList<ApplicationModelListener> listeners;
    private ContextList contextList;
    private PeopleList peopleList;
    private ProjectList projectList;
    private TaskList taskList;
    private boolean active = true;
    private String filename = null;

    /**
     * creates instance
     */
    public ApplicationModel() {
        listeners = new ArrayList<ApplicationModelListener>();
        initElementTypes();
    }
    
    /**
     * adds a listener
     * @param aListener
     *        model listener to add
     */
    public void addListener(ApplicationModelListener aListener) {
        listeners.add(aListener);
    }
    
    /**
     * signals all model listeners to update
     */
    public void fireUpdate() {

    	logger.debug("Method entered");

    	if (active) {
	        for (ApplicationModelListener listener : listeners) {
	        	
	        	logger.debug("listener: " + listener.getClass().getName());
	        	
	            if (listener != null) {
	                listener.update();
	            }
	        }
    	}      

    	logger.debug("Method exited");

    }
    
    /**
     * set the flag to fire updates to listeners
     * @param aFlag
     *        flag to fire updates to listeners
     */
    public void setActive(boolean aFlag) {

    	logger.debug("Method entered");
    	logger.debug("aFlag: " + aFlag);

    	taskList.setActive(aFlag);
    	contextList.setActive(aFlag);
    	peopleList.setActive(aFlag);
    	projectList.setActive(aFlag);
    	if (aFlag == true) {
    		
    		logger.debug("aFlag == true");
    		
    		active = true;
    		fireUpdate();
    	} else {
    		
    		logger.debug("aFlag == false");
    		
    		active = false;
    	}
    	
    	logger.debug("active: " + active);
    	logger.debug("Method exited");
    }

    /**
     * checks, if the element has been modified
     * @return <code>true</code>, if the element is modified; otherwise
     *         <code>false</code>
     */
    public boolean isModified() {
    	boolean result = false;
    	if (taskList.isModified()) {
    		result = true;
    	}
    	if (projectList.isModified()) {
    		result = true;
    	}
    	if (peopleList.isModified()) {
    		result = true;
    	}
    	if (contextList.isModified()) {
    		result = true;
    	}
    	
    	logger.debug("result: " + result);

    	return result;
    }
    
    /**
     * clears the flag that marks the element as modified
     */
    public void clearModified() {

    	logger.debug("Method entered");

    	taskList.clearModified();
    	projectList.clearModified();
    	peopleList.clearModified();
    	contextList.clearModified();

    	logger.debug("Method exited");
    }

    /**
     * resets the selection
     */
    public void resetSelection() {
    	
    	logger.debug("Method entered");

    	taskList.resetSelection();
    	contextList.resetSelection();
    	peopleList.resetSelection();
    	projectList.resetSelection();

    	logger.debug("Method exited");
    }
    
    /**
     * returns the list of contexts
     * @return list of contexts
     */
    public ContextList getContextList() {
        return contextList;
    }
    
    /**
     * returns the currently selected element type
     * @return currently selected element type
     */
    public ElementType getCurrentElementType()  {
        return currentElementType;
    }
    
    /*
     * returns the list for the current element type
     * @return list for the current element type
     */
    public ElementListBase getCurrentList() {
        ElementListBase list = null;
        if (currentElementType != null) {
            String label = currentElementType.getLabel();
            if (listTable.keySet().contains(label)) {
                list = listTable.get(label);
            }
        }
        return list;
    }
    
    /**
     * returns a collection of the element types
     * @return collection of the element types
     */
    public Collection<ElementType> getElementTypes() {
        return elementTypes;
    }
    
    /**
     * returns list of people
     * @return list of people
     */
    public PeopleList getPeopleList() {
        return peopleList;
    }

    /**
     * returns list of projects
     * @return list of projects
     */
    public ProjectList getProjectList() {
        return projectList;
    }

    /**
     * returns list of tasks
     * @return list of tasks
     */
    public TaskList getTaskList() {
        return taskList;
    }
    
    /**
     * returns the filename of the model
     * @return filename of the model
     */
    public String getFilename() {
    	return filename;
    }
    
    /**
     * checks, if the model has a filename
     * @return <code>true</code>, if the model has a filename;
     *         <code>false</code> otherwise
     */
    public boolean hasFilename() {
    	return (filename != null);
    }
    
    /**
     * saves the model with a given filename
     * @param aFilename
     *        filename to use
     * @throws ApplicationException
     *         is thrown if the model cannot be saved
     */
    public void save(String aFilename) throws ApplicationException {
 
    	logger.debug("Method entered");
    	logger.debug("aFilename: " + aFilename);

    	ModelWriter writer = new ModelWriter(this);
    	writer.save(aFilename);
    	filename = aFilename;
    	clearModified();
    	fireUpdate();
    	
    	logger.debug("Method exited");
    	
    }
    
    /**
     * saves the model
     * @throws ApplicationException
     *         is thrown if the model cannot be saved
     */
    public void save() throws ApplicationException {
    	if (hasFilename()) {
    		save(filename);
    	} else {
    		throw new ApplicationException("Application model has no filename.");
    	}
    }

    /**
     * loads the model from an XML-File
     * @param aFilename
     *        name of the file to loaf
     * @throws ApplicationException
     *         is thrown if the model cannot be saved
     */
    public void load(String aFilename) throws ApplicationException {
    	logger.debug("Method entered");
    	logger.debug("aFilename: " + aFilename);
    	
    	ModelReader reader = new ModelReader(this);
    	reader.read(aFilename);
    	filename = aFilename;
    	clearModified();
    	fireUpdate();
    	
    	logger.debug("Method exited");
    }

    /**
     * initializes the element types
     */
    protected void initElementTypes() {
        elementTypes = new ArrayList<ElementType>();
        listTable = new Hashtable<String, ElementListBase>();
        taskList = new TaskList(this);
        ElementType type = taskList.getType();
        elementTypes.add(type);
        currentElementType = type;
        listTable.put(type.getLabel(), taskList);
        projectList = new ProjectList(this);
        type = projectList.getType();
        elementTypes.add(type);
        listTable.put(type.getLabel(), projectList);
        peopleList = new PeopleList(this);
        type = peopleList.getType();
        elementTypes.add(type);
        listTable.put(type.getLabel(), peopleList);
        contextList = new ContextList(this);
        type = contextList.getType();
        elementTypes.add(type);
        listTable.put(type.getLabel(), contextList);
    }
    
    /**
     * removes a listener
     * @param aListener
     *        model listener to remove
     */
    public void removeListener(ApplicationModelListener aListener) {
        listeners.remove(aListener);
    }
    
    /**
     * sets the currently selected element type
     * @param aType
     *        new currently selected element type
     * @throws ApplicationException
     *         is thrown if an unexpected exception occured
     */
    public void setCurrentElementType(ElementType aType) 
    		throws ApplicationException {
    	try {
    		currentElementType = aType;
    		fireUpdate();
    	} catch (Exception e) {
    		throw new ApplicationException(e);
    	}
    }
    
    /**
     * deletes the current element
     */
    public void deleteCurrentElement() {
    	ElementListBase currentList = getCurrentList();
    	if (currentList != null) {
    		currentList.removeSelectedElement();
    	}
    }
    
    /**
     * deletes all data
     */
    public void reset() {
    	contextList.removeAll();
    	peopleList.removeAll();
    	projectList.removeAll();
    	taskList.removeAll();
    	clearModified();
    }
}
