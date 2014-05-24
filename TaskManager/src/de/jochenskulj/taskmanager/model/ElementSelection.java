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
package de.jochenskulj.taskmanager.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Class for a set of referenced elements
 */
public class ElementSelection {              
	
	private ElementListBase completeList;
	private ArrayList<ElementBase> __selection = new ArrayList<ElementBase>();
	private ArrayList<Long> selection = new ArrayList<Long>();

	/**
	 * creates an instance
	 * @param aList
	 *        element list that contains all elements to select
	 */
	public ElementSelection(ElementListBase aList) {
		completeList = aList;
	}
	
	/**
	 * returns Element list to select from
	 * @return Element list to select from
	 */
	public ElementListBase getCompleteList() {
		return completeList;
	}
	
	/**
	 * returns name of the Element to select
	 * @return name of the Element to select
	 */
	public String getElementType() {
		return completeList.getType().toString();
	}
	
	/**
	 * returns list of selected elements
	 * @return list of selected elements
	 */
	public ArrayList<ElementBase> getSelectedElements() {
		ArrayList<ElementBase> result = new ArrayList<ElementBase>();
		for (long id : selection) {
			ElementBase element = ElementBase.get(id);
			if (element != null) {
				result.add(element);
			}
		}
		return result;
	}
	
	/**
	 * adds an element to the selection
	 * @param anElement
	 *        element to add
	 */
	public void addSelection(ElementBase anElement) {
		long id = anElement.getId();
		if (id != -1) {
			if (!selection.contains(id)) {
				selection.add(id);
			}
		}
	}

	/**
	 * adds an element by its index to the selection
	 * @param anIndex
	 *        index of the element to add
	 */
	public void addSelection(int anIndex) {
		if (anIndex >= 0 && anIndex < completeList.getRowCount()) {
			ElementBase element = completeList.getElement(anIndex);
			if (element != null) {
				addSelection(element);
			}
		}
	}

	/**
	 * removes an element from the selection
	 * @param anElement
	 *        element to remove from the selection
	 */
	public void removeSelection(ElementBase anElement) {
		long id = anElement.getId();
		if (selection.contains(id)) {
			selection.remove(id);
		}
	}

	/**
	 * removes an element by its index from the selection
	 * @param anIndex
	 *        index of the element to remove from the selection
	 */
	public void removeSelection(int anIndex) {
		if (anIndex >= 0 && anIndex < completeList.getRowCount()) {
			ElementBase element = completeList.getElement(anIndex);
			if (element != null) {
				removeSelection(element);
			}
		}
	}
	
	/**
	 * sets the selection by an index
	 * @param anIndex
	 *        index of an element
	 * @param aFlag
	 *        flag, if the element should be added or removed from
	 *        the selection
	 */
	public void setSelection(int anIndex, boolean aFlag) {
		if (anIndex >= 0 && anIndex < completeList.getRowCount()) {
			ElementBase element = completeList.getElement(anIndex);
			if (aFlag) {
				addSelection(element);
			} else {
				removeSelection(element);			
			}
		}
	}
	
	/**
	 * checks if an element is selected
	 * @param anElement
	 *        element to check
	 * @return boolean value, if the element is selected or not
	 */
	public boolean getSelection(ElementBase anElement) {
		boolean result = false;
		long id = anElement.getId();
		if (id != -1) {
			result = selection.contains(id);
		}
		return result;
	}
	
	/**
	 * checks by the index if an element is selected
	 * @param anIndex
	 *        index of an element to check
	 * @return boolean value, if the element is selected or not
	 */
	public boolean getSelection(int anIndex) {
		boolean result = false;
		if (anIndex >= 0 && anIndex < completeList.getRowCount()) {
			ElementBase element = completeList.getElement(anIndex);
			result = getSelection(element);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ElementSelection) {
			ElementSelection other = (ElementSelection) obj;
			if (selection.size() > 0) {
				for (Long thisId : selection) {
					result = false;
					for (Long otherId : other.selection) {
						if (thisId.equals(otherId)) {
							result = true;
							break;
						}
					}
					if (result == false) {
						break;
					}
				}
				if (selection.size() != other.selection.size()) {
					result = false;
				}
			} else {
				result = (other.selection.size() == 0);
			}
		}
		return result;
	}
}
