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

import de.jochenskulj.taskmanager.component.DetailTextField;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.PeopleElement;

/**
 * Detail Panel for People
 */
public class PeopleDetailPanel extends DetailPanelBase {

	private static Logger logger = Logger.getRootLogger();

	private DetailTextField firstname;
	private DetailTextField lastname;
	private DetailTextField initials;
	private JTextArea notes;
	
    /**
     * creates an instance
     * @param aFrame
     *        application frame that the panel belongs to
     */
    public PeopleDetailPanel(ApplicationFrame aFrame) {
        super(aFrame, aFrame.getApplication().getModel().getPeopleList());
        setVerticalFill(true);
    }

    /**
     * returns first component to focus
     * @return first component to focus
     */
    @Override
    public Component getFirstComponent() {
    	return firstname;
    }

    /**
     * initializes the components of the panel
     */
    @Override
    public void initPanel() {
    	firstname = new DetailTextField();
    	// TODO: make field mandatory
    	lastname = new DetailTextField();
    	initials = new DetailTextField();
    	notes = new JTextArea();
    	addComponent("Firstname:", firstname);
    	addComponent("Lastname:", lastname);
    	addComponent("Initials:", initials);
        addComponent(new JLabel("Notes:"), false);
        JScrollPane scrollPane = new JScrollPane(notes);
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
    	
    	PeopleElement element = (PeopleElement) anElement;
    	if (element != null) {
    		firstname.setText(element.getFirstname());
    		lastname.setText(element.getLastname());
    		initials.setText(element.getInitials());
    		notes.setText(element.getNotes());
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
    	
    	((PeopleElement) anElement).setFirstname(firstname.getText());
    	((PeopleElement) anElement).setLastname(lastname.getText());
    	if (initials.getText().length() > 0) {
    		((PeopleElement) anElement).setInitials(initials.getText());
    	}
    	((PeopleElement) anElement).setNotes(notes.getText());
    	
    	logger.debug("Method exited");
    	
        return anElement;
    }
}
