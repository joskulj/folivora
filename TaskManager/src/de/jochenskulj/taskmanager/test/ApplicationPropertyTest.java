package de.jochenskulj.taskmanager.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import de.jochenskulj.taskmanager.ApplicationProperty;

public class ApplicationPropertyTest {

	
	@Test
	public void testInstance() {
		ApplicationProperty property = new ApplicationProperty();
		assertNotNull(property);
		String value = property.get(ApplicationProperty.USER_HOME);
		assertNotNull(value);
		value = property.get(ApplicationProperty.DIRECTORY);
		assertNotNull(value);
		value = property.get(ApplicationProperty.CONFIG_FILE);
		assertNotNull(value);
	}
	
}
