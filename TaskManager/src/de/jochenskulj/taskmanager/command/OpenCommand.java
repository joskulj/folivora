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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.menu.CommandBase;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.view.ApplicationFrame;

/**
 * Command to open a file
 */
public class OpenCommand extends CommandBase {
    
	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param application
     *        application to operate on
     */
    public OpenCommand(Application application) {
        super(CommandBase.OPEN, application);
    }

    /**
     * executes the command
     */
    @Override
    public void execute() {
    	
    	logger.debug("OpenCommand.execute() entered");
    	
    	getApplication().getFrame().saveCurrentRow();
    	ApplicationModel model = getApplication().getModel();
    	ApplicationFrame frame = getApplication().getFrame();
    	try {
    		if (model.hasFilename()) {
    			model.save();
    		}
    		JFileChooser fc = new JFileChooser();
    		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter( 
                    "XML-File", "xml");
    		fc.setFileFilter(xmlFilter);
    		int choice = fc.showOpenDialog(frame.getFrame());
    		if (choice == JFileChooser.APPROVE_OPTION) {
    			String  filename = fc.getSelectedFile().toString();
    			model.load(filename);
    		}

    	} catch (ApplicationException e) {
    		frame.showException(e);
    	}
    	
    	logger.debug("OpenCommand.execute() exited");
    	
    }

    /**
     * handles the update event from the model
     */
    @Override
    public void update() {
    }
}
