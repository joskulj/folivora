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

import java.applet.AppletStub;

import javax.swing.table.AbstractTableModel;

import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ElementSelection;

/**
 * Table model for element selections 
 */
public class ElementSelectionModel extends AbstractTableModel 
		implements ApplicationModelListener {

	private String[] columnNames = { "Element", "Selected" };
	private Class[] columnClasses = { String.class, Boolean.class };
	private Object[][] values;
	private ElementSelection selection;
	private ElementListBase completeList;
	
	/**
	 * creates an instance
	 */
	public ElementSelectionModel() {

	}
	
	/**
	 * sets the selection
	 * @param aSelection
	 *        selection to select
	 */
	public void setSelection(ElementSelection aSelection) {
		selection = aSelection;
		updateSelection(selection);
		if (completeList != null) {
			completeList.removeListener(this);
		}
		completeList = selection.getCompleteList();
		if (completeList != null) {
			completeList.addListener(this);
		}
	}
	
	/**
	 * returns the selection
	 * @return the selection
	 */
	public ElementSelection getSelection() {
		ElementListBase elementList = selection.getCompleteList();
		ElementSelection result = new ElementSelection(elementList);
		for (int i = 0; i < values.length; i++) {
			Boolean selected = (Boolean) values[i][1];
			if (selected == true) {
				result.addSelection(i);
			}
		}
		return result;
	}

	@Override
	public int getColumnCount() {
        return columnNames.length;
    }

	@Override
    public int getRowCount() {
		int result = 0;
		if (values != null) {
			result = values.length;
		}
        return result;
    }

	@Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
    public Object getValueAt(int row, int col) {
        return values[row][col];
    }
	
	@Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	@Override
	public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

	@Override
    public void setValueAt(Object value, int row, int col) {
        values[row][col] = value;
        fireTableCellUpdated(row, col);
    }
	
    /**
     * update object after changes of the model
     */
	@Override
    public void update() {
		updateSelection(selection);
	}

	
	/**
	 * updates the table elements
	 * @param aSelection
	 *        selection to update the table elements
	 */
	private void updateSelection(ElementSelection aSelection) {
		ElementListBase elementList = aSelection.getCompleteList();
		int rowCount = elementList.getRowCount();
		values = new Object[rowCount][2];
		for (int i = 0; i < rowCount; i++) {
			ElementBase element = elementList.getElement(i);
			values[i][0] = element.getSelectionLabel();
			values[i][1] = new Boolean(selection.getSelection(element));
		}
		fireTableDataChanged();
	}
}
