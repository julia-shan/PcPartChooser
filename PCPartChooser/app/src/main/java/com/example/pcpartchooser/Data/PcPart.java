package com.example.pcpartchooser.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class PcPart implements Serializable {
    static int numOfIds = 0;
    protected int id;
    protected String name;
    protected String category;
    protected float price;
    protected ArrayList<String> description = new ArrayList<>();
    protected LocalDate releaseDate;
    protected ArrayList<String> allIDs;
    protected int views;

    PcPart(){ } //empty constructor

    PcPart(String name, String category, float price, LocalDate releaseDate){
        this.id = numOfIds;
        this.numOfIds++;
        this.name = name;
        this.category = category;
        this.price = price;
        this.releaseDate = releaseDate;
        this.views = 0;
        setImageIDs();
    }

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getCategory(){return this.category;}

    public float getPrice(){return this.price;}

    public ArrayList<String> getDescription() { return description; }

    public LocalDate getReleaseDate(){return this.releaseDate;}

    public ArrayList<String> getImageIDs(){ return this.allIDs; }

    public void setImageIDs(){
        //string manipulation
        String lowerCaseName = name.toLowerCase();
        String temp = category.toLowerCase() + "_" + lowerCaseName.replace(" ", "_");
        String imageID = temp.replace("-", "_");
        allIDs = new ArrayList<>();
        allIDs.add(imageID);
        allIDs.add(imageID + "1");
        allIDs.add(imageID + "2");
    }

    //Get the number of views this PcPart has
    public int getViews() {return this.views;}

    //Increment the views
    public void incrementViews(){
        this.views++;
    }
}
