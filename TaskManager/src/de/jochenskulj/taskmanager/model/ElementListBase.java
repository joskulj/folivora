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
import java.util.Collection;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

/**
 * Base class for element list
 */
public abstract class ElementListBase extends AbstractTableModel {

	private static Logger logger = Logger.getRootLogger();
	
    public static final int LIST_MODE = 1;
    public static final int DETAIL_MODE = 2;
    
    private int mode = LIST_MODE;    
    private ApplicationModel model;
    private ElementType type;
    private ArrayList<String> columnNames;
    private ArrayList<ElementBase> elements;
    private ArrayList<ApplicationModelListener> listeners;
    private int selectedRow = -1;
    private boolean active = true;
    private boolean fireFlag = true;
    private boolean modifiedFlag = false;
    
    /**
     * creates an instance
     * @param aModel
     *        application model that the list belongs to
     * @param aType
     *        type of the elements in the list
     */
    public ElementListBase(ApplicationModel aModel, ElementType aType) {
        model = aModel;
        type = aType;
        columnNames = new ArrayList<String>();
        elements = new ArrayList<ElementBase>();
        listeners = new ArrayList<ApplicationModelListener>();
    }

    /**
     * checks, if the element has been modified
     * @return <code>true</code>, if the element is modified; otherwise
     *         <code>false</code>
     */
    public boolean isModified() {
    	if (!modifiedFlag) {
    		for (ElementBase element : elements) {
    			if (element.isModified()) {
    				modifiedFlag = true;
    				break;
    			}
    		}
    	}
    	logger.debug("result [" + type + "]: " + modifiedFlag);
    	return modifiedFlag;
    }
    
    /**
     * marks the element as modified
     */
    public void setModified() {
    	modifiedFlag = true;
    	
    	logger.debug("Method executed [" + type + "]");
    	
    }
    
    /**
     * clears the flag that marks the element as modified
     */
    public void clearModified() {
    	for (ElementBase element : elements) {
    		element.clearModified();
    	}
    	modifiedFlag = false;
    	
    	logger.debug("Method executed [" + type + "]");

    }
    
    /**
     * set the flag to fire updates to listeners
     * @param aFlag
     *        flag to fire updates to listeners
     */
    public void setActive(boolean aFlag) {
    	
    	logger.debug("Method entered [" + type + "]");
    	logger.debug("aFlag: " + aFlag);
    	
    	if (aFlag == true) {
    		active = true;
    		if (fireFlag) {
    			fireUpdate();
    			fireFlag = false;
    		}
    	} else {
    		active = false;
    		fireFlag = false;
    	}
    	
    	logger.debug("ElementListBase.active: " + active);
    	logger.debug("ElementListBase.fireFlag: " + fireFlag);
    	logger.debug("Method exited [" + type + "]");

    }
   
