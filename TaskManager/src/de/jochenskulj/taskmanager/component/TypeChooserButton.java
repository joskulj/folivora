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
package de.jochenskulj.taskmanager.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.util.IconFactory;

/**
 * Toggle button the select a type
 */
public class TypeChooserButton extends JToggleButton implements ActionListener {

	private Application application;
	private ElementType type;
	private String icon;
	
	/**
	 * creates an instanca
	 * @param anApplication
	 *        application to operate on
	 * @param aType
	 *        type to select
	 * @param anIcon
	 *        icon to display
	 */
	public TypeChooserButton(Application anApplication, ElementType aType,
			String anIcon) {
		application = anApplication;
		type = aType;
		icon = anIcon;
		setIcon(IconFactory.createIcon(anIcon));
		addActionListener(this);
	}
	
	/**
	 * returns the element type that will be selected
	 * @return element type to select
	 */
	public ElementType getType() {
		return type;
	}
	
	/**
	 * sets the keybinding
	 * @param aKey
	 *        key to bind (in comination with ALT)
	 */
	public void setKeyBinding(int aKey) {
    	KeyStroke keyStroke = KeyStroke.getKeyStroke(aKey,	KeyEvent.ALT_MASK);
        registerKeyboardAction(this, keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			application.getModel().setCurrentElementType(type);
		} catch (ApplicationException ex) {
			application.getFrame().showException(ex);
		}
	}

}
