package com.example.calendarserver.user;

public class UserDto {
	private String login;
	private String password;
	private String email;

	public UserDto() {

	}
	
	public UserDto(String login, String password, String email) {
		super();
		this.login = login;
		this.password = password;
		this.email = email;
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	
	
}
