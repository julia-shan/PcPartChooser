package com.example.pcpartchooser.Data;

import android.content.Context;

import com.example.pcpartchooser.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.time.LocalDate;


public class DataProvider {

    private static DataProvider dataProviderInstance = null;
    private Context dataContext;
    private ArrayList<PcPart> allPcParts;
    private final List<String> dataCategories;
    private boolean startupInitialisation = true;
    private ArrayList<PcPart> topPicks;

    //Constructor for DataProvider
    public DataProvider(Context context) {
        this.topPicks = new ArrayList<PcPart>();
        this.dataContext = context;
        this.allPcParts = new ArrayList<>();
        this.dataCategories =  Arrays.asList("CPU", "GPU", "Motherboard", "Memory", "Storage");
        setAllPcParts();
    }

    //Create all the required PcPart instances
    private void setAllPcParts(){
        try {
            //Read in the data from the csv
            InputStream is = this.dataContext.getResources().openRawResource(R.raw.data);
            BufferedReader inputData = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String currentLine = inputData.readLine();
            currentLine = inputData.readLine();

            PcPart currentPcPart;
            //Until the end of the file
            while(currentLine != null) {
                //Separate each field denoted by a comma in the csv
                String[] currentLineSeparated = currentLine.split(",");

                //Get the price from the data
                float currentPrice = Float.parseFloat(currentLineSeparated[2]);
                
                //Get the day/month/year from the data
                int currentDay = Integer.parseInt(currentLineSeparated[3].substring(0, 2));
                int currentMonth = Integer.parseInt(currentLineSeparated[3].substring(2, 4));
                int currentYear = Integer.parseInt(currentLineSeparated[3].substring(4, 6));

                //Convert day/month/year into LocalDate type
                LocalDate currentReleaseDate = LocalDate.of(currentYear, currentMonth, currentDay);

                //Check what category the current csv line corresponds to and create the
                // appropriate instance and add to the ArrayList of all Pc Parts.
                if(currentLineSeparated[1].equals("CPU")) {
                    currentPcPart =  new CPU(currentLineSeparated[0], currentPrice, currentReleaseDate, currentLineSeparated[4], currentLineSeparated[5], currentLineSeparated[6], currentLineSeparated[7]);
                    this.allPcParts.add(currentPcPart);
                }
                else if(currentLineSeparated[1].equals("GPU")) {
                    currentPcPart = new GPU(currentLineSeparated[0], currentPrice, currentReleaseDate, currentLineSeparated[4], currentLineSeparated[5], currentLineSeparated[6], currentLineSeparated[7]);
                    this.allPcParts.add(currentPcPart);
                }
                else if(currentLineSeparated[1].equals("Memory")) {
                    currentPcPart = new Memory(currentLineSeparated[0], currentPrice, currentReleaseDate, currentLineSeparated[4], currentLineSeparated[5], currentLineSeparated[6], currentLineSeparated[7]);
                    this.allPcParts.add(currentPcPart);
                }
                else if(currentLineSeparated[1].equals("Motherboard")) {
                    currentPcPart = new Motherboard(currentLineSeparated[0], currentPrice, currentReleaseDate, currentLineSeparated[4], currentLineSeparated[5], currentLineSeparated[6], currentLineSeparated[7]);
                    this.allPcParts.add(currentPcPart);
                }
                else if(currentLineSeparated[1].equals("Storage")) {
                    currentPcPart = new Storage(currentLineSeparated[0], currentPrice, currentReleaseDate, currentLineSeparated[4], currentLineSeparated[5], currentLineSeparated[6], currentLineSeparated[7]);
                    this.allPcParts.add(currentPcPart);
                }
                //Read the next line of the csv
                currentLine = inputData.readLine();
            }
        }
        //If there is an error, then output it to Logcat. This should not happen as long as data.csv
        //is not removed from res/raw (and no other file named data exists in res/raw)
        catch(Exception error) {
            System.out.println("Error in loading csv: " + error);
        }
    }

    //DataProvider is implemented as a Singleton class. This means only one instance of it
    //will be created. Thus all instantiation calls will be made to getInstance which creates an
    //instance in the event there is no DataProvider instance (on startup) otherwise returns the
    //existing instance
    public static DataProvider getInstance(Context context) {
        if(dataProviderInstance == null) {
            dataProviderInstance = new DataProvider(context);
        }
        return dataProviderInstance;
    }

