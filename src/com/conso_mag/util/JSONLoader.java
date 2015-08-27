package com.conso_mag.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

@SuppressWarnings("serial")
public class JSONLoader extends AsyncTask<String, String, Void> implements Serializable {

	//private transient ProgressDialog progressDialog;
	transient InputStream inputStream;
	transient String json, url;
	transient Fragment fragment;
	transient boolean run = true;

	public JSONLoader(Fragment frag, String url) {
		/*progressDialog = new ProgressDialog(frag.getActivity());*/
		this.url = url;
		json = "";
		fragment = frag;
	}

	public String getJSON(){
		return json;
	}

	protected void onPreExecute() {
		/*progressDialog.setMessage("Chargement...");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
				JSONLoader.this.cancel(true);
			}
		});*/
	}

	@Override
	protected Void doInBackground(String... params) {
		for (int i = 1; i < 15 && run; i++){

			try{
				URL url_con = new URL(url+"&count=1&page="+i);
				HttpURLConnection connection = (HttpURLConnection)url_con.openConnection();
				connection.setRequestProperty("User-Agent", "");
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.connect();

				InputStream inputStream = connection.getInputStream();

				BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
				StringBuilder sBuilder = new StringBuilder();

				String line = null;
				while ((line = bReader.readLine()) != null) {
					sBuilder.append(HTMLEntities.unhtmlentities(line) + "\n");
				}

				inputStream.close();
				json = sBuilder.toString();

				final String fjson = json;

				if(run){
					fragment.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							((JSONUser)fragment).load(fjson);
						}
					});
				}

			} catch (IOException e) {
				// writing exception to log
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("StringBuilding & BufferedReader", "Error converting json " + e.toString());
			}
		}
		return null;

	}

	public void setFragment(Fragment fragment){
		this.fragment = fragment;
	}

	protected void onPostExecute(Void v) {
		if (fragment.isInLayout()){
			/*this.progressDialog.dismiss();*/
		}
	}

	public void stop() {
		run = false;
	}
}