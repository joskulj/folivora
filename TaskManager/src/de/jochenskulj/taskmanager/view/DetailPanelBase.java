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
package de.jochenskulj.taskmanager.view;

import de.jochenskulj.taskmanager.component.DetailPanelElement;
import de.jochenskulj.taskmanager.component.GridBagPanel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementBase;
import de.jochenskulj.taskmanager.model.ElementListBase;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Base class for detail panels
 */
public abstract class DetailPanelBase extends GridBagPanel 
		implements ApplicationModelListener {
    
	private static Logger logger = Logger.getRootLogger();
	
	private ApplicationFrame frame;
	private ArrayList<DetailPanelElement> panelElements = new ArrayList<DetailPanelElement>(); 
    private ElementListBase list;
    private int selectedRow = -1;
    private long selectedId = -1;
    private ElementBase currentElement = null;
    
    /**
     * creates an instance
     * @param aFrame
     *        ApplicationFrame that the panel belongs to
     * @param aList
     *        element list that should be used as model
     */
    public DetailPanelBase(ApplicationFrame aFrame, ElementListBase aList) {
        super();
        frame = aFrame;
        list = aList;
        list.addListener(this);
        setLayout(new GridBagLayout());
        setVerticalFill(false);
        initPanel();
    }
    
    /**
     * returns the ApplicationFrame that the panel belongs to
     * @return ApplicationFrame that the panel belongs to
     */
    public ApplicationFrame getFrame() {
    	return frame;
    }

    /**
     * returns first component to focus
     * @return first component to focus
     */
    public Component getFirstComponent() {
    	return null;
    }

    /**
     * sets the focus on the first component
     */
    public void focusFirstElement() {
    	if (getFirstComponent() != null) {
        	getFirstComponent().requestFocus();
        }
    }
    
    /* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component comp) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(this);
		}
		return super.add(comp);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, int)
	 */
	@Override
	public Component add(Component comp, int index) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(this);
		}
		return super.add(comp, index);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
	 */
	@Override
	public void add(Component comp, Object constraints) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(this);
		}
		super.add(comp, constraints);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object, int)
	 */
	@Override
	public void add(Component comp, Object constraints, int index) {
		if (comp instanceof DetailPanelElement) {
			DetailPanelElement panelElement = (DetailPanelElement) comp;
			panelElements.add(panelElement);
			panelElement.setDetailPanel(this);
		}
		super.add(comp, constraints, index);
	}
	
    /**
     * returns the current element
     * @return current object
     */
    public ElementBase getCurrentElement() {
        return currentElement;
    }

    /**
     * returns the selectedRow index
     * @return selectedRow index
     */
    public int getSelectedRow() {
        return selectedRow;
    }
    
    /**
     * initializes the components of the panel
     */
    public abstract void initPanel();
    
    /**
     * loads the values from an element
     * @param anElement
     *        element the load the values from
     */
    public abstract void loadValues(ElementBase anElement);
    
    /**
     * saves the current value to the model
     */
    public void saveCurrentRow() {
        if (selectedRow != -1) {
            ElementBase e1 = list.getElement(selectedRow);
            if (e1 != null) {
            	ElementBase e2 = saveValues(e1);
            	list.setElement(selectedRow, e2);
            } else {
            	selectedId = -1;
            }
        }
    }
    
    /**
     * saves the values to an element
     * @param anElement
     *        element to save the values from
     * @return element with the saved values
     */
    public abstract ElementBase saveValues(ElementBase anElement);
    
    /**
     * update object after changes of the model
     */
    public void update() {
 
    	logger.debug("Method entered");
    	
    	// check, if the list is in Detail Mode
    	if (list.getMode() != ElementListBase.DETAIL_MODE) {
    		frame.update();
    		return;
    	}
        
    	// request status information from the list
        int listRow = list.getSelectedRow();
        long listId = list.getSelectedId();
        
        // debug status information
    	logger.debug("list: " + list);
    	logger.debug("selectedRow: " + selectedRow);
    	logger.debug("listRow: " + listRow);
    	logger.debug("selectedId: " + selectedId);
    	logger.debug("getSelectedId(): " + list.getSelectedId());
        
        // check, if the panel has to be updated 
        boolean updateFlag = false;
        boolean elementChanged = false;
        boolean selectionChanged = false;
        boolean currentElementExists = false;
        
    	logger.debug("selectedId: " + selectedId);
    	logger.debug("listId: " + listId);

        if (selectedId != listId) {
        	elementChanged = true;
        	updateFlag = true;
        }

        if (selectedRow != listRow) {
        	selectionChanged = true;
        	updateFlag = true;
        	if (currentElement != null) {
        		if (currentElement.isRegistered()) {
        			currentElementExists = true;
        		}
        	}
        }

    	logger.debug("updateFlag: " + updateFlag);
    	logger.debug("elementChanged: " + elementChanged);
    	logger.debug("selectionChanged: " + selectionChanged);
    	logger.debug("currentElementExists: " + currentElementExists);
      
        if (updateFlag) {
        	if (currentElementExists && selectionChanged) {
        		// save current element, before new element is loaded
                ElementBase newElement = saveValues(currentElement);
                list.setElement(selectedRow, newElement);        		
        	}
        	
        	// load the new element
            currentElement = list.getElement(listRow);
            
            logger.debug("currentElement: " + currentElement);
            
            selectedRow = listRow;
            
            logger.debug("selectedRow: " + selectedRow);
            
            loadValues(currentElement);
            selectedId = list.getSelectedId();        	
        } else {
        	
        		refreshCurrentElement();
        	
        }
        
        focusFirstElement();
        
    	logger.debug("Method exited");
    }
    
    /**
     * refreshes the current value 
     * Method must implement a subset of loadValues()
     */
    public void refreshCurrentElement() {
    	
    }
    
    /**
     * validates the elements of the panel
     * @return <code>true</code>: all elements are valid
     *         <code>false</code>: there are invalid valid
     */
    public boolean validateElements() {
    	boolean result = true;
    	DetailPanelElement firstElement = null;
    	for (DetailPanelElement element : panelElements) {
    		boolean flag = element.validateElement();
    		element.updateValidationStatus();
    		if (flag == false) {
    			result = false;
    			if (firstElement == null) {
    				firstElement = element;
    			}
    		}
    	}
    	if (firstElement != null) {
    		firstElement.setFocus();
    	}
    	return result;
    }
}
