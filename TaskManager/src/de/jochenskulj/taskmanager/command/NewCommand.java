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
import de.jochenskulj.taskmanager.util.YesNoOption;

public class NewCommand extends CommandBase {

	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param application
     *        application to operate on
     */
	public NewCommand(Application application) {
		super(CommandBase.NEW, application);
	}
    /**
     * executes the command
     */
    @Override
    public void execute() {
    	
    	logger.debug("NewCommand.execute() entered");
    	
    	getApplication().getFrame().saveCurrentRow();
    	try {
	    	ApplicationModel model = getApplication().getModel();
	    	JFrame frame = getApplication().getFrame().getFrame();
	    	boolean newFlag = true;
	    	if (model.isModified()) {
		    	if (model.hasFilename()) {
		    		model.save();
		    	} else {
		    		YesNoOption option = new YesNoOption(frame);
		    		option.setTitle("Reset Application Data");
		    		option.setQuestion("Discard unchanged chances?");
		    		newFlag = (option.show() == YesNoOption.YES_OPTION);
		    	}
		    	if (newFlag) {
		    		model.reset();
		    	}
	    	}
    	} catch (ApplicationException e) {
    		getApplication().getFrame().showException(e);
    	}
    	
    	logger.debug("NewCommand.execute() exited");
	}

    /**
     * handles the update event from the model
     */
    @Override
    public void update() {
    }

}
