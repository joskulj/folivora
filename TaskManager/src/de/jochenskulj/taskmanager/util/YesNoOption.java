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
package de.jochenskulj.taskmanager.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * Helper class to show an OptionPane
 */
public class YesNoOption {
	
	private static Logger logger = Logger.getRootLogger();
	
	public static int YES_OPTION = JOptionPane.YES_OPTION;
	public static int NO_OPTION = JOptionPane.NO_OPTION;
	public static int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
	
	private JFrame frame;
	private String title = "Question";
	private String question = "Are you sure?";

	/**
	 * creates instance
	 * @param aFrame
	 *         frame for modal display
	 */
	public YesNoOption(JFrame aFrame) {
		frame = aFrame;
	}

	/**
	 * sets the title
	 * @param aTitle
	 *        title to set
	 */
	public void setTitle(String aTitle) {
		title = aTitle;
	}
	
	/**
	 * sets the question
	 * @param aQuestion
	 *        question to set
	 */
	public void setQuestion(String aQuestion) {
		question = aQuestion;
	}
	
	
	/**
	 * shows the Option Dialog
	 * @return <code>YES_OPTION</code>, <code>NO_OPTION</code> or
	 *         <code>CANCRL_OPTION</code>
	 */
	public int show() {
		
		logger.debug("Method entered");
		logger.debug("title: " + title);
		logger.debug("question: " + question);
		
		int type = JOptionPane.YES_NO_OPTION;
		int result = JOptionPane.showConfirmDialog(frame, question, title, type);
		
		if (result == YES_OPTION) {
			logger.debug("result: YES_OPTION");
		}
		if (result == NO_OPTION) {
			logger.debug("result: NO_OPTION");
		}
		
		logger.debug("Method exited");
		
		return result;
	}
}
