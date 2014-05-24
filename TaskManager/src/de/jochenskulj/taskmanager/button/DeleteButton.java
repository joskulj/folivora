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
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.util.IconFactory;
import de.jochenskulj.taskmanager.util.YesNoOption;
import de.jochenskulj.taskmanager.view.ApplicationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.JButton;

/**
 * Button to delete the current object
 */
public class DeleteButton extends JButton implements ActionListener, ApplicationModelListener {
    
    private ApplicationFrame frame;
    
    /**
     * creates an instance
     * @param aFrame
     *        Application frame that the button is part of
     */
    public DeleteButton(ApplicationFrame aFrame) {
        super();
        frame = aFrame;
        if (frame != null) {
        	frame.getApplication().getModel().addListener(this);
        }
        setIcon(IconFactory.createIcon(IconFactory.DELETE));
        setToolTipText("Delete current entry");
        addActionListener(this);
        setState();
    }
    
    /**
     * handles an event
     * @param arg0
     *        event to handle
     */
    public void actionPerformed(ActionEvent arg0) {
    	if (isConfirmed()) {
    		try {
    			frame.getApplication().deleteCurrentElement();
    		} catch (ApplicationException e) {
    			frame.showException(e);
    		}
    	}
    }
    
    /**
     * update object after changes of the model
     */
    public void update() {
    	setState();
    }

    /**
     * enables and disables the button depending on the model
     */
    private void setState() {
    	boolean enabled = false;
    	ElementListBase currentList = frame.getApplication().getModel().getCurrentList();
    	if (currentList != null) {
    		if (currentList.getRowCount() > 0) {
    			enabled = true;
    		}
    	}
    	setEnabled(enabled);
    }
    
    private boolean isConfirmed() {
    	boolean result = false;
    	ElementListBase currentList = frame.getApplication().getModel().getCurrentList();
    	if (currentList != null) {
    		int index = currentList.getSelectedRow();
    		if (index > -1 && index < currentList.getRowCount()) {
    			ElementBase currentElement = currentList.getElement(index);
    			YesNoOption option = new YesNoOption(frame.getFrame());
    			option.setTitle("Delete element");
    			MessageFormat mf = new MessageFormat("Do you want to delete {0}?");
    			Object[] args = { currentElement.getSelectionLabel() };
    			option.setQuestion(mf.format(args));
    	    	result = (option.show() == YesNoOption.YES_OPTION);
    		}
    	}
    	return result;
    }
}
