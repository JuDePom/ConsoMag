package com.conso_mag.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings({ "unused", "serial" })
public class Article implements Serializable{
	private int id;
	private String title;
	private String date;
	private String content;
	private String shortContent;
	private String image;
	private int score;
	
	private Author author;
	private ArrayList<Categorie> categories = new ArrayList<Categorie>();

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setShort(String shortContent) {
		this.shortContent = shortContent;
	}
	
	public void setFinalScore(int score) {
		this.score = score;
	}
	
	public void setImage(String string){
		this.image = string;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public void addCategorie(Categorie categorie) {
		if (categorie != null)
			categories.add(categorie);
	}

	public String toString(){
		return "Article : " + title;
	}
	
	

	public String getImage() {
		return image;
	}

	public String getDisplayScore() {
		if ( score == 0) return "";
		return "" + (float)score/10;
	}

	public String getTitreReduit() {
		return title;
	}

	public String getDisplayDate() {
		return date;
	}
	
	public String getContent(){
		return content;
	}
	
	public Author getAuthor(){
		return author;
	}
}
