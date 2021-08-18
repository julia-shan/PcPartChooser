package com.example.pcpartchooser.Data;

import java.time.LocalDate;

public class GPU  extends PcPart{
    private String displayPorts;
    private String clockSpeed;
    private String memorySize;
    private String cardLength;

    //Constructor for GPU instance
    public GPU(String name, float price, LocalDate releaseDate, String displayPorts,
               String clockSpeed, String memorySize, String cardLength) {
        super(name, "GPU", price, releaseDate);
        this.displayPorts = displayPorts;
        this.clockSpeed = clockSpeed;
        this.memorySize = memorySize;
        this.cardLength = cardLength;
        this.setDescription();
    }
    // Set the description ArrayList based on the specs inputted
    private void setDescription() {
        this.description.add(this.displayPorts);
        this.description.add(this.clockSpeed);
        this.description.add(this.memorySize);
        this.description.add(this.cardLength);
    }
}
