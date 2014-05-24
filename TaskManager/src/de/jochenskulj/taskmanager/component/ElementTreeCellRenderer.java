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

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import de.jochenskulj.taskmanager.util.IconFactory;

/**
 * Renderer for the Element tree
 */
public class ElementTreeCellRenderer implements TreeCellRenderer {
    
    /**
     * creates an instance
     */
    public ElementTreeCellRenderer() {
        
    }
    
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        String s = o.toString();
        ImageIcon icon = IconFactory.createIcon(s);
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setText(s);
        panel.add(label);
	    
	        String lookAndFeel = UIManager.getLookAndFeel().toString();
	        if (lookAndFeel.contains("MetalLookAndFeel")) {
	        	if (hasFocus) {
	            panel.setBackground(Color.GRAY);
		    } else {
		        panel.setBackground(Color.WHITE);
	        }
	    }
        return panel;
    }
}