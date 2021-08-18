package com.example.pcpartchooser.Data;

import java.time.LocalDate;

public class Storage extends PcPart {
    private String capacity;
    private String connectionType;
    private String readWriteSpeed;
    private String formFactor;

    //Constructor for Storage model instance
    public Storage(String name, float price, LocalDate releaseDate, String capacity,
                   String connectionType, String readWriteSpeed, String formFactor) {
        super(name, "Storage", price, releaseDate);
        this.capacity = capacity;
        this.connectionType = connectionType;
        this.readWriteSpeed = readWriteSpeed;
        this.formFactor = formFactor;
        this.setDescription();
    }

    //Add description fields into description ArrayList
    private void setDescription() {
        this.description.add(this.capacity);
        this.description.add(this.connectionType);
        this.description.add(this.readWriteSpeed);
        this.description.add(this.formFactor);
    }
}
