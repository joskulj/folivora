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

import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Panel to display People in a list
 */
public class PeopleListPanel extends ListPanelBase {
    
    /**
     * creates an instance
     * @param aFrame
     *        Application frame that the panel is part of
     */
    public PeopleListPanel(ApplicationFrame aFrame) {
    	super(aFrame, aFrame.getApplication().getModel().getPeopleList());
    }
    
    /**
     * update object after changes of the model
     */
    public void update() {
        // TODO: implement method   
    }
}
