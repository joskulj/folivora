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
package de.jochenskulj.taskmanager.command;

import java.awt.Component;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.menu.CommandBase;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.view.ApplicationFrame;
import de.jochenskulj.taskmanager.view.DetailPanelBase;

/**
 * Command to refresh the views manually 
 */
public class RefreshCommand extends CommandBase {

	/**
	 * creates an instance
	 * @param application
	 *        application to operate on
	 */
	public RefreshCommand(Application application) {
		super(CommandBase.REFRESH, application);
	}

	/**
     * executes the command
     */
    @Override
    public void execute() {
    	ApplicationFrame frame = getApplication().getFrame();
    	if (frame != null) {
    		DetailPanelBase panel = frame.getCurrentDetailPanel();
    		if (panel != null) {
    			for (Component c : panel.getComponents()) {
    				c.repaint();
    			}
    		}
    	}
    }

    /**
     * handles the update event from the model
     */
    @Override
    public void update() {
    }

}
