package com.tc.chapter2.fieldLevel;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class Car {

    @NotNull
    private String manufacturer;

    @AssertTrue
    private boolean isRegistered;

    public Car(String manufacturer, boolean isRegistered) {
        this.manufacturer = manufacturer;
        this.isRegistered = isRegistered;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
