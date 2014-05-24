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
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.menu.CommandBase;
import de.jochenskulj.taskmanager.model.ApplicationModel;

/**
 * Command to save the file
 */
public class SaveCommand extends CommandBase {
    
	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param application
     *        application to operate on
     */
    public SaveCommand(Application application) {
        super(CommandBase.SAVE, application);
    }

    /**
     * executes the command
     */
    @Override
    public void execute() {
    	
    	logger.debug("SaveCommand.execute() entered");
    	
    	getApplication().getFrame().saveCurrentRow();
    	ApplicationModel model = getApplication().getModel();
    	JFrame frame = getApplication().getFrame().getFrame();
    	String filename = model.getFilename();
    	if (filename == null) {
    		JFileChooser fc = new JFileChooser();
    		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter( 
                    "XML-File", "xml");
    		fc.setFileFilter(xmlFilter);
    		int choice = fc.showSaveDialog(frame);
    		if (choice == JFileChooser.APPROVE_OPTION) {
    			filename = fc.getSelectedFile().toString();
    		}
    	}
    	if (filename != null) {
    		try {
    			model.save(filename);
    		} catch (ApplicationException e) {
    			getApplication().getFrame().showException(e);
    		}
    	}
    	
    	logger.debug("SaveCommand.execute() exited");
    	
    }

    /**
     * handles the update event from the model
     */
    @Override
    public void update() {
    }
}
