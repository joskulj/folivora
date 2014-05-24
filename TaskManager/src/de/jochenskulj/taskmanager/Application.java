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
package de.jochenskulj.taskmanager;

import java.io.File;

import javax.swing.UIManager;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;

import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.view.ApplicationFrame;
import de.jochenskulj.taskmanager.util.IconFactory;

/**
 * Main class of the application
 */
public class Application {

	private static Logger logger = Logger.getRootLogger();

	private ApplicationFrame frame;
	private ApplicationModel model = new ApplicationModel();
	private static ApplicationProperty properties = new ApplicationProperty();

	/**
	 * creates an application instance
	 */
	public Application() {
		initLogger();
	}

	/**
	 * returns directory of the application
	 * 
	 * @return directory of the application
	 */
	public static String getDirectory() {
		return properties.get(ApplicationProperty.DIRECTORY);
	}

	/**
	 * returns the application model
	 * 
	 * @return the application model
	 */
	public ApplicationModel getModel() {
		return model;
	}

	/**
	 * returns the main frame of the application
	 * 
	 * @return main frame of the application
	 */
	public ApplicationFrame getFrame() {
		return frame;
	}

	/**
	 * runs the application
	 */
	public void run() {
		
		logger.info("Application started.");

		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			
			logger.warn("Look and Feel " + lookAndFeel + " not available.");
			
		}
		frame = new ApplicationFrame(this);
		ApplicationModel model = frame.getApplication().getModel();
		ElementType type = model.getTaskList().getType();
		try {
			model.setCurrentElementType(type);
		} catch (ApplicationException e) {
			if (frame != null) {
				frame.showException(e);
			}
		}
	}

	/**
	 * closes the application
	 */
	public void close() {
		if (frame != null) {
			frame.close();
		}
	}

	/**
	 * creates a new element
	 * 
	 * @throws ApplicationException
	 *             is thrown if an unexpected exception occurs
	 */
	public void createNewElement() throws ApplicationException {
		
		logger.debug("Method entered");
		
		try {
			if (validateElements() == true) {
				ElementListBase list = model.getCurrentList();
				if (list != null) {
					list.createNewElement();
				}
			}
		} catch (Exception e) {
			
			logger.error("Exception catched: " + e.getMessage());
			
			throw new ApplicationException(e);
		}
		
		logger.debug("Method exited.");
	}

	/**
	 * deletes the current element
	 * 
	 * @throws ApplicationException
	 *             is thrown if an unexpected exception occurs
	 */
	public void deleteCurrentElement() throws ApplicationException {
		
		logger.debug("Method entered.");
		
		try {
			model.deleteCurrentElement();
		} catch (Exception e) {
			
			logger.error("Exception catched: " + e.getMessage());
			
			throw new ApplicationException(e);
		}
		
		logger.debug("Method exited.");
	}

	/**
	 * selects the next element
	 * 
	 * @throws ApplicationException
	 *             is thrown if an unexpected exception occurs
	 */
	public void selectNext() throws ApplicationException {
		
		logger.debug("Method entered.");
		
		try {
			if (validateElements() == true) {
				
				logger.debug("validateElements() == true");
				
				ElementListBase list = model.getCurrentList();
				if (list != null) {
					list.selectNext();
				}
			}
		} catch (Exception e) {
			
			logger.error("Exception catched: " + e.getMessage());
			
			throw new ApplicationException(e);
		}
		
		logger.debug("Method exited.");
	}

	/**
	 * selects the previous element
	 * 
	 * @throws ApplicationException
	 *             is thrown if an unexpected exception occurs
	 */
	public void selectPrevious() throws ApplicationException {
		
		logger.debug("Method entered.");
		
		try {
			if (validateElements() == true) {
				
				logger.debug("validateElements() == true");
				
				ElementListBase list = model.getCurrentList();
				if (list != null) {
					list.selectPrevious();
				}
			}
		} catch (Exception e) {
			
			logger.error("Exception catched: " + e.getMessage());
			
			throw new ApplicationException(e);
		}
		
		logger.debug("Method exited.");
	}

	/**
	 * switches between the List Mode and Detail Mode
	 * 
	 * @throws ApplicationException
	 *             is thrown if an unexpected
	 */
	public void switchMode() throws ApplicationException {
		
		logger.debug("Method entered.");
		
		try {
			if (validateElements() == true) {
				
				logger.debug("validateElements() == true");
				
				ElementListBase list = model.getCurrentList();
				if (list != null) {
					list.switchMode();
				}
			}
		} catch (Exception e) {
			
			logger.error("Exception catched: " + e.getMessage());
			
			throw new ApplicationException(e);
		}
		
		logger.debug("Method exited.");
	}

	/**
	 * main functions of the application
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Application application = new Application();
		IconFactory.createIcon(IconFactory.NEW);
		application.run();
	}

	/**
	 * validate the elements
	 * 
	 * @return <code>true</code>: all elements are valid <code>false</code>:
	 *         there are invalid valid
	 */
	private boolean validateElements() {
		boolean result = true;
		if (frame.getCurrentDetailPanel() != null) {
			
			logger.debug("frame.getCurrentDetailPanel() != null");
			
			result = frame.getCurrentDetailPanel().validateElements();
		}
		
		logger.debug("result: " + result);
		
		return result;
	}

	/**
	 * inits the logger
	 */
	private void initLogger() {
		try {
			ApplicationProperty property = new ApplicationProperty();
			if (property.isTrue(ApplicationProperty.DEBUG)) {
				PatternLayout layout  = new PatternLayout("%-5p [%C{1}.%M]: %m%n");
				String logfile = property.get(ApplicationProperty.LOGFILE);
				if (logfile != null) {
					File file = new File(logfile);
					file.delete();
					FileAppender a = null;
					FileAppender fileAppender = new FileAppender(layout, logfile);
					logger.addAppender(fileAppender);
				} else {
					ConsoleAppender consoleAppender = new ConsoleAppender(layout);
					logger.addAppender(consoleAppender);					
				}
				logger.setLevel(Level.ALL);
			} else {
				logger.setLevel(Level.OFF);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
