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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.util.IconFactory;

/**
 * Panel to select a type 
 */
public class TypeChooserPanel extends JPanel implements ApplicationModelListener {

	private Application application;
	private int buttonIndex = 0;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private HashMap<ElementType, TypeChooserButton>  buttons =
			new HashMap<ElementType, TypeChooserButton>();
	/**
	 * creates an instance
	 * @param anApplication
	 *        application instance
	 */
	public TypeChooserPanel(Application anApplication) {
		application = anApplication;
		setLayout(new GridBagLayout());
		initPanel();
		application.getModel().addListener(this);
	}
	
	/**
	 * adds a chooser button
	 * @param aChooserButten
	 *        chooser button to add
	 */
	public void addChooserButton(TypeChooserButton aChooserButton) {
		buttonGroup.add(aChooserButton);
		buttons.put(aChooserButton.getType(), aChooserButton);

		GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = buttonIndex++;
        c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0;
		c.weightx = 10;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5, 5, 5, 5);

        add(aChooserButton, c);
	}


	@Override
	public void update() {
		ApplicationModel model = application.getModel();
		ElementType type = model.getCurrentElementType();
		TypeChooserButton button = buttons.get(type);
		if (button != null) {
			button.setSelected(true);
		}
	}
	
	/**
	 * initializes the panel
	 */
	private void initPanel() {
		ApplicationModel model = application.getModel();
		ElementType type = model.getTaskList().getType();
		String icon = IconFactory.TASK;
		TypeChooserButton button = new TypeChooserButton(application, type, icon);
		button.setKeyBinding(KeyEvent.VK_T);
		button.setToolTipText("Tasks (Alt-T)");
		addChooserButton(button);
		
		type = model.getProjectList().getType();
		icon = IconFactory.PROJECT;
		button = new TypeChooserButton(application, type, icon);
		button.setKeyBinding(KeyEvent.VK_P);
		button.setToolTipText("Projects (Alt-P)");
		addChooserButton(button);
		
		type = model.getPeopleList().getType();
		icon = IconFactory.PEOPLE;
		button = new TypeChooserButton(application, type, icon);
		button.setKeyBinding(KeyEvent.VK_E);
		button.setToolTipText("People (Alt-E)");
		addChooserButton(button);
		
		type = model.getContextList().getType();
		icon = IconFactory.CONTEXT;
		button = new TypeChooserButton(application, type, icon);
		button.setKeyBinding(KeyEvent.VK_N);
		button.setToolTipText("Contexts (Alt-N)");
		addChooserButton(button);
	}
}
