package com.example.pcpartchooser.Data;

import java.time.LocalDate;

public class Motherboard extends PcPart{
    private String ports;
    private String socket;
    private String chipset;
    private String formFactor;

    //Constructor for Motherboard instance
    public Motherboard(String name, float price, LocalDate releaseDate, String ports, String socket,
                       String chipset, String formFactor) {
        super(name, "Motherboard", price, releaseDate);
        this.ports = ports;
        this.socket = socket;
        this.chipset = chipset;
        this.formFactor = formFactor;
        this.setDescription();
    }

    //Add description fields to description ArrayList
    private void setDescription() {
        this.description.add(this.ports);
        this.description.add(this.socket);
        this.description.add(this.chipset);
        this.description.add(this.formFactor);
    }
}
