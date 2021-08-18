package com.example.pcpartchooser.Data;

import static android.icu.lang.UCharacter.toLowerCase;

public class Category {
    String categoryName;
    String categoryImageName;
    String categoryDisplayName;

    public Category(String name){
        categoryName = name;
        categoryImageName = toLowerCase(name) + "category";
        if(name.equals("CPU")){
            categoryDisplayName = "CPU (Central Processing Unit)";
        } else if (name.equals("GPU")){
            categoryDisplayName= "GPU (Graphics Processing Unit)";
        } else if (name.equals("Storage")){
            categoryDisplayName = "Storage (SSD & HDD)";
        } else if (name.equals("Memory")){
            categoryDisplayName = "Memory (RAM)";
        } else if(name.equals("Motherboard")){
            categoryDisplayName = "Motherboard";
        } else {
        }
    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getCategoryImageName() {
        return categoryImageName;
    }

    public String getCategoryDisplayName(){ return categoryDisplayName; }
}