    /**
     * resets the selection of the list
     */
    public void resetSelection() {
    	
    	logger.debug("Method entered [" + type + "]");
    	
    	if (getRowCount() > 0) {
    		selectedRow = 0;
    	} else {
    		selectedRow = -1;
    	}
    	
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * adds a column name
     * @param aName
     *        column name
     */
    public void addColumnName(String aName) {
        columnNames.add(aName);
    }

    /**
     * adds an element
     * @param anElement
     *        element to add
     */
    public void addElement(ElementBase anElement) {
    	
    	logger.debug("Method entered [" + type + "]");
    	logger.debug("anElement: " + anElement.getSelectionLabel());
        
    	elements.add(anElement);
        setModified();
        anElement.setList(this);
        anElement.register();
        selectedRow = elements.size() - 1;
    	
        logger.debug("ElementListBase.selectedRow: " + selectedRow);
        
        fireUpdate();
        
        logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * removes the selected element
     */
    public void removeSelectedElement() {

    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	
    	if (selectedRow >= 0 && selectedRow < elements.size()) {
    		ElementBase selectedElement = elements.get(selectedRow);
    		selectedElement.unregister();
    		elements.remove(selectedRow);
    		setModified();
    		if (selectedRow >= elements.size()) {
    			selectedRow = elements.size() - 1;
    			if (selectedRow < 0) {
    				mode = LIST_MODE;
    			}
    		}
    		fireUpdate();
    	}
    	
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * removes all elements
     */
    public void removeAll() {
    	
    	logger.debug("Method entered [" + type + "]");
    	
    	for (ElementBase element : elements) {
    		element.unregister();
    	}
    	elements.removeAll(elements);
    	selectedRow = -1;
    	mode = LIST_MODE;
    	clearModified();
    	fireUpdate();
    	
    	logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * adds a listener
     * @param aListener
     *        listener to add
     */
    public void addListener(ApplicationModelListener aListener) {
        listeners.add(aListener);
    }
    
    /**
     * creates a new element in the list
     * @return the new element
     */
    public abstract ElementBase createNewElement();
    
    /**
     * signals all model listeners to update
     */
    public void fireUpdate() {
    	
    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.active: " + active);

    	if (active) {
	        for (ApplicationModelListener listener : listeners) {
	            listener.update();
	        }
	        model.fireUpdate();
    	} else {
    		fireFlag = true;
    	}
    	
    	logger.debug("Method exited [" + type + "]");
    }
    
    public int getColumnCount() {
        return columnNames.size(); 
    }
    
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    /**
     * returns an element within the list
     * @param anIndex
     *        index in the list
     * @return element at the given index
     */
    public ElementBase getElement(int anIndex) {
    	ElementBase result = null;
    	if (anIndex >= 0 && anIndex < elements.size()) {
    		result = elements.get(anIndex);
    	}
        return result; 
    }
    
    /**
     * returns a collection of elements
     * @return collection of elements
     */
    public ArrayList<ElementBase> getElements() {
    	return elements;
    }

    /**
     * returns the index of an element within the list
     * @param anElement
     *        an element in the list
     * @return index of the list
     */
    public int getElementIndex(ElementBase anElement) {
        int result = -1;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) == anElement) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * returns the current mode
     * @return current mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * returns application model that the list belongs to
     * @return application model that the list belongs to
     */
    public ApplicationModel getModel() {
        return model;
    }
    
    public int getRowCount() { 
        return elements.size(); 
    }
    
    /**
     * returns the index of the selected selectedRow
     * @return index of the selected selectedRow
     */
    public int getSelectedRow() {
        return selectedRow;
    }
    
    /**
     * returns the id of the selected element
     * @return id of the selected element or  -1, if no element is selected
     */
    public long getSelectedId() {
    	long result = -1;
    	if (selectedRow >= 0 && selectedRow < elements.size()) {
    		ElementBase selectedElement = elements.get(selectedRow);
    		result = selectedElement.getId();
    	}
    	return result;
    }
 
    /**
     * type of the elements in the list
     * @return type of the elements in the list
     */
    public ElementType getType() {
        return type;
    }
    
    public Object getValueAt(int row, int col) {
        Object result = null;
        if (row < elements.size()) {
            ElementBase element = elements.get(row);
            if (element != null) {
                result = element.getValue(col);
            }
        }
        return result;
    }
    
    public boolean isCellEditle(int row, int col) { 
        return true; 
    }
        
    /**
     * removes a listener
     * @param aListener
     *        model listener to remove
     */
    public void removeListener(ApplicationModelListener aListener) {
        listeners.remove(aListener);
    }
        
    /**
     * selects the next row
     */
    public void selectNext() {
    	
    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	
    	if (selectedRow < getRowCount() - 1) {
            ++selectedRow;
            
            logger.debug("ElementListBase.selectedRow: " + selectedRow);
            
            fireUpdate();
        }     

    	logger.debug("Method exited [" + type + "]");

    }
        
    /**
     * selects the next row in cycling order
     */
    public void selectNextCycling() {

    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	
    	if (selectedRow < getRowCount() - 1) {
            ++selectedRow;
            
            logger.debug("ElementListBase.selectedRow: " + selectedRow);
            
            fireUpdate();
        } else {
        	if (getRowCount() > 0) {
        		selectedRow = 0;
        		
        		logger.debug("ElementListBase.selectedRow: " + selectedRow);
        		
        		fireUpdate();
        	}
        }
       
    	logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * selects the previous selectedRow
     */
    public void selectPrevious() {

    	logger.debug("Method entered [" + type + "]");
        logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	
    	if (selectedRow > 0) {
            --selectedRow;
            
            logger.debug("ElementListBase.selectedRow: " + selectedRow);
            
            fireUpdate();
        }

    	logger.debug("Method exited [" + type + "]");
    }
        
    /**
     * selects the previous row in cycling order
     */
    public void selectPreviousCycling() {
 
    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.selectedRow: " + selectedRow);
    	
    	if (selectedRow > 0) {
            --selectedRow;
            
            logger.debug("ElementListBase.selectedRow: " + selectedRow);
            
            fireUpdate();
        } else {
        	if (getRowCount() > 0) {
        		selectedRow = getRowCount() - 1;
        		
        		logger.debug("ElementListBase.selectedRow: " + selectedRow);
        		
        		fireUpdate();
        	}
        }

       	logger.debug("Method entered [" + type + "]");
    }
        
    /**
     * updates an element within the list
     * @param anIndex
     *        index to update
     * @param anElement
     *        new element
     */
    public void setElement(int anIndex, ElementBase anElement) {
    	if (anElement != null) {
    		elements.set(anIndex, anElement);
    	}
    }
    
    /**
     * changes the mode
     * @param aMode
     *        mode to set
     */
    public void setMode(int aMode) {
    	
       	logger.debug("Method entered [" + type + "]");
        logger.debug("ElementListBase.aMode: " + aMode);
        logger.debug("ElementListBase.mode: " + mode);
        
        if (mode == aMode) {
            return;
        }
        if (aMode == DETAIL_MODE) {
            if (type.getDetailFlag()) {
                mode = aMode;
                fireUpdate();
            }
        } else {
            mode = aMode;
            fireUpdate();
        }

        logger.debug("ElementListBase.mode: " + mode);
       	logger.debug("Method exited [" + type + "]");
    }
    
    /**
     * sets the mode internally without firing an update
     * @param aMode
     *        mode to set
     */
    protected void setModeInternal(int aMode) {
        mode  = aMode;
    }
    
    /**
     * sets the index of the selected selectedRow
     * @param anIndex
     * index of the selected selectedRow
     */
    public void setSelectedRow(int anIndex) {
    	
    	logger.debug("Method entered [" + type + "]");
        
        selectedRow = anIndex;
        logger.debug("ElementListBase.selectedRow: " + selectedRow);
        
        fireUpdate();

    	logger.debug("Method exited [" + type + "]");
    }
    
    public void setValueAt(Object value, int row, int col) {
        if (row < elements.size()) {
            ElementBase element = elements.get(row);
            if (element != null) {
                element.setValue(col, value);
            }
        }
    }
    
    /**
     * switches to the other mode
     * @return mode after the switch
     */
    public int switchMode() {

    	logger.debug("Method entered [" + type + "]");
    	logger.debug("ElementListBase.mode: " + mode);
    	
    	setActive(false);
        if (mode == DETAIL_MODE) {
            mode = LIST_MODE;
        	
            logger.debug("ElementListBase.mode: " + mode);

        	fireUpdate();
            // Workaround to update the selection
            selectPreviousCycling();
            selectNextCycling();
        } else {
            if (type.getDetailFlag()) {
                mode = DETAIL_MODE;

            	logger.debug("ElementListBase.mode: " + mode);
                
                fireUpdate();
            }
        }
        setActive(true);

    	logger.debug("Method exited [" + type + "]");
        
        return mode;
    }
    @Override
    public String toString() {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("[");
    	buffer.append("type");
    	buffer.append(" = ");
    	buffer.append(type);
    	buffer.append(", mode");
    	buffer.append(" = ");
    	buffer.append(mode);
    	buffer.append(", selectedRow");
    	buffer.append(" = ");
    	buffer.append(selectedRow);
    	buffer.append(", elements.size");
    	buffer.append(" = ");
    	buffer.append(elements.size());
    	buffer.append("]");
    	return buffer.toString();
    }

 }