    //Returns a list of all the model class categories
    public ArrayList<Category> getCategoriesList(){
        ArrayList<Category> categoriesList = new ArrayList<>();
        //Loop through array containing categories
        for(int i = 0; i < this.dataCategories.size(); i++){
            categoriesList.add(new Category(this.dataCategories.get(i)));
        }
        return categoriesList;
    }

    //Returns all the instances of a particular model class category
    public  ArrayList<PcPart> getCategoryOfData(String selectedCategory) {
        ArrayList<PcPart> selectedCategoryList = new ArrayList<PcPart>();
        //Loop through all pc parts and check against it's category
        for(int i = 0; i < this.allPcParts.size(); i++) {
            if(this.allPcParts.get(i).getCategory().equals(selectedCategory)) {
                selectedCategoryList.add(this.allPcParts.get(i));
            }
        }
        return selectedCategoryList;
    }

    //Get a specific PcPart instance
    public PcPart getSpecificPcPart(int id) {
        for(int i = 0; i < this.allPcParts.size(); i++) {
            if(this.allPcParts.get(i).getId() == id) {
                return this.allPcParts.get(i);
            }
        }
        return null;
    }

    //Gets the latestParts (by release date on startup) for use in Top Picks. After startup, checks
    //parts against the views they have and selects the top part by views for each category
    public ArrayList<PcPart> getLatestParts() {
        boolean categoryHasBeenInitialised = false;
        //Loop through every category
        for(int j = 0; j < this.dataCategories.size(); j++) {
            String currentCategory = this.dataCategories.get(j);
            //Loop through every PcPart
            for(int i = 0; i < this.allPcParts.size(); i++) {
                PcPart currentPart = this.allPcParts.get(i);
                int currentViews = currentPart.getViews();
                //Check whether the current part is of the current category
                if(currentPart.getCategory().equals(currentCategory) && this.startupInitialisation == true) {
                    //Check whether the current category (aka index in the topPicks array) has been
                    //initialised.
                    if(categoryHasBeenInitialised) {
                        //Sort by release date as this is startup initialisation
                        if (currentPart.getReleaseDate().isBefore(topPicks.get(j).getReleaseDate())) {
                            topPicks.set(j, currentPart);
                        }
                    }
                    else {
                        //Initialise the index in the array
                        topPicks.add(currentPart);
                        categoryHasBeenInitialised = true;
                    }

                    //Check whether this is the last part and last category, if so set startup flag
                    //to false so that every other getLatestParts call is in reference to views
                    //not release date
                    if((j == this.dataCategories.size()-1) && (i == this.allPcParts.size()-1)) {
                        this.startupInitialisation = false;
                    }

                }
                //Else if it is no longer startup and top picks should be determined by views
                else if(currentPart.getCategory().equals(currentCategory) && this.startupInitialisation == false) {

                    if(currentViews > topPicks.get(j).getViews()) {
                        topPicks.set(j, currentPart);
                    }
                }
            }
            categoryHasBeenInitialised = false;
        }
        return topPicks;
    }

    //Return all PcParts - not needed for current implementation but could be used in future
    // implementations outside the current scope
    public ArrayList<PcPart> getAllPcParts() {
        return this.allPcParts;
    }
    //Return all the data categories - not needed for current implementation but could be used in
    //future implementations outside the current scope
    public List<String> getDataCategories(){
        return this.dataCategories;
    }

    //Returns a list of PcParts containing all the PcParts that correspond to a search query based
    //on it's name, category, and description fields
    public ArrayList<PcPart> getSearchResult(String dataQuery) {
        ArrayList<PcPart> matchingData = new ArrayList<PcPart>();

        //set search query to lowercase to make remove case sensitivity with user search
        dataQuery = dataQuery.toLowerCase();
        //Loop through every PcPart
        for(int i = 0; i < this.allPcParts.size(); i++) {
            PcPart currentPcPart = this.allPcParts.get(i);
            ArrayList<String> currentDescription = currentPcPart.getDescription();
            //Check whether the name or category matches the search query
            if(currentPcPart.getName().toLowerCase().contains(dataQuery) || currentPcPart.getCategory().toLowerCase().contains(dataQuery)) {
                matchingData.add(currentPcPart); //Add to list of matching parts
            }
            else { //Else check whether the description field contains the string
                //Loop through all the description fields
                for(int j = 0; j < currentDescription.size();j++) {
                    if(currentDescription.get(j).toLowerCase().contains(dataQuery)) {
                        matchingData.add(currentPcPart); //Add to list of matching parts if it contains the string
                    }
                }
            }
        }
        return matchingData;
    }
}
