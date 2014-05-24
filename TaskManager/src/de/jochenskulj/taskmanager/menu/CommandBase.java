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

import java.util.ArrayList;

import java.util.Collection;

import javax.swing.JMenuItem;

/**
 * Base class for menu commands
 */
public abstract class CommandBase {
    
    private String key;
    private Application application;
    private ArrayList<JMenuItem> items = new ArrayList<JMenuItem>();
    
    public static final String NEW = "New";
    public static final String OPEN = "OPEN";
    public static final String SAVE = "SAVE";
    public static final String SAVE_AS = "SAVE_AS";
    public static final String TASK = "TASK";
    public static final String PROJECT = "PROJECT";
    public static final String PEOPLE = "PEOPLE";
    public static final String CONTEXT = "CONTEXT";
    public static final String EXIT = "EXIT";
    public static final String REFRESH = "REFRESH";
    public static final String ABOUT = "ABOUT";
    
    /**
     * creates an instance
     * @param aKey
     *        key to identify the command
     * @param anApplication
     *        application that the command is part of
     */
    public CommandBase(String aKey, Application anApplication) {
        key = aKey;
        application = anApplication;
    }

    /**
     * adds a menu item that is connected to the command
     * @param aMenuItem
     *        menu item that is connected to the command
     */
    public void addMenuItem(JMenuItem aMenuItem) {
        items.add(aMenuItem);
    }
    
    /**
     * executes the command
     */
    public abstract void execute();
    
    /**
     * returns application that the command is part of
     * @return application that the command is part of
     */
    public Application getApplication() {
        return application;
    }
    
    /**
     * returns the key of the command
     * @return key of the command
     */
    public String getKey() {
        return key;
    }
    
    /**
     * returns a collection of menu items that is connected to 
     * the command
     * @return collection of menu items
     */
    public Collection<JMenuItem> getMenuItems() {
        return items;
    }
    
    /**
     * updates the menu items
     */
    public abstract void update();
}
