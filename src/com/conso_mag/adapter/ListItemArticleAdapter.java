package com.conso_mag.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import com.conso_mag.R;
import com.conso_mag.model.Article;
import com.conso_mag.model.DateTime;
import com.conso_mag.util.ImageLoader;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("serial")
public class ListItemArticleAdapter extends BaseAdapter implements Serializable {

	private ArrayList<Article> listData;
	private transient LayoutInflater layoutInflater;
	transient ImageLoader imageLoader; 
	transient ViewHolder holder;

	//Initialize adapter
	public ListItemArticleAdapter(Context context,  ArrayList<Article> la) {
		this.listData = la;
		layoutInflater = LayoutInflater.from(context);
		imageLoader = new ImageLoader(context);
	}


	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.article_list_item, null);

			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.score = (TextView) convertView.findViewById(R.id.score);
			holder.titre = (TextView) convertView.findViewById(R.id.titre);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.author = (TextView) convertView.findViewById(R.id.author);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		Article art = listData.get(position);

		if(art != null){
			ImageView image = holder.image;

			if (!art.getImage().equals("")) imageLoader.DisplayImage(art.getImage(), image);
			
			holder.score.setText(art.getDisplayScore());

			holder.score.setVisibility(View.VISIBLE);
			if(art.getDisplayScore().equals("")) holder.score.setVisibility(View.INVISIBLE);
			String reducedTitle = art.getTitreReduit();
			holder.titre.setText( reducedTitle.toUpperCase(Locale.FRANCE) );
			DateTime date = new DateTime( art.getDisplayDate() );
			holder.date.setText("Le " + date.afficher() );
			holder.author.setText(art.getAuthor().getNickname());
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView score;
		TextView titre;
		TextView date;
		TextView author;
		FrameLayout color;
	}

}