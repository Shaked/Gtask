package com.gtask.main;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gtask.core.Task;
import com.gtask.core.TaskList;
import com.gtask.core.TasksLists;
import com.gtask.services.Google;
import com.gtask.services.Service;

public class Gtask { 
	public static final Integer SERVICE_GOOGLE = 1;
	
	public static Logger log = Logger.getLogger(Gtask.class.getName());

/*	public static void main(String args[]) {
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
	}*/
	
	public ArrayList<TasksLists> getTasksLists(){
		//read config 
		ArrayList<Integer> servicesIds = new ArrayList<Integer>();
		servicesIds.add(new Integer(Gtask.SERVICE_GOOGLE));
		
		ArrayList<TasksLists> arrTasksLists = new ArrayList<TasksLists>();
		for (Integer serviceId:servicesIds){
			try { 
				Service service = this.createService(serviceId);
				TasksLists tasksLists = service.run(); 
				arrTasksLists.add(tasksLists);
			} catch(Exception e){
				//TODO 
			}
		}
		return arrTasksLists;
	}
	
	private Service createService(Integer serviceId) throws Exception{
		switch(serviceId){
			case 1: 
				return new Google(); 
			default: 
				throw new Exception("No such service");
		} 
	}
}