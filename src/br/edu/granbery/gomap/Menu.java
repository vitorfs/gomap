package br.edu.granbery.gomap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {
	
	private String menu[] = { "Player vs. Android"
							, "Player vs. Player"
							, "About" };
	
	private String classes[] = { "VersusAndroid"
							   , "Options"
							   , "About" };
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, menu));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			Class c = Class.forName("br.edu.granbery.gomap." + classes[position]);
			Intent i = new Intent(Menu.this, c);
			startActivity(i);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
