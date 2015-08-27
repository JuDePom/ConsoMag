package com.conso_mag.model;

import android.content.Context;

import java.io.Serializable;

import com.conso_mag.R;

@SuppressWarnings("serial")
public class Categorie implements Serializable{

	private String name;

	public void setName(String name) {

		this.name = name;

	}

	public String getName(){
		return name;
	}
	
	public static String getName(Context ctxt, int categ){
		String[] navMenuTitles = ctxt.getResources().getStringArray(R.array.categories);
		return navMenuTitles[categ];
	}
}
