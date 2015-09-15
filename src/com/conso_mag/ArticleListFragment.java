package com.conso_mag;

import java.util.ArrayList;

import com.conso_mag.adapter.ListItemArticleAdapter;
import com.conso_mag.model.Article;
import com.conso_mag.util.JSONLoader;
import com.conso_mag.util.JSONParser;
import com.conso_mag.util.JSONUser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

/**
 * A list fragment representing a list of Articles. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ArticleDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ArticleListFragment extends ListFragment implements JSONUser, OnScrollListener {

	int numCateg = 0;
	int lastArticle = -1;
	JSONLoader jloader;
	ArrayList<Article> la;
	ListView lv1;

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private static final String STATE_ARTICLE_LIST = "article_list";
	private static final String STATE_LISTVIEW_ADAPTER = "listview_adapter";
	private static final String STATE_LAST_ARTICLE = "last_article";
	private static final String STATE_JLOADER = "jloader";


	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;


	public ArticleListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.article_list, container, false);
		return view;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// Restore the previously serialized activated item position.
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) 
				setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));

			if (savedInstanceState.containsKey(STATE_ARTICLE_LIST)){
				la = (ArrayList<Article>) savedInstanceState.getSerializable(STATE_ARTICLE_LIST);
				load("");
			}
			
			if (savedInstanceState.containsKey(STATE_LAST_ARTICLE)){
				lastArticle = savedInstanceState.getInt(STATE_LAST_ARTICLE);
			}

			if (savedInstanceState.containsKey(STATE_LISTVIEW_ADAPTER)){
				lv1 = (ListView) view;
				if ( lv1 != null){
					lv1.setAdapter(new ListItemArticleAdapter(getActivity(), la));

					lv1.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							Article art = (Article) lv1.getItemAtPosition(position);
							((ArticleListActivity)getActivity()).openArticle(art);
						}
					});
				}
				lv1.setOnScrollListener(this);
			}

			if (savedInstanceState.containsKey(STATE_JLOADER)){
				jloader = (JSONLoader) savedInstanceState.getSerializable(STATE_JLOADER);
				jloader.setFragment(this);
			}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
		
		outState.putInt(STATE_LAST_ARTICLE, lastArticle);

		ListItemArticleAdapter liaa = (ListItemArticleAdapter) lv1.getAdapter();
		outState.putSerializable(STATE_LISTVIEW_ADAPTER, liaa);

		outState.putSerializable(STATE_ARTICLE_LIST, la);

		outState.putSerializable(STATE_JLOADER, jloader);
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	
	@Override
	public void onScroll(AbsListView lw, final int firstVisibleItem,
	                 final int visibleItemCount, final int totalItemCount) {
		if ( lw.getLastVisiblePosition() == lastArticle-1){
			jloader = new JSONLoader(jloader, lastArticle+1, 5);
			jloader.execute("");
			lastArticle += 5;
		}
	}

	public void getData(){
		if(jloader != null) jloader.stop();
		
		la = new ArrayList<Article>();
		lv1 = (ListView) getActivity().findViewById(R.id.article_list);
		if ( lv1 != null){
			lv1.setAdapter(new ListItemArticleAdapter(getActivity(), la));

			lv1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Article art = (Article) lv1.getItemAtPosition(position);
					((ArticleListActivity)getActivity()).openArticle(art);
				}
			});
			lv1.setOnScrollListener(this);
		}

		switch(numCateg){
		case 1:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=cine-series-articles&status=publish");
			break;
		case 2:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=conso&status=publish");
			break;
		case 3:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=culture&status=publish");
			break;
		case 4:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=jeuxjouets&status=publish");
			break;
		case 5:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=poker&status=publish");
			break;
		case 6:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=sport&status=publish");
			break;
		case 7:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=tech&status=publish");
			break;
		case 8:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_category_posts&slug=web&status=publish");
			break;
		default:
			jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_recent_posts&status=publish");
		}
		
		lastArticle = 15;

		if ( jloader != null)
			jloader.execute("");
	}

	public void load(String json) {
		if (!json.equals("")){
			la.addAll(JSONParser.parseListArticle(json));
			ListItemArticleAdapter liaa = (ListItemArticleAdapter) lv1.getAdapter();
			liaa.notifyDataSetChanged();
		}
	}

	public void setCategorie(int categ) {
		numCateg = categ;
	}

	public void changeCategorie(int categ) {
		numCateg = categ;
		getData();
	}

	public void search() {
		if(jloader != null) jloader.stop();
		
		la = new ArrayList<Article>();
		lv1 = (ListView) getActivity().findViewById(R.id.article_list);
		if ( lv1 != null){
			lv1.setAdapter(new ListItemArticleAdapter(getActivity(), la));

			lv1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Article art = (Article) lv1.getItemAtPosition(position);
					((ArticleListActivity)getActivity()).openArticle(art);
				}
			});
		}
		
		EditText editText = (EditText)(getActivity().findViewById(R.id.search));
		String string = editText.getText().toString();
		
		if (string.length() < 2) return;
		
		((ArticleListActivity)getActivity()).setTitle(string);
		jloader = new JSONLoader(this, "http://www.conso-mag.com/?json=get_search_results&count=10&status=publish&search="+string);
		jloader.execute("");
		((ArticleListActivity)getActivity()).closeNavigation();
	}
}