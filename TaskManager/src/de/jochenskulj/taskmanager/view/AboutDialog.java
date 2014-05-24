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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.jochenskulj.taskmanager.component.ImagePanel;
import de.jochenskulj.taskmanager.util.IconFactory;

/**
 * About dialog 
 */
public class AboutDialog extends JDialog implements ActionListener {

	/**
	 * creates an instance
	 * @param aParent
	 *        Frame that owns the dialog
	 */
	public AboutDialog(JFrame aParent) {
		super(aParent);
	    if (aParent != null) {
	        Dimension parentSize = aParent.getSize(); 
	        Point p = aParent.getLocation(); 
	        setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }
	    init();
	    pack();
	}
	
	/**
	 * closes the dialog
	 */
	public void actionPerformed(ActionEvent e) {
	    setVisible(false); 
	    dispose(); 
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		ImageIcon icon = IconFactory.createIcon(IconFactory.APPLICATION);
		JPanel panel = new ImagePanel(icon.getImage());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		add(panel, c);
		
		JLabel label = new JLabel("Folivora");	
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		add(label, c);
		
		label = new JLabel("Version 1.0");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		add(label, c);

		label = new JLabel("Copyright 2013-2014 by Jochen Skulj");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		add(label, c);

		label = new JLabel("Published under GNU Public License");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		add(label, c);
		
		JButton button = new JButton("Close");
		button.addActionListener(this);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(5, 5, 5, 5);
		add(button, c);
	}
}
