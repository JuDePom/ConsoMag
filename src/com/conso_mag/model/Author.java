package com.conso_mag.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Author implements Serializable {
	String name;
	String nickname;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}


}
