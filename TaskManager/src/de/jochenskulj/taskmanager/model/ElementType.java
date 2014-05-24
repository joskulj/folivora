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

/**
 * Class to represent the different element type
 */
public class ElementType {

    private String label;
    private boolean detailFlag;
    private boolean filterFlag;

    public static final String ELEMENTS = "Elements";
    public static final String TASK = "Task";
    public static final String PROJECT = "Project";
    public static final String PEOPLE = "People";
    public static final String CONTEXT = "Context";

    /**
     * creates an instance
     * @param aLabel
     *        label of the element type
     */
    public ElementType(String aLabel) {
        label = aLabel;
        detailFlag = false;
        filterFlag = false;
    }
    
    public boolean equals(Object object) {
        return label.equals(object);
    }

    /**
     * returns the flag if a detail panel exists
     * @return flag if a detail panel exists
     */
    public boolean getDetailFlag() {
        return detailFlag;
    }
    
    /**
     * returns the flag if a filter panel exists
     * @return flag if a filter panel exists
     */
    public boolean getFilterFlag() {
        return filterFlag;
    }

    /**
     * returns the label
     * @return label
     */
    public String getLabel() {
        return label;
    }
    
    public int hashCode() {
        int hash = label.hashCode();
        return hash;
    }

    /**
     * sets the flag if a detail panel exists
     * @param aFlag
     *        flag if a detail panel exists
     */
    public void setDetailFlag(boolean aFlag) {
        detailFlag = aFlag;
    }

    /**
     * sets the flag if a filter panel exists
     * @param aFlag
     *        flag if a filter panel exists
     */
    public void setFilterFlag(boolean aFlag) {
        filterFlag = aFlag;
    }

    public String toString() {
        return label.toString();
    }
}
