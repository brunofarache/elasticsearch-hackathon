package com.liferay.elasticsearch;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ListActivity
	implements SearchView.OnQueryTextListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		UserListAdapter adapter = new UserListAdapter(
			this, android.R.layout.simple_list_item_1,
			new ArrayList<User>());

		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		SearchManager searchManager = (SearchManager)getSystemService(
			Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView)menu.findItem(
			R.id.search).getActionView();

		searchView.setOnQueryTextListener(this);

		searchView.setSearchableInfo(
			searchManager.getSearchableInfo(getComponentName()));

		searchView.setIconifiedByDefault(false);

		return true;
	}

	protected void search(String query) throws Exception {
		UserListAdapter adapter = (UserListAdapter)getListAdapter();

		new SearchAsyncTask(adapter, query).execute();
	}

	protected ArrayList<JSONObject> getUsers() {
		ArrayList<JSONObject> users = new ArrayList<JSONObject>();

		try {
			JSONObject bruno = new JSONObject();
			bruno.put("name", "Bruno Farache");

			JSONObject evan = new JSONObject();
			evan.put("name", "Evan Thibodeau");

			JSONObject jon = new JSONObject();
			jon.put("name", "Jonathan Lee");

			JSONObject miguel = new JSONObject();
			miguel.put("name", "Miguel Angelo");

			users.add(bruno);
			users.add(evan);
			users.add(jon);
			users.add(miguel);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		try {
			System.out.println(query);
			search(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}