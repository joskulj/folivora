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

import de.jochenskulj.taskmanager.Application;
import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.button.DeleteButton;
import de.jochenskulj.taskmanager.button.FilterButton;
import de.jochenskulj.taskmanager.button.ModeButton;
import de.jochenskulj.taskmanager.button.NewButton;
import de.jochenskulj.taskmanager.button.NextButton;
import de.jochenskulj.taskmanager.button.PreviousButton;
import de.jochenskulj.taskmanager.component.GridBagPanel;
import de.jochenskulj.taskmanager.component.StatusLabel;
import de.jochenskulj.taskmanager.component.TypeChooserPanel;
import de.jochenskulj.taskmanager.filter.FilterPanelBase;
import de.jochenskulj.taskmanager.filter.TaskFilterPanel;
import de.jochenskulj.taskmanager.menu.ApplicationMenuBar;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementListBase;
import de.jochenskulj.taskmanager.model.ElementType;
import de.jochenskulj.taskmanager.util.IconFactory;
import de.jochenskulj.taskmanager.util.YesNoOption;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.MessageFormat;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

/**
 * Main frame class of the application
 */
public class ApplicationFrame implements ApplicationModelListener {

	private static Logger logger = Logger.getRootLogger();
	
    private Application application;
    private JFrame frame;
    private ContentPanelBase contentPanel;
    private ContentPanelBase defaultPanel;
    private DetailPanelBase currentDetailPanel;
    private FilterPanelBase currentFilterPanel;
    private Hashtable<String, ContentPanelBase> listPanels;
    private Hashtable<String, DetailPanelBase> detailPanels;
    private Hashtable<String, FilterPanelBase> filterPanels;
    private FilterButton filterButton;
    private ModeButton modeButton;
    private ApplicationMenuBar menuBar;
    private StatusLabel statusLabel;
    
