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

import de.jochenskulj.taskmanager.ApplicationException;
import de.jochenskulj.taskmanager.component.ElementTreeCellRenderer;
import de.jochenskulj.taskmanager.model.ApplicationModel;
import de.jochenskulj.taskmanager.model.ApplicationModelListener;
import de.jochenskulj.taskmanager.model.ElementType;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Panel that contains the tree of element types
 */
@Deprecated
public class TreePanel extends JPanel implements ApplicationModelListener, TreeSelectionListener {
    
    private ApplicationModel applicationModel;
    private DefaultMutableTreeNode rootNode;
    private JTree tree;
    
    /**
     * creates an instance
     * @param aFrame
     *        application frame containing the panel
     */
    public TreePanel(ApplicationFrame aFrame) {
        super();
        applicationModel = aFrame.getApplication().getModel();
        applicationModel.addListener(this);
        rootNode = createTreeModel();
        tree = new JTree(rootNode);
        tree.setCellRenderer(new ElementTreeCellRenderer());
        tree.addTreeSelectionListener(this);
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * creates the tree model
     * @return root node of the tree model
     */
    private DefaultMutableTreeNode createTreeModel() {
        ElementType rootElementType = new ElementType(ElementType.ELEMENTS);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootElementType);
        for (ElementType type : applicationModel.getElementTypes()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(type);
            root.add(node);
        }
        return root;
    }

    /**
     * returns tree path that corresponds to a given Element Type
     * @param aType
     *        Element Type
     * @return corresponding tree path
     */
    public TreePath getTreePath(ElementType aType) {
        TreePath result = null;
        Enumeration<DefaultMutableTreeNode> e = rootNode.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            ElementType nodeType = (ElementType) node.getUserObject();
            if (nodeType == aType) {
                result = new TreePath(node.getPath());
                break;
            }
        }
        return result;
    }

    /**
     * update object after changes of the model
     */
    @Override
    public void update() {
        if (tree == null) {
            return;
        }
        if (tree.getSelectionPath() == null) {
            return;
        }
        boolean updateFlag = false;
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        ElementType selectedType = (ElementType) selectedNode.getUserObject();
        if (selectedType == null) {
            updateFlag = true;
        } else {
            ElementType modelType = applicationModel.getCurrentElementType();
            if (selectedType != modelType) {
                updateFlag = true;
            }
        }
        if (updateFlag) {
            tree.setSelectionPath(getTreePath(applicationModel.getCurrentElementType()));
        }
    }
    
    /**
     * handles selection event
     * @param e
     *        event to handle
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node != null) {
        	try {
	            ElementType type = (ElementType) node.getUserObject();
	            applicationModel.setCurrentElementType(type);
        	} catch (ApplicationException ex) {
        		
        	}
        }
    }
}
