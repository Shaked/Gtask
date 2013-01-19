package com.gtask.core;
/**
 * 
 * @author Shaked
 *
 */
public class Task {
	
	private String id;  
	private String title;
	private Integer order;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	} 
	
}
