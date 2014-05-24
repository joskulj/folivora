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
package de.jochenskulj.taskmanager.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * class for menu item data
 */
public class ApplicationMenuItem implements ActionListener {
    
    private ApplicationMenu menu;
    private String label;
    private int mnemonic;
    private String key;
    
    /**
     * creates an instance for a separator
     * @param aMenu
     *        menu that the item belongs to
     */
    public ApplicationMenuItem(ApplicationMenu aMenu) {
        super();
        
        menu = aMenu;
        label = null;
        mnemonic = -1;
        key = null;
        menu.addItem(this);
    }

    /**
     * creates an instance for a menu item
     * @param aMenu
     *        menu that the item belongs to
     * @param aLabel
     *        label of the menu item
     * @param aMnemonic
     *        mnemonic of the menu item
     * @param aKey
     *        key of the command to invoke
     */
    public ApplicationMenuItem(ApplicationMenu aMenu, String aLabel, int aMnemonic, String aKey) {
        menu = aMenu;
        label = aLabel;
        mnemonic = aMnemonic;
        key = aKey;
        menu.addItem(this);
    }
    
    /**
     * creates an instance for a menu item
     * @param aMenu
     *        menu that the item belongs to
     * @param aLabel
     *        label of the menu item
     * @param aKey
     *        key of the command to invoke
     */
    public ApplicationMenuItem(ApplicationMenu aMenu, String aLabel, String aKey) {
        menu = aMenu;
        label = aLabel;
        key = aKey;
        menu.addItem(this);
    }
            
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        menu.getMenuBar().executeCommand(key);
    }
    
    /**
     * creates a Swing menu item
     * @return menu item
     */
    public JMenuItem createMenuItem() {
        JMenuItem item = new JMenuItem(label);
        if (mnemonic != -1) {
            item.setMnemonic(mnemonic);
        }
        item.addActionListener(this);
        return item;
    }
    
    /**
     * returns the key of the command to invoke
     * @return key of the command to invoke
     */
    public String getKey() {
        return key;
    }

    /**
     * returns, if the item represents a separator
     * @return true, if the item represents a separator; false, if not
     */
    public boolean isSeparator() {
        return label == null;
    }
}
