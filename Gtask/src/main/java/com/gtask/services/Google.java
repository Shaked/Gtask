package com.gtask.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.gtask.core.TasksLists;
import com.gtask.main.Gtask;

public class Google extends Service { 

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws IOException {
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY,
				Gtask.class.getResourceAsStream("/services/google.json"));
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret()
						.startsWith("Enter ")) {
			System.out
					.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=tasks "
							+ "into tasks-cmdline-sample/src/main/resources/client_secrets.json");
			System.exit(1);
		}
		// set up file credential store
		FileCredentialStore credentialStore = new FileCredentialStore(new File(
				System.getProperty("user.home"), ".credentials/tasks.json"),
				JSON_FACTORY);
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
				Collections.singleton(TasksScopes.TASKS)).setCredentialStore(
				credentialStore).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	} 

	@Override
	public TasksLists run() { 
		TasksLists tasksLists = new TasksLists();
		Tasks googleTasks = this.getServiceTasks();
		try {
			List<TaskList> googleTasksLists = googleTasks.tasklists().list().execute()
					.getItems();
			for (TaskList googleTasksList : googleTasksLists) { 
				List<Task> googleTasksInList = googleTasks.tasks().list(googleTasksList.getId()).execute().getItems(); 
				com.gtask.core.TaskList taskList = new com.gtask.core.TaskList();
				taskList.setName(googleTasksList.getTitle())
						.setId(googleTasksList.getId());
				
				int i = 0; 
				for (Task googleTask : googleTasksInList){
					com.gtask.core.Task task = new com.gtask.core.Task(); 
					task.setId(googleTask.getId());
					task.setOrder(i);
					task.setTitle(googleTask.getTitle());
					taskList.add(task);
					i++;
					
				}
				tasksLists.getList().add(taskList);
			}
			return tasksLists;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return Tasks
	 */
	private Tasks getServiceTasks() {
		try {
			// authorization
			Credential credential = authorize();
			// set up global Plus instance
			return new com.google.api.services.tasks.Tasks.Builder(
					HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName("Google-TasksAndroidSample/1.0")
					.build();
		} catch (IOException e) {
			return null;
		}
	}

}
