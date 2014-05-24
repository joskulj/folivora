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

import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementListBase;


import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/** 
 * Table class that synchronizes the selection with an element list
 */
public class ElementTable extends JTable implements ApplicationModelListener, ListSelectionListener {
    
    private ElementListBase list;
    private int lastRow = -1;
    
    /**
     * creates an instance
     * @param aList
     *        element list that should be used as model
     */
    public ElementTable(ElementListBase aList) {
        super(aList);
        list = aList;
        list.addListener(this);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(this);
        update();
    }
    
    /**
     * update object after changes of the model
     */
    public void update() {
        int row = list.getSelectedRow();
        if (row >= 0 && row < list.getRowCount()) {
        	setRowSelectionInterval(row, row);
        }
    }
    
    /**
     * handles the event to change the selection
     * @param e
     *        event to handle
     */
    public void valueChanged(ListSelectionEvent e) {
        super.valueChanged(e);
        if (e.getSource() == getSelectionModel() && e.getFirstIndex() >= 0 ) {
            int row = getSelectedRow();
            if (row != lastRow && row != -1) {
                
                list.setSelectedRow(row);
                lastRow = row;
            }
        }    
    }

}
