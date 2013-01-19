package com.gtask.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gtask.core.Task;
import com.gtask.core.TaskList;
import com.gtask.core.TasksLists;
import com.gtask.services.Google;

public class Gtask {
	public static Logger log = Logger.getLogger(Gtask.class.getName());

	public static void main(String args[]) {
		// Set up a simple configuration that logs on the console.
		BasicConfigurator.configure();

		Google gTasks = new Google();
		TasksLists tasksLists = gTasks.run();
		for (TaskList tasksList : tasksLists.getList()) {
			log.info(tasksList.getId() + ":" + tasksList.getName());
			for (Task task : tasksList.getTasks()) {
				log.info(task.getOrder() + ") " + task.getId() + ": " + task.getTitle());
			}
		}
		log.info("Done");
	}
}