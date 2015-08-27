package com.conso_mag;

import com.conso_mag.model.Article;
import com.google.gson.Gson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Article mItem;
	
	private WebView web;
	private ProgressBar progress;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ArticleDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey("article")) {

			mItem = new Gson().fromJson(getArguments().getString("article"), Article.class);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);

		progress = (ProgressBar) rootView.findViewById(R.id.article_progressbar);
		progress.setMax(100);
		
		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			String css = ""
					+ "<link rel='stylesheet' href='http://www.conso-mag.com/wp-content/themes/valenti/library/css/style.css?ver=3.0'>"
					+ "<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Oswald%3A400%2C700%2C400italic%7COpen+Sans%3A400%2C700%2C400italic&#038;subset=greek%2Cgreek-ext&#038;ver=3.0' type='text/css' media='all' />"

					+ "<style>"
					+ ".embed-youtube, .fluid-width-video-wrapper{width:100%;position:relative;padding:0;}.fluid-width-video-wrapper iframe,.fluid-width-video-wrapper object,.fluid-width-video-wrapper embed {position:absolute;top:0;left:0;width:100%;height:100%;}"
					+ "</style>"

					+ "<style>"
					+ ".cb-overlay-stars .fa-star, #cb-vote .fa-star, #cb-to-top .fa-long-arrow-up, .cb-review-box .cb-score-box, .bbp-submit-wrapper button, .bbp-submit-wrapper button:visited, .buddypress .cb-cat-header #cb-cat-title a,  .buddypress .cb-cat-header #cb-cat-title a:visited, .woocommerce .star-rating:before, .woocommerce-page .star-rating:before, .woocommerce .star-rating span, .woocommerce-page .star-rating span {color:#eb9812;}"
					+ "#cb-search-modal .cb-header, .cb-join-modal .cb-header, .lwa .cb-header, .cb-review-box .cb-score-box, .bbp-submit-wrapper button, #buddypress button:hover, #buddypress a.button:hover, #buddypress a.button:focus, #buddypress input[type=submit]:hover, #buddypress input[type=button]:hover, #buddypress input[type=reset]:hover, #buddypress ul.button-nav li a:hover, #buddypress ul.button-nav li.current a, #buddypress div.generic-button a:hover, #buddypress .comment-reply-link:hover, #buddypress .activity-list li.load-more:hover, #buddypress #groups-list .generic-button a:hover {border-color: #eb9812;}"
					+ ".cb-sidebar-widget .cb-sidebar-widget-title, .cb-multi-widget .tabbernav .tabberactive, .cb-author-page .cb-author-details .cb-meta .cb-author-page-contact, .cb-about-page .cb-author-line .cb-author-details .cb-meta .cb-author-page-contact, .cb-page-header, .cb-404-header, .cb-cat-header, #cb-footer #cb-widgets .cb-footer-widget-title span, #wp-calendar caption, .cb-tabs ul .current, #bbpress-forums li.bbp-header, #buddypress #members-list .cb-member-list-box .item .item-title, #buddypress div.item-list-tabs ul li.selected, #buddypress div.item-list-tabs ul li.current, #buddypress .item-list-tabs ul li:hover, .woocommerce div.product .woocommerce-tabs ul.tabs li.active {border-bottom-color: #eb9812 ;}"
					+ "#cb-main-menu .current-post-ancestor, #cb-main-menu .current-menu-item, #cb-main-menu .current-menu-ancestor, #cb-main-menu .current-post-parent, #cb-main-menu .current-menu-parent, #cb-main-menu .current_page_item, #cb-main-menu .current-page-ancestor, #cb-main-menu .current-category-ancestor, .cb-review-box .cb-bar .cb-overlay span, #cb-accent-color, .cb-highlight, #buddypress button:hover, #buddypress a.button:hover, #buddypress a.button:focus, #buddypress input[type=submit]:hover, #buddypress input[type=button]:hover, #buddypress input[type=reset]:hover, #buddypress ul.button-nav li a:hover, #buddypress ul.button-nav li.current a, #buddypress div.generic-button a:hover, #buddypress .comment-reply-link:hover, #buddypress .activity-list li.load-more:hover, #buddypress #groups-list .generic-button a:hover {background-color: #eb9812;}"
					+ "body, #respond { font-family: 'Open Sans', sans-serif; }"
					+ "h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6, #cb-nav-bar #cb-main-menu ul li > a, .cb-breaking-news span, .cb-grid-4 h2 a, .cb-grid-5 h2 a, .cb-grid-6 h2 a, .cb-author-posts-count, .cb-author-title, .cb-author-position, .search  .s, .cb-review-box .cb-bar, .cb-review-box .cb-score-box, .cb-review-box .cb-title, #cb-review-title, .cb-title-subtle, #cb-top-menu a, .tabbernav, .cb-byline, #cb-next-link a, #cb-previous-link a, .cb-review-ext-box .cb-score, .tipper-positioner, .cb-caption, .cb-button, #wp-calendar caption, .forum-titles, .bbp-submit-wrapper button, #bbpress-forums li.bbp-header, #bbpress-forums fieldset.bbp-form .bbp-the-content-wrapper input, #bbpress-forums .bbp-forum-title, #bbpress-forums .bbp-topic-permalink, .widget_display_stats dl dt, .cb-lwa-profile .cb-block, #buddypress #members-list .cb-member-list-box .item .item-title, #buddypress div.item-list-tabs ul li, #buddypress .activity-list li.load-more, #buddypress a.activity-time-since, #buddypress ul#groups-list li div.meta, .widget.buddypress div.item-options, .cb-activity-stream #buddypress .activity-header .time-since, .cb-font-header, .woocommerce table.shop_table th, .woocommerce-page table.shop_table th, .cb-infinite-scroll a, .cb-no-more-posts { font-family:'Oswald', sans-serif; }"
					+ "</style>"

					+ "<style>"
					+ "body{"
					+ "width: 100%;"
					+ "background-color: #EEEEEE;"
					+ "}"
					+ ".cb-review-box {"
					+ "background-color: #DDDDDD;"
					+ "}"
					+ ".cb-score-box{"
					+ "color: #eb9812;"
					+ "border-color: #eb9812;"
					+ "}"
					+ "</style>"

					+ "<style>"
					+ "img{ width: 100% !important; height: auto; }"
					+ ".embed-youtube {"
					+ "position: relative;"
					+ "padding-bottom: 56.25%;"
					+ "padding-top: 30px; height: 0; overflow: hidden;"
					+ "}"
					+ ".embed-youtube iframe, .embed-youtube object, .embed-youtube embed {"
					+ "position: absolute;"
					+ "top: 0;"
					+ "left: 0;"
					+ "width: 100%;"
					+ "height: 100%;"
					+ "}"
					+ "</style>"

					+ "<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js'></script>";

			String js = "<script type='text/javascript' src='http://www.conso-mag.com/wp-content/themes/valenti/library/js/cb-scripts.js?ver=3.0'></script>";

			String data = ""
					+ "<!DOCTYPE html>"
					+ "<html>"
					+ "<head>"+css+"</head>"
					+ "<body>"
					+ mItem.getContent()
					+ js
					+ "</body>"
					+ "</html>";

			web = ((WebView) rootView.findViewById(R.id.article_detail));
			WebSettings settings = web.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setDefaultTextEncodingName("utf-8");
			settings.setPluginState(WebSettings.PluginState.ON);
			settings.setLoadWithOverviewMode(true);
			web.setWebChromeClient(new MyWebViewClient());
			web.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
		}

		return rootView;
	}
	
	@Override
	public void onPause() {
	    web.onPause();
	    super.onPause();
	}
	
	private class MyWebViewClient extends WebChromeClient {	
		@Override
		public void onProgressChanged(WebView view, int newProgress) {			
			ArticleDetailFragment.this.setValue(newProgress);
			super.onProgressChanged(view, newProgress);
		}
	}
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);
        if(progress == 100) {
        	this.progress.setVisibility(ProgressBar.GONE);
        }else{
        	this.progress.setVisibility(ProgressBar.VISIBLE);
        }
	}
}
