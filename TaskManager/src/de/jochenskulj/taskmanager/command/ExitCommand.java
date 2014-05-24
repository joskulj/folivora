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

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.menu.CommandBase;

/**
 * Command to exit the application
 */
public class ExitCommand extends CommandBase {
    
	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param application
     *        application to operate on
     */
    public ExitCommand(Application application) {
        super(CommandBase.EXIT, application);
    }

    /**
     * executes the command
     */
    @Override
    public void execute() {
    	
    	logger.debug("ExitCommand.execute() entered");
    	
    	getApplication().getFrame().close();
    	
    	logger.debug("ExitCommand.execute() exited");
    }

    /**
     * handles the update event from the model
     */
    @Override
    public void update() {
    }
}
