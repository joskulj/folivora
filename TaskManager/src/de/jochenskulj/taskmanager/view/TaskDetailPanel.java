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
package de.jochenskulj.taskmanager.view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.component.DetailChildPanel;
import de.jochenskulj.taskmanager.component.DetailTextField;
import de.jochenskulj.taskmanager.component.DetailDateField;
import de.jochenskulj.taskmanager.component.ElementSelectionTable;
import de.jochenskulj.taskmanager.component.ProjectCombobox;
import de.jochenskulj.taskmanager.component.TaskStatusComboBox;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.model.ProjectElement;
import de.jochenskulj.taskmanager.model.TaskElement;

/**
 * Detail Panel for Tasks
 */
public class TaskDetailPanel extends DetailPanelBase {

	private static Logger logger = Logger.getRootLogger();
	    
	private ApplicationModel model;
	private ProjectCombobox project;
	private DetailTextField task;
	private DetailDateField dueDate;
	private TaskStatusComboBox status;
	private JTextArea notes;
	private ElementSelectionTable contextSelection;
	private ElementSelectionTable peopleSelection;
	private ElementType type = new ElementType(ElementType.TASK);
	
    /**
     * creates an instance
     * @param aFrame
     *        application frame that the panel belongs to
     */
    public TaskDetailPanel(ApplicationFrame aFrame) {
        super(aFrame, aFrame.getApplication().getModel().getTaskList());
        model = aFrame.getApplication().getModel(); 
        model.getContextList().addListener(this);
        model.getPeopleList().addListener(this);
        setVerticalFill(true);
    }

    /**
     * returns first component to focus
     * @return first component to focus
     */
    @Override
    public Component getFirstComponent() {
    	return task;
    }

    /**
     * initializes the components of the panel
     */
    @Override
    public void initPanel() {
    	project = new ProjectCombobox(getFrame().getApplication().getModel());
    	task = new DetailTextField();
    	dueDate = new DetailDateField();
    	status = new TaskStatusComboBox();
    	addComponent("Task Name:", task);
    	addComponent("Project:", project);
    	addComponent("Due Date:", dueDate);
    	addComponent("Status:", status);
    	notes = new JTextArea();
    	JScrollPane scrollPane = new JScrollPane(notes);
    	addComponent(new JLabel("Notes:"), false);
    	addComponent(scrollPane, true);
    	peopleSelection = new ElementSelectionTable();
    	scrollPane = new JScrollPane(peopleSelection);
    	addComponent(new JLabel("People:"), false);
     	addComponent(scrollPane, true);
     	addComponent(new JLabel("Context:"), false);
    	contextSelection = new ElementSelectionTable();
    	scrollPane = new JScrollPane(contextSelection);
    	addComponent(scrollPane, true);    	
    }

    /**
     * loads the values from an element
     * @param anElement
     *        element the load the values from
     */
    @Override
    public void loadValues(ElementBase anElement) {
    	
    	logger.debug("Method entered");
    	
    	TaskElement taskElement = (TaskElement) anElement;
    	if (taskElement != null) {
    		// TODO: add project
    		task.setText(taskElement.getName());
    		dueDate.setDate(taskElement.getDueDate());
    		project.setSelectedItem(taskElement.getProject());
    		status.setValue(taskElement.getStatus());
    		contextSelection.setSelection(taskElement.getContextSelection());
    		peopleSelection.setSelection(taskElement.getPeopleSelection());
    		notes.setText(taskElement.getNotes());
    	}
    	
    	logger.debug("Method exited");
    }

    /**
     * saves the values to an element
     * @param anElement
     *        element to save the values from
     * @return element with the saved values
     */
    @Override
    public ElementBase saveValues(ElementBase anElement) {
    	
    	logger.debug("Method entered");
    	
    	TaskElement result = null;
    	if (anElement != null) {
    		result = (TaskElement) anElement;
    		result.setName(task.getText());
    		result.setProject((ProjectElement) project.getSelectedItem());
    		result.setDueDate(dueDate.getDate());
    		result.setStatus(status.getValue());
    		result.setContextSelection(contextSelection.getSelection());
    		result.setPeopleSelection(peopleSelection.getSelection());
    		result.setNotes(notes.getText());
    	}
    	
    	logger.debug("Method exited");
    	
        return result;
    }
    
	/**
	 * returns the element type of the panel
	 * @return element type of the panel
	 */
    @Override
	public ElementType getType() {
		return type;
	}
    
    private DetailChildPanel initNotesPanel() {
    	DetailChildPanel result = new DetailChildPanel(this);
    	result.addComponent(new JLabel("Notes:"), false);
    	return result;
    }

    private DetailChildPanel initContextPanel() {
    	DetailChildPanel result = new DetailChildPanel(this);
    	return result;
    }

    private DetailChildPanel initPeoplePanel() {
    	DetailChildPanel result = new DetailChildPanel(this);
    	return result;
    }
}
