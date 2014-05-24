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

import de.jochenskulj.taskmanager.component.PriorityCombobox;
import de.jochenskulj.taskmanager.component.TaskReferencePanel;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ProjectElement;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

/**
 * Detail Panel for Projects
 */
public class ProjectDetailPanel extends DetailPanelBase {

	private static Logger logger = Logger.getRootLogger();
	
    private JTextField name;
    private PriorityCombobox priority;
    private TaskReferencePanel taskReferences;

    /**
     * creates an instance
     * @param aFrame
     *        application frame that the panel belongs to
     */
    public ProjectDetailPanel(ApplicationFrame aFrame) {
        super(aFrame, aFrame.getApplication().getModel().getProjectList());
        setVerticalFill(true);
    }

    /**
     * returns first component to focus
     * @return first component to focus
     */
    @Override
    public Component getFirstComponent() {
    	return name;
    }
    
    /**
     * initializes the components of the panel
     */
    @Override
    public void initPanel() {
        name = new JTextField();
        priority = new PriorityCombobox();
        taskReferences = new TaskReferencePanel();
        addComponent("Project Name:", name);
        addComponent("Priority", priority);
        addComponent(new JLabel("Tasks:"), false);
        addComponent(taskReferences, true);
    }

    /**
     * loads the values from an element
     * @param anElement
     *        element the load the values from
     */
    @Override
    public void loadValues(ElementBase anElement) {
    	
    	logger.debug("Method entered");
    	
        if (anElement != null) {
            ProjectElement project = (ProjectElement) anElement;
            name.setText(project.getName());
            priority.setValue(project.getPriority());
            taskReferences.setTaskReferences(project);
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
    	
        ProjectElement project = null;
        if (anElement != null) {
            project = (ProjectElement) anElement;
            project.setName(name.getText());
            project.setPriority(priority.getValue());
        }
        
        logger.debug("Method exited");
        
        return project;
    }
    
    /**
     * refreshes the current value 
     * Method must implement a subset of loadValues()
     */
    @Override
    public void refreshCurrentElement() {
    	
    	logger.debug("Method entered");
    	
    	ProjectElement project = (ProjectElement) getCurrentElement();
    	if (project != null) {
    		taskReferences.setTaskReferences(project);
    	}
    	
    	logger.debug("Method exited");
    }
}
