package com.tc.chapter2.propertyLevel;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class Car {

    private String manufacturer;

    private boolean isRegistered;

    public Car(String manufacturer, boolean isRegistered) {
        this.manufacturer = manufacturer;
        this.isRegistered = isRegistered;
    }

    @NotNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @AssertTrue
    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
