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
import de.jochenskulj.taskmanager.command.AboutCommand;
import de.jochenskulj.taskmanager.command.ExitCommand;
import de.jochenskulj.taskmanager.command.NewCommand;
import de.jochenskulj.taskmanager.command.OpenCommand;
import de.jochenskulj.taskmanager.command.RefreshCommand;
import de.jochenskulj.taskmanager.command.SaveAsCommand;
import de.jochenskulj.taskmanager.command.SaveCommand;
import de.jochenskulj.taskmanager.command.SelectContextCommand;
import de.jochenskulj.taskmanager.command.SelectPeopleCommand;
import de.jochenskulj.taskmanager.command.SelectProjectCommand;
import de.jochenskulj.taskmanager.command.SelectTaskCommand;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * menu bar of the application
 */
public class ApplicationMenuBar implements ApplicationModelListener {
    
    private Application application;
    private ArrayList<CommandBase> commandList = new ArrayList<CommandBase>();
    private HashMap<String, CommandBase> commandMap = new HashMap<String, CommandBase>();
    private ArrayList<ApplicationMenu> menus = new ArrayList<ApplicationMenu>();
    private JMenuBar menuBar;
    
    /**
     * creates an instance
     * @param anApplication
     *        application that the menu bar belongs to
     */
    public ApplicationMenuBar(Application anApplication) {
        application = anApplication;
        createCommands();
        createFileMenu();
        createEditMenu();
        createHelpMenu();
    }
    
    /**
     * creates the About menu
     */
    protected void createHelpMenu() {
    	ApplicationMenu helpMenu = new ApplicationMenu(this, "Help", KeyEvent.VK_H);
        ApplicationMenuItem item = new ApplicationMenuItem(helpMenu, "About", KeyEvent.VK_A, CommandBase.ABOUT);
        menus.add(helpMenu);
    }
    
    /**
     * creates all command instances
     */
    protected void createCommands() {
        CommandBase command = new OpenCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new NewCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SaveCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SaveAsCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SelectContextCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SelectPeopleCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SelectProjectCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new SelectTaskCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new ExitCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new RefreshCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
        command = new AboutCommand(application);
        commandList.add(command);
        commandMap.put(command.getKey(), command);
    }
    
    /**
     * creates the Edit menu
     */
    protected void createEditMenu() {
        
    }
    
    /**
     * creates the File menu
     */
    protected void createFileMenu() {
        ApplicationMenu fileMenu = new ApplicationMenu(this, "File", KeyEvent.VK_F);
        ApplicationMenuItem item = new ApplicationMenuItem(fileMenu, "New", KeyEvent.VK_N, CommandBase.NEW);
        item = new ApplicationMenuItem(fileMenu, "Open", KeyEvent.VK_O, CommandBase.OPEN);
        item = new ApplicationMenuItem(fileMenu, "Save", KeyEvent.VK_S, CommandBase.SAVE);
        item = new ApplicationMenuItem(fileMenu, "Save As...", KeyEvent.VK_A, CommandBase.SAVE_AS);
        item = new ApplicationMenuItem(fileMenu);
        // item = new ApplicationMenuItem(fileMenu, "Refresh", KeyEvent.VK_R, CommandBase.REFRESH);
        // item = new ApplicationMenuItem(fileMenu);
        item = new ApplicationMenuItem(fileMenu, "Task", KeyEvent.VK_T, CommandBase.TASK);
        item = new ApplicationMenuItem(fileMenu, "Project", KeyEvent.VK_P, CommandBase.PROJECT);
        item = new ApplicationMenuItem(fileMenu, "People", KeyEvent.VK_E, CommandBase.PEOPLE);
        item = new ApplicationMenuItem(fileMenu, "Context", KeyEvent.VK_C, CommandBase.CONTEXT);
        item = new ApplicationMenuItem(fileMenu);
        item = new ApplicationMenuItem(fileMenu, "Exit", KeyEvent.VK_X, CommandBase.EXIT);
        menus.add(fileMenu);
    }
    
    /**
     * executes a menu command
     * @param aCommandKey
     *        key of the command to execute
     */
    public void executeCommand(String aCommandKey) {
        if (commandMap.keySet().contains(aCommandKey)) {
            CommandBase command = commandMap.get(aCommandKey);
            command.execute();
        }
    }
    
    /**
     * returns the Swing menu bar
     * @return
     */
    public JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            for (ApplicationMenu menu : menus) {
                menuBar.add(menu.createMenu());
            }
        }
        return menuBar;
    }
    
    /**
     * registers a Swing menu item for a given command key
     * @param aKey
     *        commandd key
     * @param anItem
     *        item to register
     */
    public void registerItem(String aKey, JMenuItem anItem) {
        boolean flag = false;
        for (CommandBase command : commandList) {
            if (command.getKey().equals(aKey)) {
                command.addMenuItem(anItem);
                flag = true;
            }
        }
        if (flag == false) {
            anItem.setEnabled(false);
        }
    }
    
    /**
     * update object after changes of the model
     */
    @Override
    public void update() {
        for (CommandBase command : commandList) {
            command.update();
        }
    }

}
