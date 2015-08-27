package com.conso_mag.util;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.conso_mag.model.Article;
import com.conso_mag.model.Author;
import com.conso_mag.model.Categorie;

@SuppressWarnings("unused")
public class JSONParser {
	
	private static final String TAG_ARTICLE = "posts";
	private static final String TAG_ARTICLE_ID = "id";
	private static final String TAG_ARTICLE_TITLE = "title";
	private static final String TAG_ARTICLE_DATE = "date";
	private static final String TAG_ARTICLE_CONTENT = "content";
	private static final String TAG_ARTICLE_SHORT = "excerpt";
	
	
	private static final String TAG_AUTHOR = "author";
	private static final String TAG_AUTHOR_ID = "id";
	private static final String TAG_AUTHOR_NAME = "name";
	private static final String TAG_AUTHOR_NICKNAME = "nickname";
	private static final String TAG_AUTHOR_URL = "url";
	private static final String TAG_AUTHOR_DESCRITPION = "description";
	
	private static final String TAG_CATEGORIES = "categories";
	private static final String TAG_CATEGORIE_ID = "id";
	private static final String TAG_CATEGORIE_TITLE = "title";
	
	private static final String TAG_OTHER = "custom_fields";
	private static final String TAG_OTHER_FINAL_SCORE = "cb_final_score";
	private static final String TAG_IMAGES = "thumbnail_images";
	private static final String TAG_THUMBNAIL_IMAGE = "medium";
	
	private static final String TAG_ATTACHMENTS = "attatchments";
	private static final String TAG_IMAGES_BIS = "images";
	private static final String TAG_THUMBNAIL_IMAGE_BIS = "medium";
	
	private static final String TAG_IMAGE_URL = "url";
	
	
	public static ArrayList<Article> parseListArticle(String json){
		ArrayList<Article> lart = new ArrayList<Article>();
		try {
			
			JSONArray listArticle = new JSONArray(new JSONObject(json).getString(TAG_ARTICLE));
			for(int i = 0; i < listArticle.length(); i++){
				Article art = parseArticle(listArticle.getString(i));
				if (art != null) lart.add( art );
			}
			
		} catch (JSONException e) {
			Log.e("parseListArticle", e.toString());
		}
		return lart;
	}
	
	
	public static Article parseArticle(String json){
		try {

			Article art = new Article();
			
			JSONObject article = new JSONObject(json);
			
			art.setId(article.getInt(TAG_ARTICLE_ID));
			art.setTitle(article.getString(TAG_ARTICLE_TITLE));
			art.setDate(article.getString(TAG_ARTICLE_DATE));
			art.setContent(article.getString(TAG_ARTICLE_CONTENT));
			art.setShort(article.getString(TAG_ARTICLE_SHORT));

			JSONObject other = article.getJSONObject(TAG_OTHER);
			
			try{
			JSONArray score = other.getJSONArray(TAG_OTHER_FINAL_SCORE);
			art.setFinalScore(score.getInt(0));
			} catch (JSONException e){
				art.setFinalScore(0);
			}
			
			String imageurl = "http://www.conso-mag.com/wp-content/themes/valenti/library/images/thumbnail-360x240.png";
			
			try {
				JSONObject images = article.getJSONObject(TAG_IMAGES);
				JSONObject image = images.getJSONObject(TAG_THUMBNAIL_IMAGE);
				imageurl = image.getString(TAG_IMAGE_URL);
			} catch (JSONException je) {
				try {
					JSONArray attachments = article.getJSONArray(TAG_ATTACHMENTS);
					JSONObject images = attachments.getJSONObject(0).getJSONObject(TAG_IMAGES_BIS);
					JSONObject image = images.getJSONObject(TAG_THUMBNAIL_IMAGE_BIS);
					imageurl = image.getString(TAG_IMAGE_URL);
				} catch (JSONException je2) {
					
				}
			}
			art.setImage( unescapeJava(imageurl) );

			art.setAuthor( parseAuthor(article.getString(TAG_AUTHOR)));
			
			JSONArray categories = article.getJSONArray(TAG_CATEGORIES);
			for(int i = 0; i < categories.length(); i++){
				art.addCategorie( parseCategorie(categories.getString(i)) );
			}
			return art;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Author parseAuthor(String json) {
		try {
			Author aut = new Author();
		
			JSONObject author = new JSONObject(json);
			
			aut.setName(author.getString(TAG_AUTHOR_NAME));
			aut.setNickname(author.getString(TAG_AUTHOR_NICKNAME));
			
			return aut;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Categorie parseCategorie(String json){
		try {
			
			Categorie cat = new Categorie();
			
			JSONObject categorie = new JSONObject(json);
			
			cat.setName(categorie.getString(TAG_CATEGORIE_TITLE));
		
			return cat;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String unescapeJava(String escaped) {
	    if(escaped.indexOf("\\u")==-1)
	        return escaped;

	    String processed="";

	    int position=escaped.indexOf("\\u");
	    while(position!=-1) {
	        if(position!=0)
	            processed+=escaped.substring(0,position);
	        String token=escaped.substring(position+2,position+6);
	        escaped=escaped.substring(position+6);
	        processed+=(char)Integer.parseInt(token,16);
	        position=escaped.indexOf("\\u");
	    }
	    processed+=escaped;

	    return processed;
	}
}
