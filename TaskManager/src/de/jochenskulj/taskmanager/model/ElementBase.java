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
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Base class for elements
 */
public abstract class ElementBase {

	private static Logger logger = Logger.getRootLogger();
	
	private static long idCounter = 10000;
	private static HashMap<Long, ElementBase> elementMap 
											= new HashMap<Long, ElementBase>(); 
	
    private ElementListBase list;
    private long id = -1;
    private boolean modifiedFlag = false;
    
    /**
     * creates an instance
     */
    public ElementBase() {
        super();
    }
    
    /**
     * fires a change of the element
     */
    public void fireChanged() {
    	
    	logger.debug("Method entered [" + id + " ]");
    	
    	if (list != null) {
    		list.fireTableDataChanged();
    	}
        
    	logger.debug("Method exited [" + id + " ]");
    }
    
    /**
     * returns the list, that the element belongs to
     * @return list, that the element belongs to
     */
    public ElementListBase getList() {
        return list;
    }

    /**
     * returns the label to be used in selection tables
     * @return label to be used in selection tables
     */
    public String getSelectionLabel() {
    	String result = toString();
    	Object firstValue = getValue(0);
    	if (firstValue != null) {
    		result = firstValue.toString();
    	}
    	return result;
    }
    
    /**
     * returns the id of the element
     * @return id of the element
     */
    public long getId() {
    	return id;
    }
    
    /**
     * checks if the element is registered to the element map
     * @return <code>true</code>, if the element is registered to the element 
     *         map; otherwise <code>false</code>
     */
    public boolean isRegistered() {
    	return elementMap.containsKey(id);
    }
    
    /**
     * registers the element at the element map
     */
    public void register() {
    	
    	logger.debug("Method entered [" + id + " ]");
    	
    	if (!isRegistered()) {
	    	id = ++idCounter;
	    	elementMap.put(id, this);
    	}
    	
    	logger.debug("Method exited [" + id + " ]");
    }

    /**
     * registers the element at the element map with a given id
     * @param anId
     *        id to use for the element
     */
    public void register(long anId) {
    	
    	logger.debug("Method entered [" + id + " ]");
    	logger.debug("anId: " + anId);
    	
    	id = anId;
		if (id > idCounter) {
			idCounter = id;
		}
		elementMap.put(id, this);
		
		logger.debug("Method exited [" + id + " ]");
    }
    
    /**
     * removes the element from the element map
     */
    public void unregister() {
    	
    	logger.debug("Method entered [" + id + " ]");
    	
    	if (id != -1) {
    		if (elementMap.containsKey(id)) {
    			elementMap.remove(id);
    		}
    		id = -1;
    	}
    	
    	logger.debug("Method exited [" + id + " ]");
    }
    
    /**
     * retrieves an element from the element map
     * @param anId
     *        id of the element
     * @return retrieved element or <code>null</code> if the id is not valid
     */
    public static ElementBase get(long anId) {
    	ElementBase result = null;
    	if (elementMap.containsKey(anId)) {
    		result = elementMap.get(anId);
    	}
    	return result;
    }

    /**
     * checks, if the element has been modified
     * @return <code>true</code>, if the element is modified; otherwise
     *         <code>false</code>
     */
    public boolean isModified() {
    	return modifiedFlag;
    }
    
    /**
     * marks the element as modified
     */
    public void setModified() {
    	modifiedFlag = true;
    	
    	logger.debug("Method executed [" + id + " ]");
    }
    
    /**
     * clears the flag that marks the element as modified
     */
    public void clearModified() {
    	modifiedFlag = false;
    	
    	logger.debug("Method executed [" + id + " ]");
    }
    
    /**
     * returns the value object at a given index
     * @param anIndex
     *        index of a value object
     * @return value object at the index
     */
    public abstract Object getValue(int anIndex);
    
    /**
     * sets the list that the element belongs to
     * @param aList
     *        list, that the element belongs to
     */
    public void setList (ElementListBase aList) {
    	list = aList;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ElementBase) {
			ElementBase otherElement = (ElementBase) obj;
			if (id != -1 && otherElement.id != -1) {
				result = (id == otherElement.id);
			}
		}
		return result;
	}

    
    /**
     * sets the value object at a given index
     * @param anIndex
     *        index of a value object
     * @param anObject
     *        new value object to set
     */
    public abstract void setValue(int anIndex, Object anObject);
}
