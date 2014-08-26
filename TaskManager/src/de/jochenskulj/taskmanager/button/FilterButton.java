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
package de.jochenskulj.taskmanager.button;

import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.filter.TaskFilterDialog;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.util.IconFactory;
import de.jochenskulj.taskmanager.view.ApplicationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

/**
 * Button to open or close the filter
 */
public class FilterButton extends JButton implements ActionListener {
    
	private static Logger logger = Logger.getRootLogger();
	
    private ApplicationFrame frame;
    
    /**
     * creates an instance
     * @param aFrame
     *        Application frame that the button is part of
     */
    public FilterButton(ApplicationFrame aFrame) {
        super();
        frame = aFrame;
        setIcon(IconFactory.createIcon(IconFactory.FILTER));
        addActionListener(this);
    }
    
    /**
     * handles an event
     * @param arg0
     *        event to handle
     */
    public void actionPerformed(ActionEvent arg0) {
    	logger.debug("FilterButton.actionPerformed() entered.");

    	ElementListBase currentList = 
    				frame.getApplication().getModel().getCurrentList();
    	JDialog filterDialog = null;
    	if (currentList.getType().equals(ElementType.TASK)) {
    		filterDialog = new TaskFilterDialog(frame.getFrame(), currentList);
    	}
    	if (filterDialog != null) {
    		filterDialog.setVisible(true);
    	}
    
    	logger.debug("Filter.actionPerformed() exited.");    }
}
