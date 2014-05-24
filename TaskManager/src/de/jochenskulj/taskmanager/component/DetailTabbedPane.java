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

import javax.swing.JTabbedPane;

import de.jochenskulj.taskmanager.view.DetailPanelBase;

/**
 * Class for Tabbed Panes within DetailPanel
 */
public class DetailTabbedPane extends JTabbedPane implements DetailPanelElement {

	private DetailPanelBase parentPanel;
	private ArrayList<DetailChildPanel> childPanels = new ArrayList<DetailChildPanel>();
	private boolean validated = false;
	private boolean valid = false;

	/**
	 * creates instance
	 * 
	 * @param aPanel
	 *            parent panel
	 */
	public DetailTabbedPane(DetailPanelBase aPanel) {
		parentPanel = aPanel;
	}

	/**
	 * adds a child panel as a tab
	 * 
	 * @param aTitle
	 *            title for the child panel
	 * @param aChildPanel
	 *            child panel to panel
	 */
	public void addChildPanel(String aTitle, DetailChildPanel aChildPanel) {
		childPanels.add(aChildPanel);
		addTab(aTitle, aChildPanel);
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
		for (DetailChildPanel panel : childPanels) {
			panel.setDetailPanel(aPanel);
		}
		
	}

	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#updateValidationStatus()
	 */
	@Override
	public void updateValidationStatus() {
		if (childPanels.size() > 0) {
			setSelectedIndex(0);
			DetailChildPanel childPanel = childPanels.get(0);
			childPanel.updateValidationStatus();
		}				
	}

	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#setFocus()
	 */
	@Override
	public void setFocus() {
		if (childPanels.size() > 0) {
			setSelectedIndex(0);
			DetailChildPanel childPanel = childPanels.get(0);
			childPanel.setFocus();
		}		
	}

	/* (non-Javadoc)
	 * @see de.jochenskulj.taskmanager.component.DetailPanelElement#validateElement()
	 */
	@Override
	public boolean validateElement() {
		boolean result = true;
		for (DetailChildPanel childPanel : childPanels) {
			if (childPanel.validateElement() == false) {
				result = false;
			}
		}
		validated = true;
		return result;
	}
}
