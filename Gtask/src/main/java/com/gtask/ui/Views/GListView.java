package com.gtask.ui.Views;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GListView extends JFrame {

	public GListView(Vector<String> listData) {
		// Create a panel to hold all other components
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		this.getContentPane().add(topPanel);

		// Create a new listbox control
		JList listbox = new JList(listData);
		topPanel.add(listbox, BorderLayout.CENTER);
		JScrollPane jsp = new JScrollPane(topPanel);
		this.setAlwaysOnTop(true);
		this.add(jsp);
	}
}
