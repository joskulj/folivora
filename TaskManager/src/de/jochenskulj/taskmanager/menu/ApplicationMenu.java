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

import de.jochenskulj.taskmanager.Application;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * class for menus
 */
public class ApplicationMenu {

    private ApplicationMenuBar menuBar;
    private String label;
    private int mnemonic;    
    private ArrayList<ApplicationMenuItem> items = new ArrayList<ApplicationMenuItem>();

    /**
     * creates an instance
     * @param aMenuBar
     *        menu bar that the item belongs to
     * @param aLabel
     *        label of the menu
     */
    public ApplicationMenu(ApplicationMenuBar aMenuBar, String aLabel) {
        menuBar = aMenuBar;
        label = aLabel;
        mnemonic = -1;
    }
    
    /**
     * creates an instance
     * @param aMenuBar
     *        menu bar that the menu belongs to
     * @param aLabel
     *        label of the menu
     * @param aMnemonic
     *        mnemonic of the menu
     */
    public ApplicationMenu(ApplicationMenuBar aMenuBar, String aLabel, int aMnemonic) {
        menuBar = aMenuBar;
        label = aLabel;
        mnemonic = aMnemonic;
    }
    
    /**
     * adds a menu item
     * @param anItem
     *        menu item to add
     */
    public void addItem(ApplicationMenuItem anItem) {
        items.add(anItem);
    }
    
    /**
     * creates a Swing menu
     * @return Swing menu
     */
    public JMenu createMenu() {
        JMenu menu = new JMenu(label);
        if (mnemonic != -1) {
            menu.setMnemonic(mnemonic);
        }
        for (ApplicationMenuItem item : items) {
            if (item.isSeparator()) {
                menu.addSeparator();
            } else {
                JMenuItem menuitem = item.createMenuItem();
                menu.add(menuitem);
                menuBar.registerItem(item.getKey(), menuitem);
            }
        }
        return menu;
    }

    /**
     * returns the menu bar
     * @return menu bar that the menu belongs to
     */
    public ApplicationMenuBar getMenuBar() {
        return menuBar;
    }
}
