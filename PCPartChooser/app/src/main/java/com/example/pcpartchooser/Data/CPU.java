package com.example.pcpartchooser.Data;

import java.time.LocalDate;

public class CPU extends PcPart{
    private String clockSpeed;
    private String cpuFamily;
    private String numCores;
    private String threadCount;

    //Constructor for CPU instance
    public CPU(String name, float price, LocalDate releaseDate, String clockSpeed, String cpuFamily,
               String numCores, String threadCount) {
        super(name, "CPU", price, releaseDate);
        this.clockSpeed = clockSpeed;
        this.cpuFamily = cpuFamily;
        this.numCores = numCores;
        this.threadCount = threadCount;
        this.setDescription();
    }
    //Add description fields to a generic ArrayList of strings in the parent class (PcPart)
    private void setDescription() {
        this.description.add(clockSpeed);
        this.description.add(cpuFamily);
        this.description.add(numCores);
        this.description.add(threadCount);
    }
}
