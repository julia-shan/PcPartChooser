package com.example.pcpartchooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pcpartchooser.Data.DataProvider;
import com.example.pcpartchooser.Data.PcPart;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class DetailsActivity extends AppCompatActivity {

    private ImageView partImageView;
    private TextView partNameTextView;
    private TextView partPriceTextView;
    private TextView partSpec1TextView;
    private TextView partSpec2TextView;
    private TextView partSpec3TextView;
    private TextView partSpec4TextView;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private PcPart thisPart;
    private ArrayList<Integer> partImages;
    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent thisIntent = getIntent();

        //Checking where the intent is from and passing in the part according to
        //whether it was from top picks or ListActivity. Sets the current item to
        //display to the item passed in through the intent
        if(thisIntent.getSerializableExtra(ListAdapter.LIST_ITEM_KEY) != null){
            thisPart = (PcPart) thisIntent.getSerializableExtra(ListAdapter.LIST_ITEM_KEY);

        } else if(thisIntent.getSerializableExtra(TopPicksAdapter.TOP_PICKS_KEY) != null){
            thisPart = (PcPart) thisIntent.getSerializableExtra(TopPicksAdapter.TOP_PICKS_KEY);
        }


        //Sets title in the action bar with spacing in between the logo and text and sets logo in action bar
        dataProvider = DataProvider.getInstance(this);
        PcPart thisPartActual = dataProvider.getSpecificPcPart(thisPart.getId());
        thisPartActual.incrementViews();
        setTitle("  "+ thisPart.getName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Initialise images list
        partImages = new ArrayList<>();

        //Fills an array with all the image ids for the item
        for(int i = 0; i < thisPart.getImageIDs().size(); i++) {
            String imageName = thisPart.getImageIDs().get(i);
            int imageID = getResources().getIdentifier(imageName, "drawable", getPackageName());
            partImages.add(imageID);
        }

        viewPager = (ViewPager) findViewById(R.id.details_viewPager);
        viewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, partImages);
        viewPager.setAdapter(viewPagerAdapter);

        //Set all the text views for the name, price and descriptions of the item
        partNameTextView = (TextView) findViewById(R.id.item_details_name);
        partPriceTextView = (TextView) findViewById(R.id.item_details_price);
        partSpec1TextView = (TextView) findViewById(R.id.item_details_spec1);
        partSpec2TextView = (TextView) findViewById(R.id.item_details_spec2);
        partSpec3TextView = (TextView) findViewById(R.id.item_details_spec3);
        partSpec4TextView = (TextView) findViewById(R.id.item_details_spec4);

        //Setting the data for the name,price and descriptions in the corresponding text view
        partNameTextView.setText(thisPart.getName());
        partPriceTextView.setText("Price: $" + thisPart.getPrice());
        partSpec1TextView.setText(thisPart.getDescription().get(0));
        partSpec2TextView.setText(thisPart.getDescription().get(1));
        partSpec3TextView.setText(thisPart.getDescription().get(2));
        partSpec4TextView.setText(thisPart.getDescription().get(3));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Set the slide transition when the back button is pressed
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
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