    /**
     * creates an instance
     * @param anApplication
     *        application instance
     */
    public ApplicationFrame(Application anApplication) {
        application = anApplication;
        application.getModel().addListener(this);
        frame = new JFrame();
        init(frame);
        menuBar = new ApplicationMenuBar(application);
        frame.setJMenuBar(menuBar.getMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        updateTitle();
        frame.setIconImage(IconFactory.createIcon(IconFactory.APPLICATION).getImage());
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent anEvent) {
                 closeFrame(anEvent);
            }
        });

        frame.setVisible(true);
    }
    
    /**
     * closes the application frame
     */
    public void close() {
    	
    	logger.debug("Method entered");
    	
    	saveCurrentRow();
    	try {
	    	boolean exitFlag = true;
	    	ApplicationModel model = getApplication().getModel();
	    	if (model.hasFilename()) {
	    		model.save();
	    		
	    		logger.debug("Model saved");
	    		
	    		exitFlag = true;
	    	} else {
	    		if (model.isModified()) {
	    			
	    			logger.debug("Model is modified");
	    			
		    		YesNoOption option = new YesNoOption(frame);
		    		option.setTitle("Exit Application");
		    		option.setQuestion(
		    				"Exit Appication without saving current changes?");
		    		exitFlag = (option.show() == YesNoOption.YES_OPTION);
	    		}
	    	}
	    	if (exitFlag) {
	    		
	    		logger.debug("Exit application");
	    		
	    		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    		frame.dispose();
	    	} else {

	    		logger.debug("Don't exit application");
	    		
	    		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    		
	    		logger.info("Application closed.");
	    		
	    	}
    	} catch (ApplicationException e) {
    		getApplication().getFrame().showException(e);
    	}
    	
    	logger.debug("ApplicationFrame.close() exited");
    	
    } 

    /**
     * returns the application instance
     * @return the application instance
     */
    public Application getApplication() {
        return application;
    }
    
    /**
     * returns the application's frame
     * @return application's frame
     */
    public JFrame getFrame() {
    	return frame;
    }
    
    /**
     * returns the current DetailPanel or <code>null</code> of a list panel is
     * active
     * @return <code>null</code>
     */
    public DetailPanelBase getCurrentDetailPanel() {
    	return currentDetailPanel;
    }
    
    /**
     * returns the StatusBar
     * @return StatusBar of the Application frame
     */
    public StatusLabel getStatusLabel() {
    	return statusLabel;
    }
    
    
    /**
     * removes the existing content panel an displays a new one
     * @param aPanel
     *        new content panel to display
     */
    public void setContentPanel(ContentPanelBase aPanel) {
    	
    	logger.debug("Method entered");
    	logger.debug("aPanel = " + aPanel.getClass().getName());
    	
    	// check, if the filter panel has to be changed
    	boolean changeFilter = false;
    	boolean filterActive = false;
    	FilterPanelBase newFilterPanel = null;
    	
    	ElementListBase panelList = 
    			application.getModel().getList(aPanel.getType());
    	logger.debug("panelList = " + panelList);
    	if (panelList != null) {
    		filterActive  = panelList.getFilterFlag();
    		if (filterActive == true) {
    			newFilterPanel = filterPanels.get(aPanel.getType().getLabel());
    			logger.debug("currentFilterPanel = " + currentFilterPanel);
    			logger.debug("newFilterPanel = " + newFilterPanel);
    			if (newFilterPanel != currentFilterPanel) {
    				changeFilter = true;
    			}
    		} else {
    			if (currentFilterPanel != null) {
    				changeFilter = true;
    				currentFilterPanel = null;
    			}
    		}
    	}
    	logger.debug("changeFilter = " + changeFilter);
    	logger.debug("filterActive = " + filterActive);
    	
    	if (aPanel == currentDetailPanel && changeFilter == false) {
    		// content panel already active; leave method
    		return;
    	}
        
    	// save the values of detail panels
    	if (currentDetailPanel != null) {
    		currentDetailPanel.saveCurrentRow();
    	}
        // remove the existing panel
    	contentPanel.removeAll();
    	
        GridBagConstraints c = null;
        int gridDeltaY = 0;
        
        // show filter panel
        if (filterActive == true) {
        	c = new GridBagConstraints();
        	c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            contentPanel.add(newFilterPanel, c);
            currentFilterPanel = newFilterPanel;
            gridDeltaY = 1;
        }

        // add the new panel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0 + gridDeltaY;
        c.weightx = 1;
        if (aPanel.getVerticalFill()) {
            c.fill = GridBagConstraints.BOTH;
            c.weighty = 1;
        } else {
        	c.fill = GridBagConstraints.HORIZONTAL;
        }
        contentPanel.add(aPanel, c);
        if (aPanel instanceof DetailPanelBase) {
        	((DetailPanelBase) aPanel).refreshCurrentElement();
        }
        if (aPanel.getVerticalFill() == false) {
            // insert dummy panel for top alignment of Detail panels
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1 + gridDeltaY;
            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            contentPanel.add(new JPanel(), c);
             ((DetailPanelBase) aPanel).focusFirstElement();
        }
        if (aPanel instanceof DetailPanelBase) {
        	currentDetailPanel = (DetailPanelBase) aPanel;
        } else {
        	currentDetailPanel = null;
        }
        aPanel.updateUI();
        
        if (currentFilterPanel != null) {
        	//currentFilterPanel.refreshPanel();
        	//currentFilterPanel.updateUI();
        }
        
    	logger.debug("Method exited");
    }
    
    /**
     * displays the frame maximized
     */
    public void show() {
        // setVisible(true);
        // 
    }
    
    /**
     * displays an Error Message for an Exception
     * @param anException
     *        exception to display
     */
    public void showException(ApplicationException anException) {
    	anException.printStackTrace();
    	JOptionPane.showMessageDialog(frame,
    		    anException.getMessage(),
    		    "Application Error",
    		    JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * update object after changes of the model
     */
    public void update() {
    	
    	logger.debug("Method entered");
    	
        ElementType modelType = application.getModel().getCurrentList().getType();
        if (modelType.getDetailFlag()) {
        	
        	logger.debug("Detail mode active");
        	
            modeButton.setVisible(true);
            ContentPanelBase panel = null;
            ElementListBase currentList = getApplication().getModel().getCurrentList();
            if (currentList != null) {
                if (currentList.getMode() == ElementListBase.DETAIL_MODE) {
                    panel = detailPanels.get(modelType.getLabel());
                } else {
                    panel = listPanels.get(modelType.getLabel());
                }
                if (modeButton != null) {
                	
                	logger.debug("mode: " +	currentList.getMode());

                	modeButton.setMode(currentList.getMode());  
                }
                setContentPanel(panel);    
            } else {
                panel = listPanels.get(modelType.getLabel());
            }
            if (panel == null) {
                panel = defaultPanel;
            }            
        } else {
        	
        	logger.debug("List mode active");

        	modeButton.setVisible(false);
            ContentPanelBase panel = listPanels.get(modelType.getLabel());
            if (panel == null) {
                panel = defaultPanel;
            }
            setContentPanel(panel);
        }
        if (modelType.getFilterFlag()) {
            filterButton.setVisible(true);
        } else {
            filterButton.setVisible(false);
        }
        DetailPanelBase detailPanel = getCurrentDetailPanel();
        if (detailPanel != null) {
        	detailPanel.refresh();
        }
        updateTitle();
    	
    	logger.debug("Method exited");
    	
    }

    /**
     * saves the current detail view
     */
    public void saveCurrentRow() {
    	
    	logger.debug("Method entered");
    	
    	DetailPanelBase detailPanel = getCurrentDetailPanel();
    	if (detailPanel != null) {
    		detailPanel.saveCurrentRow();
    	}

    	logger.debug("Method exited");
    
    }
    
    /**
     * initializes the components within the frame
     * @param aFrame
     *        frame to initialize
     */
    protected void init(JFrame aFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
                
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 10;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(new TypeChooserPanel(getApplication()), c);
        aFrame.add(panel);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 10;
        c.weighty = 10;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(initMainPanel(), c);
        aFrame.add(panel);
        // gridx
        
        /*
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new TreePanel(this));
        splitPane.setRightComponent(initMainPanel());
        splitPane.setDividerLocation(150);
        aFrame.add(splitPane);
        */
        initContentPanels();
    }
    
    /**
     * initializes the bottom button panel
     * @return top button bottom panel
     */
    protected JPanel initBottomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        statusLabel = new StatusLabel();
        panel.add(statusLabel, c);
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(new PreviousButton(this), c);
        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        statusLabel.setStatus("Test ok.");
        panel.add(new NextButton(this), c);
        return panel;
    }
    
    /**
     * initializes the panels to display the content
     */
    protected void initContentPanels() {
        listPanels = new Hashtable<String, ContentPanelBase>();
        detailPanels = new Hashtable<String, DetailPanelBase>();
        filterPanels = new Hashtable<String, FilterPanelBase>();
        for (ElementType type : application.getModel().getElementTypes()) {
            String label = type.getLabel();
            ContentPanelBase listPanel = null;
            DetailPanelBase detailPanel = null;
            FilterPanelBase filterPanel = null;
            if (label.equals(ElementType.CONTEXT)) {
                listPanel = new ContextListPanel(this);
                detailPanel = new ContextDetailPanel(this);
            }
            if (label.equals(ElementType.PEOPLE)) {
                listPanel = new PeopleListPanel(this);
                detailPanel = new PeopleDetailPanel(this);
            }
            if (label.equals(ElementType.PROJECT)) {
                listPanel = new ProjectListPanel(this);
                detailPanel = new ProjectDetailPanel(this);
            }
            if (label.equals(ElementType.TASK)) {
                listPanel = new TaskListPanel(this);
                detailPanel = new TaskDetailPanel(this);
                filterPanel = new TaskFilterPanel(application.getModel());
            }
            listPanels.put(label, listPanel);
            detailPanels.put(label, detailPanel);
            if (filterPanel != null) {
            	filterPanels.put(label, filterPanel);
            }
        }
        defaultPanel = new ContentPanelBase();
        defaultPanel.setBackground(Color.LIGHT_GRAY);
    }
    
    /**
     * initializes the main panel on the right side
     * @return main panel
     */
    protected JPanel initMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(initTopPanel(), c);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        contentPanel = new GridBagPanel();
        contentPanel.setBackground(Color.DARK_GRAY);
        panel.add(contentPanel, c);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(initBottomPanel(), c);
        return panel;
    }
    
    /**
     * initializes the top button panel
     * @return top button panel
     */
    protected JPanel initTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(new NewButton(this), c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(new DeleteButton(this), c);
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panel.add(new JPanel(), c);
        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        filterButton = new FilterButton(this);
        panel.add(filterButton, c);
        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        modeButton = new ModeButton(this);
        panel.add(modeButton, c);
        return panel;
    }
    
    /**
     * updates the frame title
     */
    protected void updateTitle() {
    	
    	logger.debug("ApplicationFrame.updateTitle()");
    	
        ApplicationModel model = getApplication().getModel();
        if (model.hasFilename()) {
        	MessageFormat titleFormat = null;
        	if (model.isModified()) {
        		titleFormat = new MessageFormat("Folivora - {0} [modified]");
        	} else {
        		titleFormat = new MessageFormat("Folivora - {0}");	
        	}
        	File file = new File(model.getFilename());
        	String name = file.getName();
        	Object[] args = { name };
        	String title = titleFormat.format(args);
        	frame.setTitle(title);
        } else {
        	if (model.isModified()) {
        		frame.setTitle("Folivora [modified]");
        	} else {
        		frame.setTitle("Folivora");
        	}
        }
    }
    
    /**
     * handles the close event
     * @param anEvent
     *        close event to handle
     */
    protected void closeFrame(WindowEvent anEvent) {
    	getApplication().getFrame().close();
    }
}