package com.gtask.ui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.html.ListView;

import com.gtask.core.Task;
import com.gtask.core.TaskList;
import com.gtask.core.TasksLists;
import com.gtask.main.Gtask;
import com.gtask.ui.Views.GListView;

public class ListController {

	public static void main(String args[]) {
		// Create some items to add to the list
		Vector<String> listData = new Vector<String>();
		// Create an instance of the test application
		Gtask gtask = new Gtask();
		ArrayList<TasksLists> arrTasksLists = gtask.getTasksLists();
		for (TasksLists tasksLists : arrTasksLists) {
			for (TaskList tasksList : tasksLists.getList()) {
				listData.add(new String(tasksList.getId() + ":"
						+ tasksList.getName()));
				for (Task task : tasksList.getTasks()) {
					listData.add(new String(task.getOrder() + ") "
							+ task.getId() + ": " + task.getTitle()));
				}
			}
		}
		GListView mainFrame = new GListView(listData);
		mainFrame.setSize(300,300); 
		mainFrame.setVisible(true);
	}

}