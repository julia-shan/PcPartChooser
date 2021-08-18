package com.example.pcpartchooser.Data;

import java.time.LocalDate;

public class Memory extends PcPart {
    private String clockSpeed;
    private String memoryType;
    private String memoryCapacity;
    private String formFactor;

    //Constructor for Memory model class instance
    public Memory(String name, float price, LocalDate releaseDate, String clockSpeed,
                  String memoryType, String memoryCapacity, String formFactor) {
        super(name, "Memory", price, releaseDate);
        this.clockSpeed = clockSpeed;
        this.memoryType = memoryType;
        this.memoryCapacity = memoryCapacity;
        this.formFactor = formFactor;
        this.setDescription();
    }

    //Add description fields to description vector
    private void setDescription() {
        this.description.add(this.clockSpeed);
        this.description.add(this.memoryType);
        this.description.add(this.memoryCapacity);
        this.description.add(this.formFactor);
    }
}
