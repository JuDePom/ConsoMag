package com.conso_mag;

import java.util.ArrayList;

import com.conso_mag.adapter.NavDrawerListAdapter;
import com.conso_mag.model.Article;
import com.conso_mag.model.Categorie;
import com.conso_mag.model.NavDrawerItem;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleListActivity extends AppCompatActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private final String STATE_TITLE = "title";
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);

		if (findViewById(R.id.article_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.article_list))
			.setActivateOnItemClick(true);
		}

		mTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.categories);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.categories_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navigation_listview);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home

		for(int i = 0; i < navMenuTitles.length; i++)
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));         

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();

				EditText editText = (EditText)(findViewById(R.id.search));
				editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							search(null);
							return true;
						}
						return false;
					}
				});
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState != null) {
			mTitle = savedInstanceState.getString(STATE_TITLE);
			restoreActionBar();
		} else {
			displayView(0);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(STATE_TITLE, mTitle.toString());
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			displayView(position);
			view.setSelected(true);
			setTitle(Categorie.getName(getApplicationContext(), position));
			mDrawerLayout.closeDrawers();
			closeKeyboard();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		restoreActionBar();
		closeKeyboard();
		return super.onCreateOptionsMenu(menu);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		closeKeyboard();
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_about:
			Intent detailIntent = new Intent(this, AboutActivity.class);
			startActivity(detailIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinear);
		//dqmenu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		ArticleListFragment alf = ((ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.article_list));
		if (alf != null) {
			alf.changeCategorie(position);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		restoreActionBar();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void openArticle(Article art) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString("article", new Gson().toJson(art));
			ArticleDetailFragment fragment = new ArticleDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
			setTitle(art.getTitreReduit());
		} else {
			Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
			detailIntent.putExtra("article", new Gson().toJson(art));
			startActivity(detailIntent);
		}
	}

	public void closeKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(
				INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	public void search(View view){
		closeKeyboard();
		ArticleListFragment alf = ((ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.article_list));
		if(alf != null){
			alf.search();
		}
		restoreActionBar();
	}

	public void closeNavigation() {
		mDrawerLayout.closeDrawers();
	}
}
