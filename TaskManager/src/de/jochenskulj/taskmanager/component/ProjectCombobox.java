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

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ProjectElement;

/**
 * Combobox to select a project
 */
public class ProjectCombobox extends JComboBox implements ApplicationModelListener {

	public ApplicationModel applicationModel;
	public DefaultComboBoxModel comboboxModel;
	public ArrayList<ProjectElement> elements = new ArrayList<ProjectElement>();
	
	/**
	 * creates a new instance
	 * @param anApplicationModel
	 *        model to retrieve the projects
	 */
	public ProjectCombobox(ApplicationModel anApplicationModel) {
		applicationModel = anApplicationModel;
		comboboxModel = new DefaultComboBoxModel();
		ElementListBase list = applicationModel.getProjectList();
		for (ElementBase project : applicationModel.getProjectList().getElements()) {
			comboboxModel.addElement(project);
		}
		setModel(comboboxModel);
		applicationModel.addListener(this);
	}

    /**
     * update object after changes of the model
     */
    public void update() {
    	ElementBase selectedElement = (ElementBase) getSelectedItem();
    	int selectedIndex = -1;
    	int i = 0;
    	comboboxModel = new DefaultComboBoxModel();
		ElementListBase list = applicationModel.getProjectList();
		for (ElementBase project : applicationModel.getProjectList().getElements()) {
			if (selectedElement != null) {
				if (selectedElement == project) {
					selectedIndex = i;
				}
			}
			comboboxModel.addElement(project);
			i++;
		}
		setModel(comboboxModel);
		setSelectedIndex(selectedIndex);
    }

}
