package com.team.app.backend.dto;

public class QuizCategoryDto {
	private String title;
	private String[] categories;
	private String dateFrom;
	private String dateTo;
	private String user;
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String[] getCategories() {
		return this.categories;
	}
	
	public String getDateFrom() {
		return this.dateFrom;
	}
	
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public String getDateTo() {
		return this.dateTo;
	}
	
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
}