package com.example.pcpartchooser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcpartchooser.Data.DataProvider;
import com.example.pcpartchooser.Data.PcPart;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    DataProvider dataProvider;
    ArrayList<PcPart> searchData;
    LinearLayoutManager listItemsLayoutManager;
    ListAdapter listAdapter;
    RecyclerView view;
    TextView numSearchResultsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataProvider = DataProvider.getInstance(this);

        //Get intent data from search query
        Intent searchIntent = getIntent();
        String searchQuery = searchIntent.getStringExtra("searchQuery");
        //Set the action bar, including title and logo
        setTitle(" " + searchQuery);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Search the data for the user's query
        searchData = dataProvider.getSearchResult(searchQuery);

        //Check whether there are no results
        if (searchData.size() == 0) {
            setContentView(R.layout.searchable_no_results); //If so, set view to be no results screen
        }
        else {
            //If there are results, set the view to be RecyclerView xml
            setContentView(R.layout.activity_search);

            //Set TextView displaying the number of search results
            numSearchResultsView = (TextView) findViewById(R.id.search_results);
            numSearchResultsView.setText("Found " + searchData.size() + " results");

            //Set recycler view displaying the matching items
            view = (RecyclerView) findViewById(R.id.pcparts_search_list);
            listAdapter = new ListAdapter(searchData);
            view.setAdapter(listAdapter);
            listItemsLayoutManager = new LinearLayoutManager(this);
            view.setLayoutManager(listItemsLayoutManager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Check whether the user has submitted their search
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();

                //Create intent for SearchActivity containing the search query
                Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
                searchIntent.putExtra("searchQuery", query);
                startActivity(searchIntent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }
}
