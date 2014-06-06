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

import de.jochenskulj.taskmanager.component.DetailTextField;
import de.jochenskulj.taskmanager.component.TaskReferencePanel;
import de.jochenskulj.taskmanager.model.ContextElement;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementType;

import java.awt.Component;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

/**
 * Detail Panel for Context
 */
public class ContextDetailPanel extends DetailPanelBase {

	private static Logger logger = Logger.getRootLogger();
	
    private DetailTextField context;
    private TaskReferencePanel taskReferences;
    private ElementType type = new ElementType(ElementType.CONTEXT);

    /**
     * creates an instance
     * @param aFrame
     *        application frame that the panel belongs to
     */
    public ContextDetailPanel(ApplicationFrame aFrame) {
        super(aFrame, aFrame.getApplication().getModel().getContextList());
        setVerticalFill(true);
    }

    /**
     * returns first component to focus
     * @return first component to focus
     */
    @Override
    public Component getFirstComponent() {
    	return context;
    }
    
    /**
     * initializes the components of the panel
     */
    @Override
    public void initPanel() {
        context = new DetailTextField();
        context.setMandatory(false);
        context.setErrorMessage("Context is mandatory.");
        taskReferences = new TaskReferencePanel();
        addComponent("Context:", context);
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
    	
        ContextElement contextElement = (ContextElement) anElement;
        if (contextElement != null) {
            context.setText(contextElement.getContext());
            taskReferences.setTaskReferences(contextElement);
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
    	
        ((ContextElement) anElement).setContext(context.getText());
    	
    	logger.debug("Method exited");
    	
        return anElement;
    }
    /**
     * refreshes the current value 
     * Method must implement a subset of loadValues()
     */
    @Override
    public void refreshCurrentElement() {
    	    	
    	logger.debug("Method entered");
    	    	
    	ContextElement contextElement = (ContextElement) getCurrentElement();
    	if (contextElement != null) {
    		taskReferences.setTaskReferences(contextElement);
    	}
    	
    	logger.debug("Method exited");
    }
    
	/**
	 * returns the element type of the panel
	 * @return element type of the panel
	 */
    @Override
	public ElementType getType() {
		return type;
	}
}
