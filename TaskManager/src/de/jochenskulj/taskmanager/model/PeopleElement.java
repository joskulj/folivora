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
 * Element class for people
 */
public class PeopleElement extends ElementBase {

	private String firstname = "";
	private String lastname = "";
	private String initials = "";
	private String notes = "";
	
	/**
	 * creates an instance
	 */
	public PeopleElement() {
		firstname = "";
		lastname = "";
		initials = "";
		notes = "";
	}

	/**
	 * returns the firstname
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * returns the lastname
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * returns the initials
	 * @return initials
	 */
	public String getInitials() {
		return initials;
	}
	
	/**
	 * returns the notes
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

    /**
     * returns the label to be used in selection tables
     * @return label to be used in selection tables
     */
	@Override
    public String getSelectionLabel() {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(firstname);
    	buffer.append(" ");
    	buffer.append(lastname);
    	return buffer.toString();
    }

    /**
     * returns the list, that the element belongs to
     * @return list, that the element belongs to
     */
    @Override
    public Object getValue(int anIndex) {
        Object result = null;
        if (anIndex == 0) {
            result = firstname;
        }
        if (anIndex == 1) {
            result = lastname;
        }
        if (anIndex == 2) {
            result = initials;
        }
        if (anIndex == 3) {
        	result = notes;
        }
        return result;
    }

    /**
     * sets the firstname
     * @param aName
     *        firstname to set
     */
	public void setFirstname(String aName) {
		if (firstname != null && aName != null) {
			if (firstname.equals(aName) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		firstname = aName;
		updateIntials();
		fireChanged();
	}
	
	/**
	 * sets the lastname
	 * @param aName
	 *        lastname to set
	 */
	public void setLastname(String aName) {
		if (lastname != null && aName != null) {
			if (lastname.equals(aName) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		lastname = aName;
		updateIntials();
		fireChanged();
	}
	
	/**
	 * sets the initials
	 * @param anInitials
	 *        initials to set
	 */
	public void setInitials(String anInitials) {
		if (initials != null && anInitials != null) {
			if (initials.equals(initials) == false) {
				setModified();
			}
		} else {
			setModified();
		}
		initials = anInitials;
		fireChanged();
	}
	
	
	/**
	 * sets notes
	 * @param aNote
	 *        notes to set
	 */
	public void setNotes(String aNote) {
		notes = aNote;
		fireChanged();
	}
	
    /**
     * sets the value object at a given index
     * @param anIndex
     *        index of a value object
     * @param anObject
     *        new value object to set
     */
    @Override
    public void setValue(int anIndex, Object anObject) {
        if ((anIndex == 0) && (anObject instanceof String)) {
            setFirstname((String) anObject);
        }
        if ((anIndex == 1) && (anObject instanceof String)) {
            setLastname((String) anObject);
        }
        if ((anIndex == 2) && (anObject instanceof String)) {
            setInitials((String) anObject);
        }
        if ((anIndex == 3) && (anObject instanceof String)) {
            setNotes((String) anObject);
        }
    }

    /**
     * checks to set initials derived from firstname and lastname
     */
    private void updateIntials() {
    	if (initials.length() == 0) {
    		if (firstname.length() > 0 && lastname.length() > 0) {
    			initials = firstname.substring(0, 0) + lastname.substring(0, 0);
    			initials = initials.toUpperCase();
    		}
    	}
    }
}
