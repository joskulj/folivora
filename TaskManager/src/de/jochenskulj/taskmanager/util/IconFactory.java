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
package de.jochenskulj.taskmanager.util;

import de.jochenskulj.taskmanager.Application;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * Class to create icons
 */
public class IconFactory {

	public static final String APPLICATION = "Folivora.png";
    public static final String CONTEXT = "Context.png";
    public static final String DELETE = "Delete.png";
    public static final String ELEMENTS = "Elements.png";
    public static final String FILTER = "Filter.png";
    public static final String NEW = "New.png";
    public static final String NEXT = "Next.png";
    public static final String MODE_DETAIL = "Mode-Detail.png";
    public static final String MODE_LIST = "Mode-List.png";
    public static final String PEOPLE = "People.png";
    public static final String PREVIOUS = "Previous.png";
    public static final String PROJECT = "Project.png";
    public static final String TASK = "Task.png";
    
    /**
     * creates an icon
     * @param aName
     *        name of the icon
     * @return icon
     */
    public static ImageIcon createIcon(String aName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Application.getDirectory());
        buffer.append(File.separator);
        buffer.append("icons");
        buffer.append(File.separator);
        buffer.append(aName);
        if (!aName.endsWith(".png")) {
            buffer.append(".png");
        }
        String fname = buffer.toString();
        File f = new File(fname);
        ImageIcon result = null;
        if (f.exists()) {
            result = new ImageIcon(fname);
        }
        return result;
    }
}
