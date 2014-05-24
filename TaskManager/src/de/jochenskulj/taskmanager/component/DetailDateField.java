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
package de.jochenskulj.taskmanager.component;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

import de.jochenskulj.taskmanager.util.Formatter;
import de.jochenskulj.taskmanager.view.DetailPanelBase;

/**
 * Class for Date fields
 */
public class DetailDateField extends TextField
		implements DetailPanelElement,  FocusListener {

	private DetailPanelBase detailPanel;
	private boolean validated = false;
	private boolean valid;
	private String errorMessage = 
			"Required format: " + Formatter.getDatePattern();

	/**
	 * create instance 
	 */
	public DetailDateField() {
		super();
		addFocusListener(this);
	}

	/**
	 * returns the date of the string
	 * @return date of the string
	 */
	public Date getDate() {
		Date result = null;
		String text = getText();
		if (text != null) {
			if (text.length() > 0) {
				if (Formatter.checkDateString(text)) {
					result = Formatter.convertStringDate(text);
				}
			}
		}
		return result;
	}
	
	/**
	 * sets the date of the string
	 * @param aDate
	 *        date of the string
	 */
	public void setDate(Date aDate) {
		String text = Formatter.formatDateString(aDate);
		setText(text);
	}
	
	/**
	 * returns, if the element is valid
	 * @return <code>true</code>: element is valid
	 *         <code>false</code>: element is not valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * returns, if the element has been validated
	 * @return <code>true</code>: element  has been validated
	 *         <code>false</code>: element has not been validated
	 */
	public boolean isValidated() {
		return validated;
	}
	
	/**
	 * sets the DetalPanel
	 * @param aPanel
	 *        DetailPanel that the element belongs to
	 */
	public void setDetailPanel(DetailPanelBase aPanel) {
		detailPanel = aPanel;
	}
	
	/**
	 * updates the element according to the validation
	 */
	public void updateValidationStatus() {
		if (validated == true) {
			if (valid == true) {
				if (detailPanel != null) {
					StatusLabel statusLabel = detailPanel.getFrame().getStatusLabel();
					statusLabel.clearStatus();
					setBackground(Color.WHITE);
				}
			} else {
				if (detailPanel != null) {
					StatusLabel statusLabel = detailPanel.getFrame().getStatusLabel();
					statusLabel.setStatus(errorMessage, true);
					setBackground(Color.ORANGE);
				}
			}
		}
	}
	
	/**
	 * sets the focus to the element
	 */
	public void setFocus() {
		requestFocus();
	}

	/**
	 * validates the element
	 * @return <code>true</code>: element is valid
	 *         <code>false</code>: element is not valid
	 */
	public boolean validateElement() {
		valid = true;
		String text = getText();
		if (text.length() > 0) {
			valid = Formatter.checkDateString(text);
		}
		validated = true;
		return valid;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if (validated == true && valid == false) {
			if (detailPanel != null) {
				StatusLabel statusLabel = detailPanel.getFrame().getStatusLabel();
				statusLabel.setStatus(errorMessage, true);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (validated == true) {
			validateElement();
		}
		if (detailPanel != null) {
			StatusLabel statusLabel = detailPanel.getFrame().getStatusLabel();
			statusLabel.clearStatus();
		}
	}
}
