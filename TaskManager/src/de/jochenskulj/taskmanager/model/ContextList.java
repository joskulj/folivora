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

import org.apache.log4j.Logger;

/**
 * List for Context elements
 */
public class ContextList extends ElementListBase {

	private static Logger logger = Logger.getRootLogger();
	
    /**
     * creates an instance
     * @param aModel
     *        application model that the list belongs to
     */
    public ContextList(ApplicationModel aModel) {
        super(aModel, new ElementType(ElementType.CONTEXT));
        getType().setDetailFlag(true);
        addColumnName("Context");
    }

    /**
     * creates a new element in the list
     * @return the new element
     */
    public ElementBase createNewElement() {
    	
    	logger.debug("Method entered");
    	
    	setActive(false);
        ContextElement result = new ContextElement();
        StringBuffer buffer = new StringBuffer();
        buffer.append("New Context ");
        buffer.append(getRowCount() + 1);
        result.setContext(buffer.toString());
        addElement(result);
        setModeInternal(ElementListBase.DETAIL_MODE);
        setSelectedRow(getRowCount() - 1);
        setActive(true);
        
        logger.debug("Method exited");
        
        return result;
    }

    public Class<?> getColumnClass(int i) {
        Class result = null;
        if (i == 0) {
            result = String.class;
        }
        return result;
    }
}
