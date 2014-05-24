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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.jochenskulj.taskmanager.view.ContentPanelBase;

/**
 * Helper class for panels with a GridBagLayout
 */
public class GridBagPanel extends ContentPanelBase {

    private int componentRowCount = 0;

    /**
     * creates an instance
     */
	public GridBagPanel() {
		setVerticalFill(true);
		setLayout(new GridBagLayout());
	}

	/**
     * adds a component to the panel
     * @param aLabel
     *        label for the component
     * @param aComponent
     *        component to add
     */
    public void addComponent(String aLabel, Component aComponent) {
        GridBagConstraints c = createConstraints(0, componentRowCount);
        add(new JLabel(aLabel), c);
        c = createConstraints(1, componentRowCount++);
        add(aComponent, c);
    }
    
    /**
     * adds a panel filling horizontal or the remaining panel
     * @param aComponent
     *        component to add
     * @param verticalFlag
     *        if <code>true</code>, the component fills the remaining panel
     */
    public void addComponent(Component aComponent, boolean verticalFlag) {
    	// FIXME
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = componentRowCount++;
        c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		if (verticalFlag) {
			c.weighty = 50000;
		} else {
			c.weighty = 1;
		}
		c.weightx = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5, 5, 5, 5);
    	add(aComponent, c);
    }
    
    /**
     * adds a component filling the remaining panel
     * @param aComponent
     *        panel to add
     */
    public void addComponent(Component aComponent) {
    	addComponent(aComponent, true);
    }
    
    /**
     * refreshes the components within the frame
     */
    public void refresh() {
    	refreshPanel(this);
    }
    	
    /**
     * creates a default GridBagConstraint
     * @param x
     *        X-Position for a new element
     * @param y
     *        Y-Position for a new element
     * @return default GridBagConstraint
     */
    private GridBagConstraints createConstraints(int x, int y) {
        GridBagConstraints result = new GridBagConstraints();
        result.gridx = x;
        result.gridy = y;
        if (x == 1) {
            result.fill = GridBagConstraints.HORIZONTAL;
            result.weightx = 1;
        }
        result.insets = new Insets(5, 5, 5, 5);
        result.anchor = GridBagConstraints.LINE_START;
        result.weighty = 1;
        return result;
    }
    
    /**
     * refreshes the components within a panel
     * @param aPanel
     *        panel to refresh
     */
    private void refreshPanel(JPanel aPanel) {
    	for (Component panelComponent : getComponents()) {
    		panelComponent.repaint();
			if ((panelComponent instanceof JPanel) && !(panelComponent instanceof TaskReferencePanel)) {
				refreshPanel((JPanel) panelComponent);
			}
		}
    }
}
