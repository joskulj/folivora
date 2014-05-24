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
package de.jochenskulj.taskmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 * Class for Application Configuration 
 */
public class ApplicationProperty {

	private static Logger logger = Logger.getRootLogger();
	
	public static String ENV_HOME = "FOLIVORA_HOME";
	public static String USER_HOME = "USER_HOME";
	public static String DIRECTORY = "DIRECTORY";
	public static String CONFIG_FILE = "CONFIG_FILE";
	public static String DEBUG = "debug";
	public static String LOGFILE = "logfile";
	public static String DATEFORMAT = "dateformat";
	
	private Hashtable<String, String> properties = new Hashtable<String, String>();	
	
	/**
	 * creates an instance
	 */
	public ApplicationProperty() {
		initUserHome();
		initDirectory();
		initConfigPath();
		loadConfigFile();
		debug();
	}
	
	/**
	 * returns a property value
	 * @param aKey
	 *        property key
	 * @return property value
	 */
	public String get(String aKey) {
		return properties.get(aKey);
	}
	
	public boolean isTrue(String aKey) {
		boolean result = false;
		String value = properties.get(aKey);
		if (value != null) {
			result = value.trim().toLowerCase().equals("true");
		}
		return result;
	}

	/**
	 * creates a file path
	 * @param p1
	 *        first part of the path
	 * @param p2
	 *        second part of the path
	 * @param p3
	 *        second part of the path 
	 * @return file path
	 */
	private File createPath(String p1, String p2, String p3) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(p1);
		if (p2 != null) {
			if (!p1.endsWith(File.separator)) {
				buffer.append(File.separator);
			}
			buffer.append(p2);
		}
		if (p3 != null) {
			if (!p2.endsWith(File.separator)) {
				buffer.append(File.separator);
			}
			buffer.append(p3);
		}
		return new File(buffer.toString());
	}

	/**
	 * inits user's home path
	 */
	private void initUserHome() {
		String value = System.getProperty("user.home");
		properties.put(USER_HOME, value);
	}
	
	/**
	 * inits program directory
	 */
	private void initDirectory() {
		String value = System.getenv(ENV_HOME);
		if (value != null) {
			properties.put(ENV_HOME, value);
			properties.put(DEBUG, "false");
			File file = new File(value);
			if (file.exists()) {
				properties.put(DIRECTORY, value);
			} else {
				value = System.getProperty("user.dir");
				properties.put(DIRECTORY, value);
			}
		} else {
			value = System.getProperty("user.dir");
			properties.put(DIRECTORY, value);
		}
	}

	/**
	 * inits the config path
	 */
	private void initConfigPath() {
		String userdir = System.getProperty("user.home");
		File configPath = createPath(userdir, ".folivora.properties", null);
		if (configPath.exists()) {
			String path = configPath.getAbsolutePath();
			properties.put(CONFIG_FILE, path);
		} else {
			String directory = get(DIRECTORY);
			configPath = createPath(directory, "config", "folivora.properties");
			if (configPath.exists()) {
				String path = configPath.getAbsolutePath();
				properties.put(CONFIG_FILE, path);
			}
		}
	}
	
	/**
	 * loads the configuration file
	 */
	private void loadConfigFile() {
		String filename = properties.get(CONFIG_FILE);
		if (filename != null) {
			try {
				Properties propertyFile = new Properties();
				FileInputStream stream = new FileInputStream(filename);
				propertyFile.load(stream);
				for (Object keyObject : propertyFile.keySet()) {
					if (keyObject instanceof String) {
						String key = (String) keyObject;
						String value =  propertyFile.getProperty(key);
						properties.put(key, value);
					}
				}
			} catch (IOException e) {
				MessageFormat mf = 
						new MessageFormat("Unable to load config file: {0}");
				Object[] args = { filename };
				String message = mf.format(args);
				System.err.println(message);
			}
		}
	}
	
	/**
	 * debugs values
	 */
	private void debug() {
		if (isTrue(DEBUG)) {
			for (String key : properties.keySet()) {
				String value = properties.get(key);
				StringBuffer buffer = new StringBuffer();
				buffer.append("ApplicationProperty ");
				buffer.append(key);
				buffer.append(" = ");
				buffer.append(value);
				logger.debug(buffer.toString());
			}
		}
	}
}
