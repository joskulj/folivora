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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.jochenskulj.taskmanager.view.DetailPanelBase;

/**
 * Class for child panels within the DetailPanel
 * @author joskulj
 *
 */
public class DetailChildPanel extends GridBagPanel implements DetailPanelElement {

	private DetailPanelBase parentPanel;
	private ArrayList<DetailPanelElement> panelElements = new ArrayList<DetailPanelElement>(); 
    private boolean validated = false;
    private boolean valid = false;

	/**
	 * creates an instance
	 * @param aParentPanel
	 *        parent panel of the child panel
	 */
	public DetailChildPanel(DetailPanelBase aParentPanel) {
		parentPanel = aParentPanel;
		setLayout(new GridBagLayout());
	}
	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, int)
	 */
	@Override
	public Component add(Component comp, int index) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(parentPanel);
		}
		return super.add(comp, index);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
	 */
	@Override
	public void add(Component comp, Object constraints) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(parentPanel);
		}
		super.add(comp, constraints);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object, int)
	 */
	@Override
	public void add(Component comp, Object constraints, int index) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(parentPanel);
		}
		super.add(comp, constraints, index);
	}

	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#isValidated()
	 */
	@Override
	public boolean isValidated() {
		return validated;
	}
	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#setDetailPanel(de.jochenskulj.taskmanager.view.DetailPanelBase)
	 */
	@Override
	public void setDetailPanel(DetailPanelBase aPanel) {
		parentPanel = aPanel;
		for (DetailPanelElement panelElement : panelElements) {
			panelElement.setDetailPanel(aPanel);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#updateValidationStatus()
	 */
	@Override
	public void updateValidationStatus() {
		if (panelElements.size() > 0) {
			DetailPanelElement firstElement = panelElements.get(0);
			firstElement.updateValidationStatus();
		}				
	}

	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#setFocus()
	 */
	@Override
	public void setFocus() {
		if (panelElements.size() > 0) {
			DetailPanelElement firstElement = panelElements.get(0);
			firstElement.setFocus();
		}		
	}
	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#validateElement()
	 */
	@Override
	public boolean validateElement() {
		boolean result = true;
		for (DetailPanelElement panelElement : panelElements) {
			if (panelElement.validateElement() == false) {
				result = false;
			}
		}
		validated = true;
		return result;
	}
}
