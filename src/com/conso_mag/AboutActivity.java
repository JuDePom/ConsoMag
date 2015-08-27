package com.conso_mag;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity{
	private AdView mAdView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		/*
		mAdView = (AdView) findViewById(R.id.pub_about);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("0123456789ABCDEF")
				.build();
		mAdView.loadAd(adRequest);
		*/
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
